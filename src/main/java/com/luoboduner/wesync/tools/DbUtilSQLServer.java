package com.luoboduner.wesync.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * SQLServer数据库工具，单例，持久连接
 *
 * @author Bob
 */
public class DbUtilSQLServer {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private static String DBUrl = null;
    private static String DBName = null;
    private static String DBUser = null;
    private static String DBPassword = null;

    private static DbUtilSQLServer instance = null;

    private static final Logger logger = LoggerFactory.getLogger(DbUtilSQLServer.class);

    /**
     * 私有的构造
     */
    private DbUtilSQLServer() {
        loadConfig();
    }

    /**
     * 获取实例，线程安全
     *
     * @return
     */
    public static synchronized DbUtilSQLServer getInstance() {
        if (instance == null) {
            instance = new DbUtilSQLServer();
        }
        return instance;
    }

    /**
     * 从配置文件加载设置数据库信息
     */
    private void loadConfig() {
        try {
            String dbclassname = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            DBUrl = ConstantsTools.CONFIGER.getHostFrom();
            DBName = ConstantsTools.CONFIGER.getNameFrom();
            DBUser = ConstantsTools.CONFIGER.getUserFrom();
            DBPassword = ConstantsTools.CONFIGER.getPasswordFrom();

            Class.forName(dbclassname);
        } catch (Exception e) {

            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 获取连接，线程安全
     *
     * @return
     * @throws SQLException
     */
    public synchronized Connection getConnection() throws SQLException {
        String user = "";
        String password = "";
        try {
            DESPlus des = new DESPlus();
            user = des.decrypt(DBUser);
            password = des.decrypt(DBPassword);
        } catch (Exception e) {
            logger.error(PropertyUtil.getProperty("ds.ui.database.from.err.decode") + e.toString());
            e.printStackTrace();
        }
        // 当DB配置变更时重新获取
        if (!ConstantsTools.CONFIGER.getHostFrom().equals(DBUrl)
                || !ConstantsTools.CONFIGER.getNameFrom().equals(DBName)
                || !ConstantsTools.CONFIGER.getUserFrom().equals(DBUser)
                || !ConstantsTools.CONFIGER.getPasswordFrom().equals(DBPassword)) {
            loadConfig();
            // "jdbc:sqlserver://20.1.1.194:1433;DatabaseName=AIS20151221115438;"
            connection = DriverManager.getConnection("jdbc:sqlserver://" + DBUrl + ";DatabaseName=" + DBName, user,
                    password);
            // 把事务提交方式改为手工提交
            connection.setAutoCommit(false);
        }

        // 当connection失效时重新获取
        if (connection == null || connection.isValid(10) == false) {

            // "jdbc:sqlserver://20.1.1.194:1433;DatabaseName=AIS20151221115438;"
            connection = DriverManager.getConnection("jdbc:sqlserver://" + DBUrl + ";DatabaseName=" + DBName, user,
                    password);
            // 把事务提交方式改为手工提交
            connection.setAutoCommit(false);
        }

        if (connection == null) {
            logger.error("Can not load SQL Server jdbc and get connection.");
        }
        return connection;
    }

    /**
     * 测试连接，线程安全 参数从配置文件获取
     *
     * @return
     * @throws SQLException
     */
    public synchronized Connection testConnection() throws SQLException {
        loadConfig();
        // "jdbc:sqlserver://20.1.1.194:1433;DatabaseName=AIS20151221115438;"
        String user = "";
        String password = "";
        try {
            DESPlus des = new DESPlus();
            user = des.decrypt(DBUser);
            password = des.decrypt(DBPassword);
        } catch (Exception e) {
            logger.error(PropertyUtil.getProperty("ds.ui.database.from.err.decode") + e.toString());
            e.printStackTrace();
        }
        connection = DriverManager.getConnection("jdbc:sqlserver://" + DBUrl + ";DatabaseName=" + DBName, user,
                password);
        // 把事务提交方式改为手工提交
        connection.setAutoCommit(false);

        if (connection == null) {
            logger.error("Can not load SQL Server jdbc and get connection.");
        }
        return connection;
    }

    /**
     * 测试连接，线程安全 参数从入参传入
     *
     * @return
     * @throws SQLException
     */
    public synchronized Connection testConnection(String dburl, String dbname, String dbuser, String dbpassword)
            throws SQLException {
        loadConfig();
        // "jdbc:sqlserver://20.1.1.194:1433;DatabaseName=AIS20151221115438;"
        connection = DriverManager.getConnection("jdbc:sqlserver://" + dburl + ";DatabaseName=" + dbname, dbuser,
                dbpassword);
        // 把事务提交方式改为手工提交
        connection.setAutoCommit(false);

        if (connection == null) {
            logger.error("Can not load SQL Server jdbc and get connection.");
        }
        return connection;
    }

    /**
     * 获取数据库声明，私有，线程安全
     *
     * @throws SQLException
     */
    private synchronized void getStatement() throws SQLException {
        getConnection();
        // 仅当statement失效时才重新创建
        if (statement == null || statement.isClosed()) {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
    }

    /**
     * 关闭（结果集、声明、连接），线程安全
     *
     * @throws SQLException
     */
    public synchronized void close() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
            resultSet = null;
        }
        if (statement != null) {
            statement.close();
            statement = null;
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * 执行查询，线程安全
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public synchronized ResultSet executeQuery(String sql) throws SQLException {
        getStatement();
        if (resultSet != null && !resultSet.isClosed()) {
            resultSet.close();
        }
        resultSet = null;
        resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    /**
     * 执行更新，线程安全
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public synchronized int executeUpdate(String sql) throws SQLException {
        int result = 0;
        getStatement();
        result = statement.executeUpdate(sql);
        return result;
    }

    /**
     * 执行任意，线程安全
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public synchronized boolean execute(String sql) throws SQLException {
        boolean result;
        getStatement();
        result = statement.execute(sql);
        return result;
    }

}
