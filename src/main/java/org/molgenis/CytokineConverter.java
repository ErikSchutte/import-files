package org.molgenis;

import org.apache.camel.Converter;
import org.molgenis.model.CytokineExpression;
import org.molgenis.model.CytokineExpressionEntity;

@Converter
public class CytokineConverter
{
	@Converter
	public CytokineExpressionEntity toCytokineEntity(CytokineExpression csv) throws Exception
	{
		String[] parts = csv.getFileName().split("_");
		CytokineExpressionEntity result = new CytokineExpressionEntity();
		result.setRownum(csv.getRowNum());
		result.setFilename(csv.getFileName());
		result.setSnp(csv.getSnp());
		result.setCytokine(parts[0]);
		result.setStimulus(parts[1]);
		result.setCellType(parts[2]);
		result.setDuration(parts[3]);
		result.setStatistic(csv.getStatistic());
		result.setpValue(csv.getpValue());
		result.setFDR(csv.getFDR());
		result.setBeta(csv.getBeta());
		return result;
	}
}
