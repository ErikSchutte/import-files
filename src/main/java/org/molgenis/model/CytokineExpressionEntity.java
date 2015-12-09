package org.molgenis.model;

public class CytokineExpressionEntity
{
	private String id;
	private int rownum;
	private String filename;
	private String snp;
	private String cytokine;
	private String cellType;
	private String stimulus;
	private String duration;
	private double statistic;
	private double pValue;
	private double FDR;
	private double beta;

	public String getCellType()
	{
		return cellType;
	}

	public void setCellType(String cellType)
	{
		this.cellType = cellType;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getRownum()
	{
		return rownum;
	}

	public void setRownum(int rownum)
	{
		this.rownum = rownum;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public String getSnp()
	{
		return snp;
	}

	public void setSnp(String snp)
	{
		this.snp = snp;
	}

	public String getCytokine()
	{
		return cytokine;
	}

	public void setCytokine(String cytokine)
	{
		this.cytokine = cytokine;
	}

	public String getStimulus()
	{
		return stimulus;
	}

	public void setStimulus(String stimulus)
	{
		this.stimulus = stimulus;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
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
		return FDR;
	}

	public void setFDR(double fDR)
	{
		this.FDR = fDR;
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
