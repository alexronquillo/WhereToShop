package com.wheretoshop.model;

import org.json.JSONException;
import org.json.JSONObject;

public class GroceryListProduct
{
    private static final String JSON_PRODUCT = "json_product";
    private static final String JSON_QUANTITY = "json_quantity";
	private Product product;
	private int quantity;

    public GroceryListProduct(GroceryListProduct groceryListProduct)
    {
        this.product = new Product(groceryListProduct.getProduct());
        this.quantity = groceryListProduct.getQuantity();
    }

    public GroceryListProduct(JSONObject serializedGroceryListProduct) throws JSONException
    {
        JSONObject jsonProduct = (JSONObject)serializedGroceryListProduct.get(JSON_PRODUCT);
        long productId = jsonProduct.getLong("productId");
        String productName = jsonProduct.getString("productName");
        String brandName = jsonProduct.getString("brandName");
        String sizeDescription = jsonProduct.getString("sizeDescription");
        String ouncesOrCount = jsonProduct.getString("ouncesOrCount");
        this.product = new Product(productId, productName, brandName, sizeDescription, ouncesOrCount);
        this.quantity = (int)serializedGroceryListProduct.get(JSON_QUANTITY);
    }

	public GroceryListProduct(Product product, int quantity)
    {
		this.product = product;
		this.quantity = quantity;
	}

    public JSONObject toJSON() throws JSONException
    {
        JSONObject serializedGroceryListProduct = new JSONObject();
        serializedGroceryListProduct.put(JSON_PRODUCT, product.toJson());
        serializedGroceryListProduct.put(JSON_QUANTITY, quantity);
        return serializedGroceryListProduct;
    }

	public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

	public Product getProduct()
    {
        return product;
    }

	public int getQuantity()
    {
        return quantity;
    }
}
