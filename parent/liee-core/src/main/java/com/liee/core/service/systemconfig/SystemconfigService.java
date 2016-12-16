package com.liee.core.service.systemconfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jdao.base.QueryDao;
import com.jdao.base.Where;
import com.liee.core.common.BasePageModel;
import com.liee.core.dao.Systemconfig;
import com.liee.core.exception.BaseException;
import com.liee.core.service.BaseService;
import com.liee.core.utils.StringUtil;

@Service("systemconfigService")
public class SystemconfigService  extends BaseService {

	/**
	 * 分页查询
	 * @param sr
	 * @param page
	 * @return
	 */
	public BasePageModel<Systemconfig> queryPage(Systemconfig sr,BasePageModel<Systemconfig> page ){
		
		Systemconfig cq = new Systemconfig();
		
		// 查询条件
		List<Where> whereList = new ArrayList<Where>();
		if(!StringUtil.isEmpty(sr.getKeyword())){
			whereList.add(Systemconfig.KEYWORD.LIKE(sr.getKeyword()));
		}
		
		if(!StringUtil.isEmpty(sr.getRemark())){
			whereList.add(Systemconfig.REMARK.LIKE(sr.getRemark()));
		}
		
		sr.where(whereList.toArray(new Where[whereList.size()]));
		cq.where(whereList.toArray(new Where[whereList.size()]));
		
		try {
			// 数据
			sr.limit(page.getPageSize()*(page.getPageNum()-1), page.getPageSize());
			List<Systemconfig> list = sr.query();
			
			// 总条数
			QueryDao qd = cq.query(Systemconfig.ID.count());
			Long totalCount = (Long)qd.fieldValue(1);
			
			page.setTotal(totalCount.intValue());
			page.setRows(list);
			
		} catch (Exception e) {
			log.error("分页查询错误",e);
			throw new BaseException("分页查询错误", e);
		}
		
		return page;
	}
	
	
	/**
	 * 保存或者更新
	 * @param systemconfig
	 */
	public void saveOrUpdate(Systemconfig systemconfig){
		
		// 根据名称判断唯一
		try {
			
			Systemconfig old = new Systemconfig();
			old.where(Systemconfig.KEYWORD.EQ(systemconfig.getKeyword()));
			if(systemconfig.getId()!=0){  // 更新
				old.where(Systemconfig.ID.NEQ(systemconfig.getId()));
			}
			List<Systemconfig> existList = old.query();
			if(existList!=null && existList.size()>0){
				throw new BaseException("该系统参数名称已经存在！");
			}
			
			if(systemconfig.getId()!=0){  //更新
				systemconfig.where(Systemconfig.ID.EQ(systemconfig.getId()));
				systemconfig.update();
			}else{  // 新增
				systemconfig.save();
			}
			
		} catch (Exception e) {
			log.error("保存系统参数失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());  // 抛出异常到controller
		}
	}
	
	/**
	 * 根据id删除系统参数
	 * @param id
	 */
	public void delete(int id){
		try {
			Systemconfig old = new Systemconfig();
			old.where(Systemconfig.ID.EQ(id));
			old.delete();
		} catch (SQLException e) {
			log.error("删除系统参数失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());  // 抛出异常到controller
		}
	}
	
	
}
