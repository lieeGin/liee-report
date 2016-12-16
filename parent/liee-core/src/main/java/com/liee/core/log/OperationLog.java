package com.liee.core.log;

public abstract class OperationLog implements OperationLogInterface {

	
	public Logger log = Logger.getLogger();
	
	protected Object entity;
	
	public OperationLog(Object obj){
		entity=obj;
	}
	
	@Override
	public void log(int operaterId){
		try{
			log.info(">>>>开始记录日志");
			String logContent = getLogContentByParam();
			recordLog(operaterId,logContent);
			log.info("<<<<记录日志结束");
		}catch(Exception e){
			log.error("记录日志失败", e);
		}
	}
	/**
	 * 实际记录日志
	 * @param operaterId
	 * @param recordEntity
	 */
	public abstract void recordLog(int operaterId,String logContent);
	
	/**
	 * 拼接日志记录内容
	 * @param recordEntity
	 */
	public abstract String getLogContentByParam();
}
