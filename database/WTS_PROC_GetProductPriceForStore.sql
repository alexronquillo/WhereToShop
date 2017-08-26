DELIMITER //
CREATE PROCEDURE GetProductPriceForStore
(
IN product_name VARCHAR(25)
, IN brand_name VARCHAR(25)
, IN size_description VARCHAR(25)
, IN store_name VARCHAR(25)
, IN zip_code INT
)
BEGIN
	SELECT 
  		Price 
	FROM 
		WTS_ProductTable
	WHERE 
		ProductName = product_name
		AND BrandName = brand_name
		AND SizeDescription = size_description
		AND StoreID = (SELECT StoreID FROM WTS_StoreTable WHERE StoreName = store_name AND StoreZipCode = zip_code);
END //
DELIMITER ;
