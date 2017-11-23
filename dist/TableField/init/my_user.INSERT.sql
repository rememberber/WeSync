INSERT INTO my_user (
	LOGIN_NAME,
	PASSWORD,
	REAL_NAME,
	STATUS,
	ADDRESS,
	RMK,
	CREATE_DATE,
	CREATE_USER,
	UPDATE_DATE,
	UPDATE_USER,
	LOGIN_IP,
	MOBILE,
	SYS_EMAIL,
	TELPHONE,
	LOGO_IMG,
	PROVINCE,
	CITY,
	sbc_id,
	EMP_CODE,
	ID_CARD,
	DEPT_CODE,
	POSITION_CODE,
	BANK_NUMBER
)
VALUES
	(
		'$PINYIN{this.Name}$@SUB(7,END){this.Telphone}@',
		'#MD5PE{@SUB(12,END){this.IDCardID}@}#',
		'this.Name',
		'#CASE(1=2,2=1){this.Status}#',
		'this.Address',
		'',
		'$SYS_DATE_TIME$',
		2,
		'',
		null,
		'',
		'this.Telphone',
		'this.EMail',
		'',
		'',
		'',
		'',
		null,
		'',
		'this.IDCardID',
		left('#REPLACE(".",""){@SUB(".",END){this.UnitCode}@}#',16),
		(SELECT SP_POSITION_CODE FROM my_position WHERE SP_POSITION_NAME = '$SQL{SELECT PositionName FROM Info WHERE id = 'this.id'}$'),
		'this.BankAccountNum'
	);
	
INSERT INTO my_user_role (
	USER_ID,
	ROLE_ID,
	STATUS,
	CREATE_DATE,
	CREATE_USER,
	UPDATE_DATE,
	UPDATE_USER,
	REMARK
)
VALUES
	(
		(SELECT MAX(ID) FROM my_user),
		(SELECT ID FROM my_role WHERE ROLE_NAME = 'test'),
		'2',
		'$SYS_DATE_TIME$',
		2,
		'',
		null,
		null
	);

