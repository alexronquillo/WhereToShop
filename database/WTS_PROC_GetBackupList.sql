DELIMETER //
CREATE PROCEDURE GetBackupList
(
IN user VARCHAR(25)
)
BEGIN
	SELECT
		*
	FROM  
		WTS_GroceryListBackupTable
	WHERE 
		UserID = user;
END //
DELIMETER;
