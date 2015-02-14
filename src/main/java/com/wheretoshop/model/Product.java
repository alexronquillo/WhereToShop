package com.wheretoshop.model;

import java.math.BigDecimal;

/**
	A product is a grocery store item which contains a product name,
	total ounces or package item count, and optionally a brand name and
	size description.
*/
class Product
{
	private String productName;
	private String brandName;
	private String sizeDescription; 
	private BigDecimal ouncesOrCount;

	/** 
		Product Constructor
		This constructor contains parameters for all attributes, required and not required,
		of a product.

		@param productName The name of the product
		@param brandName The brand of the product
		@param ouncesOrCount The total ounces or the package item count of the product
		@param sizeDesciption A description of the size of the product
	*/
	public Product(String productName, String brandName, BigDecimal ouncesOrCount, String sizeDescription)
	{
		init(productName, brandName, ouncesOrCount, sizeDescription); 
	}

	/** 
		Product Constructor
		This constructor contains parameters for both required attributes 
		(product name and ounces/count) and a parameter for one optional 
		attribute (brand name) of a product.

		@param productName The name of the product
		@param brandName The brand of the product
		@param ouncesOrCount The total ounces or the package item count of the product
	*/
	public Product(String productName, String brandName, BigDecimal ouncesOrCount)
	{
		init(productName, brandName, ouncesOrCount, "");
	}

	/** 
		Product Constructor
		This constructor contains parameters for both required attributes 
		(product name and ounces/count) and a parameter for one optional 
		attribute (size description) of a product.

		@param productName The name of the product
		@param brandName The brand of the product
		@param sizeDesciption A description of the size of the product
	*/
	public Product(String productName, BigDecimal ouncesOrCount, String sizeDescription)
	{
		init(productName, "", ouncesOrCount, sizeDescription);
	}

	/** 
		Product Constructor
		This constructor contains only parameters for required attributes 
		(product name and ounces/count).

		@param productName The name of the product
		@param brandName The brand of the product
	*/
	public Product(String productName, BigDecimal ouncesOrCount)
	{
		init(productName, "", ouncesOrCount, "");
	}

	/** 
		Product Initializer
		This private method is used by constructors to initialize instance variables.

		@param productName The name of the product
		@param brandName The brand of the product
		@param ouncesOrCount The total ounces or the package item count of the product
		@param sizeDesciption A description of the size of the product
	*/
	private void init(String productName, String brandName, BigDecimal ouncesOrCount, String sizeDescription)
	{
		this.productName = productName;
		this.brandName = brandName;
		this.sizeDescription = sizeDescription;
		this.ouncesOrCount = ouncesOrCount;
	}

	/**
		Gets the name of the product.

		@return productName The name of the product
	*/
	public String getProductName() { return productName; }

	/**
		Gets the brand name of the product.

		@return brandName The brand name of the product
	*/
	public String getBrandName() { return brandName; }

	/**
		Gets the size description of the product.

		@return productName A description of the size of the product
	*/
	public String getSizeDescription() { return sizeDescription; }

	/**
		Gets the ounces/count of the product.

		@return productName A description of the size of the product
	*/
	public BigDecimal getOuncesOrCount() { return ouncesOrCount; }

	/**
		Sets the name of the product

		@param productName A description of the size of the product
	*/
	public void setProductName(String productName) { this.productName = productName; }

	/**
		Sets the brand name of the product.

		@param brandName The brand name of the product
	*/
	public void setBrandName(String brandName) { this.brandName = brandName; }

	/**
		Sets the size description of the product.

		@param productName A description of the size of the product
	*/
	public void setSizeDescription(String sizeDescription) { this.sizeDescription = sizeDescription; }

	/**
		Sets the ounces/count of the product.

		@param productName A description of the size of the product
	*/
	public void setOuncesOrCount(BigDecimal ouncesOrCount) { this.ouncesOrCount = ouncesOrCount; }
}
