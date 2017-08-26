USE WTS;

CREATE TABLE WTS_ProductTable
(
	ProductID INT AUTO_INCREMENT NOT NULL
	, ProductName VARCHAR(25) NOT NULL
	, BrandName VARCHAR(25) NULL
	, StoreID INT NOT NULL
	, Price DECIMAL(8, 2) NOT NULL
	, SizeDescription VARCHAR(25) NULL
	, TotalOunces DECIMAL(6,2)
	, PRIMARY KEY (ProductID)
	, INDEX store_id_index (StoreID)
	, Foreign KEY (StoreID)
		REFERENCES WTS_StoreTable(StoreID)
		ON DELETE CASCADE
) Engine=InnoDB;
