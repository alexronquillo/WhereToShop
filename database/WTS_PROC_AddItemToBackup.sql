DELIMETER //
CREATE PROCEDURE AddItemToBackup
(
IN user VARCHAR(25)
, IN product_name VARCHAR(25)
, IN brand_name VARCHAR(25)
, IN units VARCHAR(10)
, IN quantity DECIMAL
)
BEGIN
	INSERT INTO WTS_GroceryListBackupTable(UserID, ProductName, BrandName, Units, Quantity) 
	VALUES(user, product_name, brand_name, units, quantity);
END //
DELIMETER;
