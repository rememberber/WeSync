package com.luoboduner.wesync.logic;

/**
 * 执行器的接口
 *
 * @author Bob
 */
public interface ExecuteThreadInterface {
    /**
     * 初始化变量
     */
    void init();

    /**
     * 测试连接
     *
     * @return
     */
    boolean testLink();

    /**
     * 解析配置文件
     *
     * @return
     */
    boolean analyseConfigFile();

    /**
     * 备份
     */
    void backUp();

    /**
     * 建立快照
     *
     * @return
     */
    boolean newSnap();

    /**
     * 对比快照，生成SQL
     *
     * @return
     */
    boolean diffSnap();

    /**
     * 执行SQL
     *
     * @return
     */
    boolean executeSQL();

}