package org.molgenis;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
import org.molgenis.model.CytokineExpression;
import org.molgenis.model.CytokineExpressionEntity;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder
{
	/**
	 * Let's configure the Camel routing rules using Java code...
	 */
	@Override
	public void configure()
	{
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
				.convertBodyTo(CytokineExpressionEntity.class).aggregate(constant(true), aggregationStrategy)
				.completionSize(1000).completionInterval(1000).marshal().json(JsonLibrary.Gson)
				.setHeader("x-Molgenis-token", constant("a46e2cce65044a8caad0ede10bf16557"))
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader("Content-Type", constant("application/json"))
				.to("http4://localhost:8080/api/v2/CytokineExpression").log("in.headers");
	}
}
