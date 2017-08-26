USE WTS;

CREATE TABLE WTS_LoyaltyMembershipTable
(
	UserID INT NOT NULL,
	StoreName VARCHAR(25) NOT NULL,
	StoreZipCode INT NOT NULL,
	FOREIGN KEY (UserID) REFERENCES WTS_UserTable (UserId),
	FOREIGN KEY (StoreName) REFERENCES WTS WTS_StoreTable (StoreID)
) Engine=InnoDB;

