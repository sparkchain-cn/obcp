package cn.obcp.user.service;

// ##remain#import#

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.service.BaseService;
import cn.obcp.user.domain.TResources;

/**
 * <pre>
 * 资源的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface ResourcesService extends BaseService<TResources, Long> {

	// ##remain#method#

	// 根据属性找到唯一的方法
	// public List<TResources> findByRole(Long roleId);

	public List<TResources> getUserTreeList(Long userId);

	/**
	 * TODO
	 * 
	 * @param name
	 * @param parentid
	 * @param limit
	 * @param pageNo
	 * @return LayUiRetData lmf 创建于2018年11月14日
	 */
	public LayUiRetData findByParent(String name, Long parentid, int pageNo, int limit);

	/**
	 * TODO
	 * 
	 * @param id
	 * @return RetData lmf 创建于2018年11月14日
	 */
	public RetData remove(Long id);

	/**
	 * TODO
	 * 
	 * @param intValue
	 * @param intValue2
	 * @param params
	 * @return Page<TResources> lmf 创建于2018年11月15日
	 */
	public Page<TResources> findByMap(int intValue, int intValue2, Map<String, Object> params);

	/**
	 * TODO
	 * 
	 * @param id
	 * @return TResources lmf 创建于2018年11月15日
	 */
	public TResources findResourceById(Long id);

	/**
	 * TODO 返回的资源只带有父级id和父级名称
	 * 
	 * @param id
	 * @return TResources lmf 创建于2018年11月15日
	 */
	public TResources findParentResourceById(Long id);

	/**
	 * TODO
	 * 
	 * @param id
	 * @return List<TResources> lmf 创建于2018年11月15日
	 */
	public List<TResources> findByRole(Long id);

	/**
	 * TODO
	 * 
	 * @param page
	 * @return Page<TResources> lmf 创建于2018年11月18日
	 */
	public Page<TResources> findBindByPage(Page<TResources> page);

	/**
	 * TODO
	 * 
	 * @param code
	 * @return boolean lmf 创建于2018年11月26日
	 */
	public boolean exitCode(String code);

	/**
	 * TODO
	 * 
	 * @param page
	 * @return List<TResources> lmf 创建于2018年11月27日
	 */
	public List<TResources> findRoot(Page<TResources> page);

	/**
	 * TODO
	 * 
	 * @return long lmf 创建于2018年11月27日
	 */
	public long getRootCount();

	/**
	 * TODO
	 * 
	 * @param page
	 * @return Page<TResources> lmf 创建于2018年11月30日
	 */
	public Page<TResources> findByAdmin(Page<TResources> page);

	List<TResources> findAllRes();

	/**
	 * 获取控制台菜单资源
	 * 
	 * @return
	 */
	public List<TResources> getAllMenuResource();

	/**
	 * 添加资源
	 * 
	 * @param res
	 * @return
	 */
	public RetData saveRes(@Valid TResources res);

	/**
	 * 获取全部资源列表
	 * 
	 * @return
	 */
	public List<TResources> getAllResource(TResources res);

	/**
	 * 根据用户查询资源（根据分组）
	 * 
	 * @param userId
	 * @param resGroup
	 * @return
	 */
	public List<TResources> findResourceByUserId(Long userId, int resGroup);

	/**
	 * 非管理员用户查看角色权限
	 * 
	 * @param page
	 * @return
	 */
	Page<TResources> findUserResBindByPage(Page<TResources> page);

	/**
	 * 批量修改父级
	 * 
	 * @param ids
	 * @param parentId
	 * @return
	 */
	RetData updateNodeParent(List<String> ids, Long parentId, String moveType);

	/**
	 * 除去数据库中已存在的资源编码数据
	 * 
	 * @param resList
	 * @return
	 */
	List<TResources> excludeExist(List<TResources> resList);

	/**
	 * 验证是否存在
	 * 
	 * @param code
	 * @param existResource
	 * @return
	 */
	boolean checkExist(String code, List<TResources> existResource);
}