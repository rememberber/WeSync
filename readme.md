Data Sync
============

UI
-------
>Java Swing  
>some icons

功能特性
---------
>数据库同步，数据库间数据传输  
  
  
图标来源：http://designmodo.com/linecons-free/

环境依赖
---------
>Java 8

目录结构描述
-------------
>config：配置
>>config.xml：UI设置参数的持久化  
>>Re-initialization.sql：还原普相关表到初始化状态的memo  
>>zh-cn.properties：语言，国际化

>DB_Backup：备份相关
>>mysql_backup.bat：备份脚本  
>>mysql_table_backup.sql：备份时执行的sql

>log_SQL：记录每次执行的sql流水

>snaps：csv快照  
>snaps_bak：csv快照备份

>TableField：表-字段 对应关系配置

>Trigger：同步出发机制配置

版本内容更新
-------------

声明
-------