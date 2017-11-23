UPDATE my_user
 SET REAL_NAME = 'this.Name',
 ADDRESS = 'this.Address',
 UPDATE_DATE = '$SYS_DATE_TIME$',
 UPDATE_USER = 2,
 MOBILE = 'this.Telphone',
 SYS_EMAIL = 'this.EMail',
 ID_CARD = 'this.IDCardID',
 DEPT_CODE = left('#REPLACE(".",""){@SUB(".",END){this.UnitCode}@}#',16),
 POSITION_CODE = (SELECT SP_POSITION_CODE FROM my_position WHERE SP_POSITION_NAME = '$SQL{SELECT PositionName FROM Info WHERE id = 'this.id'}$'),
 BANK_NUMBER = 'this.BankAccountNum'
WHERE ID_CARD = 'before.IDCardID' ;