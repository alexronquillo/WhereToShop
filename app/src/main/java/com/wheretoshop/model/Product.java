package com.wheretoshop.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Map;

public class Product implements Serializable
{
	private long productId;
	private String productName;
	private String brandName;
	private String sizeDescription; 
	private String ouncesOrCount;

    public Product(Product product)
    {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.brandName = product.getBrandName();
        this.sizeDescription = product.getSizeDescription();
        this.ouncesOrCount = product.getOuncesOrCount();
    }

	public Product(long productId, String productName, String brandName, String ouncesOrCount, String sizeDescription)
    {
		init(productId, productName, brandName, ouncesOrCount, sizeDescription); 
	}

	private void init(long productId, String productName, String brandName, String ouncesOrCount, String sizeDescription)
    {
		this.productId = productId;
		this.productName = productName;
		this.brandName = brandName;
		this.sizeDescription = sizeDescription;
		this.ouncesOrCount = ouncesOrCount;
	}

	public long getProductId()
    {
        return productId;
    }

	public String getProductName()
    {
        return productName;
    }

	public String getBrandName()
    {
        return brandName;
    }

	public String getSizeDescription()
    {
        return sizeDescription;
    }

	public String getOuncesOrCount()
    {
        return ouncesOrCount;
    }

	public void setProductName(String productName)
    {
        this.productName = productName;
    }

	public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

	public void setSizeDescription(String sizeDescription)
    {
        this.sizeDescription = sizeDescription;
    }

	public void setOuncesOrCount(String ouncesOrCount)
    {
        this.ouncesOrCount = ouncesOrCount;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject jsonProduct = new JSONObject();
        jsonProduct.put("productId", productId);
        jsonProduct.put("productName", productName);
        jsonProduct.put("brandName", brandName);
        jsonProduct.put("sizeDescription", sizeDescription);
        jsonProduct.put("ouncesOrCount", ouncesOrCount);
        return jsonProduct;
    }
}
