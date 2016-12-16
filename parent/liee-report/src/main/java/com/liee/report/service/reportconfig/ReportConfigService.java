package com.liee.report.service.reportconfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jdao.base.QueryDao;
import com.jdao.base.Where;
import com.liee.core.common.BasePageModel;
import com.liee.core.exception.BaseException;
import com.liee.core.service.BaseService;
import com.liee.core.utils.StringUtil;
import com.liee.report.dao.RepReport;
import com.liee.report.dao.RepReportColumn;
import com.liee.report.dao.RepReportParam;

@Service("reportConfigService")
public class ReportConfigService  extends BaseService {

	/**
	 * 分页查询
	 * @param sr
	 * @param page
	 * @return
	 */
	public BasePageModel<RepReport> queryPage(RepReport sr,BasePageModel<RepReport> page ){
		
		RepReport cq = new RepReport();
		
		// 查询条件
		List<Where> whereList = new ArrayList<Where>();
		if(!StringUtil.isEmpty(sr.getName())){
			whereList.add(RepReport.NAME.LIKE(sr.getName()));
		}
		
		if(!StringUtil.isEmpty(sr.getCode())){
			whereList.add(RepReport.CODE.LIKE(sr.getCode()));
		}
		
		sr.where(whereList.toArray(new Where[whereList.size()]));
		cq.where(whereList.toArray(new Where[whereList.size()]));
		
		try {
			// 数据
			sr.limit(page.getPageSize()*(page.getPageNum()-1), page.getPageSize());
			List<RepReport> list = sr.query();
			
			// 总条数
			QueryDao qd = cq.query(RepReport.ID.count());
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
	public void saveOrUpdate(RepReport report,List<RepReportParam> paramList,List<RepReportColumn> columnList, String groovyStr){
		
		try {
			
			RepReport old = new RepReport();
			old.where(RepReport.CODE.EQ(report.getCode()));
			if(report.getId()!=0){  // 更新
				old.where(RepReport.ID.NEQ(report.getId()));
			}
			List<RepReport> existList = old.query();
			if(existList!=null && existList.size()>0){
				throw new BaseException("该报表编号已经存在！");
			}
			
			if(report.getId()!=0){  //更新
				report.where(RepReport.ID.EQ(report.getId()));
				report.update();
				
				// 更新子表  
				saveOrUpdateColumn(report.getId(),columnList);
				saveOrUpdateParam(report.getId(),paramList);
			}else{  // 新增
				int id = report.saveAndGetLastInsertId4MYSQL();
				// 保存子表  
				saveOrUpdateColumn(id,columnList);
				saveOrUpdateParam(id,paramList);
			}
			
		} catch (Exception e) {
			log.error("保存报表失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());  // 抛出异常到controller
		}
	}
	
	
	/**
	 * 保存或更新结果列
	 * @param reportId
	 * @param columnList
	 */
	public void saveOrUpdateColumn(int reportId,List<RepReportColumn> columnList){
		
		try {
			// 先删除
			RepReportColumn cq = new RepReportColumn();
			cq.where(RepReportColumn.REPORTID.EQ(reportId));
			cq.delete();
			
			//再保存
			for (RepReportColumn column : columnList) {
				column.setReportId(reportId);
				column.save();
			}
			
		} catch (Exception e) {
			log.error("保存结果列失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());  // 抛出异常到controller
		}
		
	}
	
	
	/**
	 * 保存或更新参数
	 * @param reportId
	 * @param paramList
	 */
	public void saveOrUpdateParam(int reportId,List<RepReportParam> paramList){

		try {
			// 先删除
			RepReportParam cq = new RepReportParam();
			cq.where(RepReportParam.REPORTID.EQ(reportId));
			cq.delete();
			
			//再保存
			for (RepReportParam param : paramList) {
				param.setReportId(reportId);
				param.save();
			}
			
		} catch (Exception e) {
			log.error("保存参数失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());  // 抛出异常到controller
		}
		
	}
	
	/**
	 * 根据id删除报表
	 * @param id
	 */
	public void delete(int id){
		try {
			RepReport old = new RepReport();
			old.where(RepReport.ID.EQ(id));
			old.delete();
		} catch (SQLException e) {
			log.error("删除报表失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());  // 抛出异常到controller
		}
	}
	
	
}
