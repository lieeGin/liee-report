package com.liee.core.service.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jdao.base.QueryDao;
import com.jdao.base.Where;
import com.liee.core.common.BasePageModel;
import com.liee.core.dao.SysUser;
import com.liee.core.dao.SysUserRole;
import com.liee.core.exception.BaseException;
import com.liee.core.service.BaseService;
import com.liee.core.utils.Md5Util;
import com.liee.core.utils.StringUtil;

@Service("userService")
public class UserService  extends BaseService {

	private static String defaultPassword = "123456";
	
	/**
	 * 分页查询
	 * @param sr
	 * @param page
	 * @return
	 */
	public BasePageModel<SysUser> queryPage(SysUser sr,BasePageModel<SysUser> page ){
		
		SysUser cq = new SysUser();
		
		// 查询条件
		List<Where> whereList = new ArrayList<Where>();
		if(!StringUtil.isEmpty(sr.getUserName())){
			whereList.add(SysUser.USERNAME.LIKE(sr.getUserName()));
		}
		
		sr.where(whereList.toArray(new Where[whereList.size()]));
		cq.where(whereList.toArray(new Where[whereList.size()]));
		
		try {
			// 数据
			sr.limit(page.getPageSize()*(page.getPageNum()-1), page.getPageSize());
			List<SysUser> list = sr.query();
			
			// 总条数
			QueryDao qd = cq.query(SysUser.ID.count());
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
	 * @param user
	 */
	public void saveOrUpdate(SysUser user){
		
		// 根据名称判断唯一
		try {
			
			SysUser old = new SysUser();
			old.where(SysUser.USERNAME.EQ(user.getUserName()));
			if(user.getId()!=0){  // 更新
				old.where(SysUser.ID.NEQ(user.getId()));
			}
			List<SysUser> existList = old.query();
			if(existList!=null && existList.size()>0){
				throw new BaseException("该用户账号已经存在！");
			}
			
			if(user.getId()!=0){  //更新
				// 设置部分值，以免不被覆盖。
				SysUser dbEntity = new SysUser();
				dbEntity.where(SysUser.ID.EQ(user.getId()));
				dbEntity = dbEntity.queryById();
				user.setPassword(dbEntity.getPassword());
				user.setRegisterTime(dbEntity.getRegisterTime());
				user.setLastLoginTime(dbEntity.getLastLoginTime());
				
				user.where(SysUser.ID.EQ(user.getId()));
				user.update();
			}else{  // 新增
				// 创建、注册时间
				user.setRegisterTime(new Date());
				// 默认密码
				user.setPassword(Md5Util.encrypt(defaultPassword));
				user.save();
			}
			
		} catch (Exception e) {
			log.error("保存用户失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());   // 抛出异常到controller
		}
	}
	
	/**
	 * 根据id删除用户
	 * @param id
	 */
	public void delete(int id){
		try {
			SysUser old = new SysUser();
			old.where(SysUser.ID.EQ(id));
			old.delete();
		} catch (SQLException e) {
			log.error("删除用户失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());   // 抛出异常到controller
		}
	}
	
	
	/**
	 * 保存该用户的角色信息，先删后插
	 * @param userId
	 * @param roleIds
	 */
	public void saveUserRoles(int userId,String roleIds){
		
		try {
			// 先删除
			SysUserRole ur = new SysUserRole();
			ur.where(SysUserRole.USERID.EQ(userId));
			ur.delete();
			// 再保存
			if(!StringUtils.isEmpty(roleIds)){
				String[] roleIdArr = roleIds.split(",");
				if(roleIdArr!=null && roleIdArr.length > 0){
					for(int i=0;i<roleIdArr.length;i++){
						SysUserRole entity = new SysUserRole();
						entity.setUserId(userId);
						entity.setRoleId(Integer.parseInt(roleIdArr[i]));
						
						entity.save();
					}
				}
			}
			
		} catch (SQLException e) {
			log.error("保存用户角色失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());  // 抛出异常到controller
		}
	}
	
	
	/**
	 * 修改用户密码
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 */
	public void updatePassword(int userId, String oldPwd, String newPwd){
		
		try{
			SysUser u = new SysUser();
			u.where(SysUser.ID.EQ(userId));
			u = u.queryById();
			
			if(u.getPassword().equals(Md5Util.encrypt(oldPwd))){
				u.setPassword(Md5Util.encrypt(newPwd));
				u.where(SysUser.ID.EQ(userId));
				u.update();
			}else{
				throw new BaseException("原密码输入错误！");
			}
			
		}catch(Exception e){
			log.error("修改用户密码失败",e);
			throw new BaseException(e.getMessage()); 
		}
	}
	
	
}
