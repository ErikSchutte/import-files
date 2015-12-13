package org.molgenis;

import static com.google.common.collect.Iterables.transform;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
import org.molgenis.data.MolgenisInvalidFormatException;
import org.molgenis.data.excel.ExcelRepositoryCollection;
import org.molgenis.model.CytokineExpression;
import org.molgenis.model.CytokineExpressionEntity;

import com.google.common.collect.Sets;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder
{
	private String molgenisToken = "dba0eb85782044c6a53c317de1a03ec9";
	private Set<String> snps;

	/**
	 * Let's configure the Camel routing rules using Java code...
	 * 
	 * @throws MolgenisInvalidFormatException
	 * @throws IOException
	 */
	@Override
	public void configure() throws IOException, MolgenisInvalidFormatException
	{
		snps = getSnps();

		GroupedExchangeAggregationStrategy aggregationStrategy = new GroupedExchangeAggregationStrategy()
		{
			public void onCompletion(Exchange exchange)
			{
				@SuppressWarnings("unchecked")
				List<Exchange> list = (List<Exchange>) exchange.getProperty(Exchange.GROUPED_EXCHANGE);
				if (list != null)
				{
					Map<String, List<Object>> body = new LinkedHashMap<String, List<Object>>();
					body.put("entities", StreamSupport.stream(list.spliterator(), false).map(x -> x.getIn().getBody())
							.collect(Collectors.toList()));
					exchange.getIn().setBody(body);
				}
			}
		};

		from("file:src/data?noop=true").unmarshal().bindy(BindyType.Csv, CytokineExpression.class).split().body()
				.convertBodyTo(CytokineExpressionEntity.class).filter(this::isKnownSnp)
				.aggregate(constant(true), aggregationStrategy).completionSize(1000).completionInterval(1000).marshal()
				.json(JsonLibrary.Gson).setHeader("x-Molgenis-token", constant(molgenisToken))
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader("Content-Type", constant("application/json"))
				.to("http4://localhost:8080/api/v2/CytokineExpression").log("in.headers");
	}

	private boolean isKnownSnp(Exchange e)
	{
		return snps.contains(e.getIn().getBody(CytokineExpressionEntity.class).getSnp());
	}

	private Set<String> getSnps() throws IOException, MolgenisInvalidFormatException 
	{
		ExcelRepositoryCollection repos = new ExcelRepositoryCollection(
				new File("src/main/resources/snpsToPlot-emx.xlsx"));
		Iterable<String> snpNames = transform(repos.getRepository("SnpsToPlot"), snp -> snp.getString("SnpRs"));
		return Sets.newHashSet(snpNames);
	}
}
