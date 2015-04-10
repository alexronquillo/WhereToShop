package com.wheretoshop.model;

public class Store
{
    private long storeId;
    private String storeName;
    private String zipCode;

    public Store(long storeId, String storeName, String zipCode)
    {
        this.storeId = storeId;
        this.storeName = storeName;
        this.zipCode = zipCode;
    }

    public long getStoreId()
    {
        return storeId;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setStoreId(long storeId)
    {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }
}
