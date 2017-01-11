package com.liee.core.service.syscode;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.liee.core.dao.SysCode;
import com.liee.core.exception.BaseException;
import com.liee.core.service.BaseService;
import com.liee.core.utils.StringUtil;

@Service("sysCodeService")
public class SysCodeService extends BaseService {

	
	/**
	 * 查询所有数据字典
	 * @return
	 */
	public List<SysCode> getAllCode(){
		List<SysCode> menuList = new ArrayList<SysCode>();
		
		SysCode q = new SysCode();
		q.sort(SysCode.SORT.asc(),SysCode.DISPLAYORDER.asc());
		try {
			menuList = (List<SysCode>)q.query();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return menuList;
	}
	
	
	/**
	 * 保存
	 * @param menu
	 */
	public void saveOrUpdate(SysCode code){
		// 根据名称判断唯一
		try {
			
			SysCode old = new SysCode();
			old.where(SysCode.CODE.EQ(code.getCode()),SysCode.PARENTCODE.EQ(code.getParentCode()));
			if(code.getId()!=0){  // 更新
				old.where(SysCode.ID.NEQ(code.getId()));
			}
			List<SysCode> existList = old.query();
			if(existList!=null && existList.size()>0){
				throw new BaseException("相同父结点下不能用相同编号的子结点！");
			}
			
			if(code.getId()!=0){  //更新
				code.where(SysCode.ID.EQ(code.getId()));
				code.update();
			}else{  // 新增
				// 设置level
				if(!StringUtil.isEmpty(code.getParentCode())){
					SysCode parent = new SysCode();
					parent.where(SysCode.CODE.EQ(code.getParentCode()));
					parent = parent.queryById();
					if(parent.getSort()>1){
						throw new BaseException("目前只支持两级数据字典！");
					}else{
						code.setSort(parent.getSort()+1); 
					}
				}else{
					code.setSort(1); 
				}
				code.save();
			}
			
		} catch (Exception e) {
			log.error("保存数据字典失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());   // 抛出异常到controller
		}
	}
	
}
