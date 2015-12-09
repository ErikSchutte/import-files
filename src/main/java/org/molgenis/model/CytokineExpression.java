package org.molgenis.model;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = "\t", skipFirstLine = true)
public class CytokineExpression
{
	@DataField(pos = 1)
	private int rowNum;
	@DataField(pos = 2)
	private String snp;
	@DataField(pos = 3)
	private String fileName;
	@DataField(pos = 4)
	private double statistic;
	@DataField(pos = 5)
	private double pValue;
	@DataField(pos = 6)
	private double fDR;
	@DataField(pos = 7)
	private double beta;

	public int getRowNum()
	{
		return rowNum;
	}

	public void setRowNum(int rowNum)
	{
		this.rowNum = rowNum;
	}

	public String getSnp()
	{
		return snp;
	}

	public void setSnp(String snp)
	{
		this.snp = snp;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public double getStatistic()
	{
		return statistic;
	}

	public void setStatistic(double statistic)
	{
		this.statistic = statistic;
	}

	public double getpValue()
	{
		return pValue;
	}

	public void setpValue(double pValue)
	{
		this.pValue = pValue;
	}

	public double getFDR()
	{
		return fDR;
	}

	public void setfDR(double fDR)
	{
		this.fDR = fDR;
	}

	public double getBeta()
	{
		return beta;
	}

	public void setBeta(double beta)
	{
		this.beta = beta;
	}

}
