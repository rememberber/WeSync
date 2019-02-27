<p align="center">
  <a href="https://github.com/rememberber/WeSync">
   <img alt="WeSync-Logo" src="https://github.com/rememberber/WeSync/blob/master/src/main/resources/icon/WeSync.png?raw=true">
  </a>
</p>

WeSync [![Build Status](https://travis-ci.org/rememberber/WeSync.svg?branch=master)](https://travis-ci.org/rememberber/WeSync)
============

com.luoboduner.wesync.ui
-------
>Java Swing  
>some icons

功能特性
---------
>数据库同步，数据库间数据传输  
>支持不同数据库间，不同表和不同表结构间数据同步  
>支持同步过程中数据加工（可扩展）  
>支持失败事务回滚  
>支持失败快照回滚  
>目前支持SqlServer->MySQL  
>可扩展任意不同数据库间数据传输

速览
-----
<p align="center">
  <a href="https://github.com/rememberber/WeSync">
   <img alt="WeSync" src="https://github.com/rememberber/WeSync/blob/master/screen_shot/%E5%9B%BE%E5%83%8F%20030.png?raw=true">
  </a>
</p>
<p align="center">
  <a href="https://github.com/rememberber/WeSync">
   <img alt="WeSync" src="https://github.com/rememberber/WeSync/blob/master/screen_shot/%E5%9B%BE%E5%83%8F%20031.png?raw=true">
  </a>
</p>
<p align="center">
  <a href="https://github.com/rememberber/WeSync">
   <img alt="WeSync" src="https://github.com/rememberber/WeSync/blob/master/screen_shot/%E5%9B%BE%E5%83%8F%20032.png?raw=true">
  </a>
</p>
<p align="center">
  <a href="https://github.com/rememberber/WeSync">
   <img alt="WeSync" src="https://github.com/rememberber/WeSync/blob/master/screen_shot/%E5%9B%BE%E5%83%8F%20033.png?raw=true">
  </a>
</p>
<p align="center">
  <a href="https://github.com/rememberber/WeSync">
   <img alt="WeSync" src="https://github.com/rememberber/WeSync/blob/master/screen_shot/%E5%9B%BE%E5%83%8F%20034.png?raw=true">
  </a>
</p>
<p align="center">
  <a href="https://github.com/rememberber/WeSync">
   <img alt="WeSync" src="https://github.com/rememberber/WeSync/blob/master/screen_shot/%E5%9B%BE%E5%83%8F%20035.png?raw=true">
  </a>
</p>

  
环境依赖
---------
>Java 8

目录结构描述
-------------
>config：配置
>>config.xml：UI设置参数的持久化  
>>Re-initialization.sql：还原相关表到初始化状态的memo  
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
>图标来源：http://designmodo.com/linecons-free/