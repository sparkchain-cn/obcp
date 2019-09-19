package cn.obcp.user.service;

import java.util.List;

// ##remain#import#

import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.service.BaseService;
import cn.obcp.user.VO.NavsNodeTree;
import cn.obcp.user.VO.RoleResourceVo;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserToken;

/**
 * <pre>
 * 用户表的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface UserExtendService extends BaseService<TUserExtend, Long> {



	/**
	 * TODO
	 * @param loginName
	 * @return
	 * TUserExtend
	 * lmf 创建于2018年11月15日
	 */
	TUserExtend findByLogin(String loginName);

	/**
	 * TODO
	 * @param mobileNum
	 * @return
	 * boolean
	 * lmf 创建于2018年11月16日
	 */
	boolean checkMobileNum(String mobileNum);

	/**
	 * TODO
	 * @param page
	 * @return
	 * Page<TUserExtend>
	 * lmf 创建于2018年11月17日
	 */
	Page<TUserExtend> findBindByPage(Page<TUserExtend> page);

	// ##remain#method#

	/**
	 *  获取当前用户
	 * TODO
	 * @return
	 * TUserExtend
	 * lmf 创建于2018年11月21日
	 */
	TUserExtend getCurrentUser();
	
	/**
	 *  获取当前用户权限
	 * TODO
	 * @return
	 * List<TreeNode>
	 * lmf 创建于2018年11月21日
	 */
	List<NavsNodeTree> getUserResource();
	
	/**
	 * TODO 判断用户是否是平台业务管理员
	 * @param userId
	 * @return
	 * boolean
	 * lmf 创建于2018年11月22日
	 */
	public Boolean isAdmin(Long userId) ;


	/**
	 *  todo:判断用户是否是系统超级管理员
	 * @param userId
	 * @return
	 */
	public Boolean isSysAdmin(Long userId);
	// 根据属性找到唯一的方法
	
	/**
	 * logout
	 * TODO
	 * void
	 * lmf 创建于2018年11月23日
	 */
	public void logout();

	/**
	 * TODO
	 * @param userid
	 * @param password
	 * @return
	 * int
	 * lmf 创建于2018年11月26日
	 */
	int updatePassword(Long userid, String password);

	boolean checkValidateCode(String key, String validateCode);

	Page<TUserExtend> findByPage(Page<TUserExtend> page);

	/**
	 * TODO
	 * @param page
	 * @return
	 * Page<TUserExtend>
	 * lmf 创建于2018年11月27日
	 */
	Page<TUserExtend> getNoBindByRole(Page<TUserExtend> page);

	/**
	 * TODO
	 * @param page
	 * @return
	 * Page<TUserExtend>
	 * lmf 创建于2018年11月27日
	 */
	Page<TUserExtend> getBindByRole(Page<TUserExtend> page);

	Page<TUserExtend> searchByOr(Page<TUserExtend> page);

	Page<TUserExtend> findCompanyByPage(Page<TUserExtend> page);


	RetData verifyUser(Long userId, Long verifyuser, int state, String explain);

	TUserExtend findUserById(Long id);

	Page<RoleResourceVo> getRoleResourceByUser(Page<RoleResourceVo> page);

    boolean updateUserPrincipalCollection(TUserExtend user);

	/**
	 *  验证当前用户是否是管理员
	 * @return
	 */
	boolean currIsAdmin();

	String createUserToken(TUserExtend user) throws Exception;

    RetData runWith(TUserToken userToken);

    Page<TUserExtend> findUserWithoutOrg(Page<TUserExtend> page);
    
    List<TUserExtend> finUserAll();
}
