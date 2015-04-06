package com.wheretoshop.model;

public class GroceryListProduct {
	private Product product;
	private int quantity;

	public GroceryListProduct(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public void setQuantity(int quantity) { this.quantity = quantity; }
	public Product getProduct() { return product; }
	public int getQuantity() { return quantity; }
}
