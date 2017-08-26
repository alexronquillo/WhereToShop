DELIMETER //
CREATE PROCEDURE EmptyBackupList
(
IN user VARCHAR(25)
)
BEGIN
	DELETE 
		*
	FROM
		(SELECT * FROM WTS_GroceryListBackupTable WHERE UserID = user);


END //
DELIMETER ;

