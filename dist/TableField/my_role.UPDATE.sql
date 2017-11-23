UPDATE my_role
SET ROLE_NAME = 'this.Name',
 UPDATE_DATE = '$SYS_DATE_TIME$',
 UPDATE_USER = 2
WHERE
	(ROLE_NAME = 'before.Name');