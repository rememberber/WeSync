package logic;

/**
 * 执行器的接口
 * 
 * @author Bob
 *
 */
public interface ExecuteThreadInterface {
	/**
	 * 初始化变量
	 */
	public abstract void init();

	/**
	 * 测试连接
	 * 
	 * @return
	 */
	public abstract boolean testLink();

	/**
	 * 解析配置文件
	 * 
	 * @return
	 */
	public abstract boolean analyseConfigFile();

	/**
	 * 备份
	 */
	public abstract void backUp();

	/**
	 * 建立快照
	 * 
	 * @return
	 */
	public abstract boolean newSnap();

	/**
	 * 对比快照，生成SQL
	 * 
	 * @return
	 */
	public abstract boolean diffSnap();

	/**
	 * 执行SQL
	 * 
	 * @return
	 */
	public abstract boolean executeSQL();

}
