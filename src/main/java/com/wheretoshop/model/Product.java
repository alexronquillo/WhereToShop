package com.wheretoshop.model;

import java.math.BigDecimal;

class Product
{
	private String productName;
	private String brandName;
	private String sizeDescription; 
	private BigDecimal ouncesOrCount;

	public Product(String productName, String brandName, BigDecimal ouncesOrCount, String sizeDescription)
	{
		init(productName, brandName, ouncesOrCount, sizeDescription); 
	}

	public Product(String productName, BigDecimal ouncesOrCount, String sizeDescription)
	{
		init(productName, "", ouncesOrCount, sizeDescription);
	}

	private void init(String productName, String brandName, BigDecimal ouncesOrCount, String sizeDescription)
	{
		this.productName = productName;
		this.brandName = brandName;
		this.sizeDescription = sizeDescription;
		this.ouncesOrCount = ouncesOrCount;
	}

	public String getProductName() { return productName; }
	public String getBrandName() { return brandName; }
	public String getSizeDescription() { return sizeDescription; }
	public BigDecimal getOuncesOrCount() { return ouncesOrCount; }

	public void setProductName(String productName) { this.productName = productName; }
	public void setBrandName(String brandName) { this.brandName = brandName; }
	public void setSizeDescription(String sizeDescription) { this.sizeDescription = sizeDescription; }
	public void setOuncesOrCount(BigDecimal ouncesOrCount) { this.ouncesOrCount = ouncesOrCount; }
}
