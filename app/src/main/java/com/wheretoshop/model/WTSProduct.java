package com.wheretoshop.model;

/**
 * Created by arr on 5/7/15.
 */
public class WTSProduct
{
    private Product product;
    private String storeName;
    private String generalPrice;

    public WTSProduct(Product product, String storeName, String generalPrice)
    {
        this.product = product;
        this.storeName = storeName;
        this.generalPrice = generalPrice;
    }

    public Product getProduct()
    {
        Product productCopy = new Product(product);
        return productCopy;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public String getGeneralPrice()
    {
        return generalPrice;
    }
}
