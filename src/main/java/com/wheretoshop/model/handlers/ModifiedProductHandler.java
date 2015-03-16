package com.wheretoshop.model;

import java.util.Set;

public interface ModifiedProductHandler {
	public enum PRODUCT_COLUMN_TYPE {BRAND_NAME, SIZE_DESCRIPTION, OUNCES_OR_COUNT}
	public void handleModifiedProduct(PRODUCT_COLUMN_TYPE type, Set<String> column);	
}
