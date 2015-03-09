package com.wheretoshop.model;

import java.math.BigDecimal;
import java.io.Serializable;

public class Product implements Serializable
{
	private int productId;
	private String productName;
	private String brandName;
	private String sizeDescription; 
	private BigDecimal ouncesOrCount;

	public Product(int productId, String productName, String brandName, BigDecimal ouncesOrCount, String sizeDescription)
	{
		init(productId, productName, brandName, ouncesOrCount, sizeDescription); 
	}

	public Product(int productId, String productName, BigDecimal ouncesOrCount, String sizeDescription)
	{
		init(productId, productName, "", ouncesOrCount, sizeDescription);
	}

	private void init(int productId, String productName, String brandName, BigDecimal ouncesOrCount, String sizeDescription)
	{
		this.productId = productId;
		this.productName = productName;
		this.brandName = brandName;
		this.sizeDescription = sizeDescription;
		this.ouncesOrCount = ouncesOrCount;
	}

	public int getProductId() { return productId; }
	public String getProductName() { return productName; }
	public String getBrandName() { return brandName; }
	public String getSizeDescription() { return sizeDescription; }
	public BigDecimal getOuncesOrCount() { return ouncesOrCount; }

	public void setProductName(String productName) { this.productName = productName; }
	public void setBrandName(String brandName) { this.brandName = brandName; }
	public void setSizeDescription(String sizeDescription) { this.sizeDescription = sizeDescription; }
	public void setOuncesOrCount(BigDecimal ouncesOrCount) { this.ouncesOrCount = ouncesOrCount; }
}
