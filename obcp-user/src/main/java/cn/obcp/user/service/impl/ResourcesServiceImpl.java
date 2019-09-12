package cn.obcp.user.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.base.utils.StringUtils;
import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TResources;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.enmu.ResourceAddrType;
import cn.obcp.user.enmu.ResourceStateEnmu;

// ##remain#import#

import cn.obcp.user.mapper.ResourcesMapper;
import cn.obcp.user.mapper.RoleResourcesMapper;
import cn.obcp.user.service.ResourcesService;

/**
 * <pre>
  * 资源的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("bsResourcesService")
public class ResourcesServiceImpl extends BaseServiceImpl<TResources, Long> implements ResourcesService {

	protected final static Logger logger = LoggerFactory.getLogger(ResourcesServiceImpl.class);

	@Value("${com.sparkchain.admin.roleId}")
	private String adminRole;

	@Resource
	private ResourcesMapper resourcesMapper;
	// ##remain#property#

	@Autowired
	private cn.obcp.user.service.RoleResourcesService roleResourcesService;

	@Autowired
	private RoleResourcesMapper roleResourcesMapper;

	ResourcesServiceImpl() {
		super();
		this.objClz = TResources.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("resourcesMapper") BaseMapper<TResources> baseMapper) {
		this.baseMapper = baseMapper;

	}

	@Transactional(readOnly = false)
	public RetData saveRes(TResources resource) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		if (resource.getParentid() != null) { // 存在父级
			TResources resourcesParent = findById(resource.getParentid());// 父级资源
			if (resourcesParent != null && null != resourcesParent.getResourceGroup()
					&& resourcesParent.getResourceGroup() != resource.getResourceGroup()) {// 资源存在父资源且与父资源分组不同
				return RetData.error("资源必须和父资源分组相同！");
			}
		}
		TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
		Date datetime = calendar.getTime();
		resource.setId(uuidCreator.id());
		resource.setResAddrType(ResourceAddrType.SERVICE.getType()); // 接口类型
		resource.setState(ResourceStateEnmu.NORMAL.getStatus());// 正常

		resource.setCreatetime(datetime);
		resource.setUpdatetime(datetime);
		resource.setCreator(user.getId());
		// resource.setSystemid(systemId); //读取配置文件
		if (null == resource.getSortnum()) {
			Integer max = resourcesMapper.getMaxSortNum(new Page<>());
			if (null == max) {
				max = 1;
			}
			resource.setSortnum(max + 1);
		}

		boolean result = super.save(resource);
		if (result)
			return RetData.succuess();
		return RetData.error("添加出错！");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.obcp.base.service.BaseServiceImpl#update(java.lang.Object)
	 */

	@Override
	@Transactional(readOnly = false)
	public boolean update(TResources entity) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
		/*
		 * if(entity.getParentid() != null) { //存在父级 TResources resourcesParent
		 * =findById(entity.getParentid());//父级资源 if(resourcesParent == null) { return
		 * false; } }
		 */
		Date datetime = calendar.getTime();
		entity.setUpdatetime(datetime);
		entity.setUpdater(user.getId());
		return super.update(entity);
	}

	/**
	 * 查询用户已关联资源（默认控制台资源）
	 */
	@Override
	public List<TResources> getUserTreeList(Long userId) {
		return findResourceByUserId(userId, UserConstans.RESOURCE_CONSOULE_GROUP);
	}

	/**
	 * 根据用户查询资源（根据分组）
	 * 
	 * @param userId
	 * @param isAppGroup
	 * @return
	 */
	public List<TResources> findResourceByUserId(Long userId, int isAppGroup) {

		return resourcesMapper.findResourceByUserId(userId, isAppGroup);
	}

	/**
	 * 根据父级id查询资源
	 */
	@Override
	public LayUiRetData findByParent(String name, Long parentid, int pageNo, int limit) {
		try {
			TResources resources = findById(parentid);
			LayUiRetData ret = new LayUiRetData();
			if (resources != null) {
				Map<String, Object> param = new HashMap<>();
				param.put("parentid", parentid);
				if (StringUtils.isNotNullOrEmpty(name)) {
					param.put("name", name);
				}
				param.put("state", "");
				Page<TResources> page = new Page<>();
				page.setStartRow((pageNo - 1) * limit);
				page.setSize(limit);
				page.setMeta(param);

				List<TResources> resourceList = resourcesMapper.findByPage(page);

				if (resourceList != null && resourceList.size() > 0) {
					resourceList.forEach(r -> {
						if (r.getParentid() != null) {
							TResources parentNode = findById(r.getParentid());
							if (parentNode != null) {
								r.setParentName(parentNode.getName());
							}
						}
						if (r.getResTypeCode() != null) {
							r.setTypeName("类型名称（字典暂未关联）");
						}

					});
				}
				int count = resourcesMapper.countByPage(page);
				ret.setCode("0");
				ret.setCount(Long.valueOf(count));
				ret.setData(resourceList);
				return ret;
			}
			return LayUiRetData.error("参数错误！");
		} catch (Exception e) {
			// TODO: handle exception
			return LayUiRetData.error("发生错误！");
		}
	}

	/**
	 * 删除资源
	 */
	@Override
	@Transactional(readOnly = false)
	public RetData remove(Long id) {
		try {
			TResources resources = findById(id);
			if (resources != null) {
				Map<String, Object> map = new HashMap<>();
				map.put("parentid", id);
				Page<TResources> page = PageUtils.getPageInfo(map);
				int childrenNum = resourcesMapper.countByPage(page);
				if (childrenNum > 0) {
					return RetData.error("资源存在子级资源，不能删除！");
				} else if (roleResourcesMapper.hadRoleRes(id)) {
					return RetData.error("资源已关联角色，请先解除关联关系！");
				} else {
					int result = resourcesMapper.delete(resources);
					/*
					 * resources.setState(ResourceStateEnmu.DELETED.getStatus()); boolean result =
					 * update(resources);
					 */
					if (result > 0) {
						logger.info("删除资源成功");
						return RetData.succuess("删除成功！");
					} else {
						logger.info("删除资源失败");
						return RetData.error("删除失败！");
					}
				}
			} else {
				return RetData.error("资源不存在！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("删除资源出错");
			return RetData.error("发生错误！");
		}
	}

	/**
	 * 通过map参数进行查询
	 */
	@Override
	public Page<TResources> findByMap(int number, int size, Map<String, Object> params) {
		Map<String, Object> paramMap = new HashMap<>();
		Iterator<Entry<String, Object>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> en = iter.next();
			if (en.getValue() != null && StringUtils.isNotNullOrEmpty(String.valueOf(en.getValue()))) {
				paramMap.put(en.getKey(), en.getValue());
			}
		}
		Page<TResources> page = new Page<>();
		page.setMeta(paramMap);
		long total = resourcesMapper.countByPage(page);
		page = new Page<>(number, size);
		List<TResources> list = resourcesMapper.findByPage(page);
		page.setResult(list);
		page.setTotal(total);
		return page;
	}

	/**
	 * 通过ID查询
	 */
	@Override
	public TResources findResourceById(Long id) {
		return resourcesMapper.findResourceById(id);
	}

	/**
	 * 查询父级资源
	 */
	@Override
	public TResources findParentResourceById(Long id) {
		return resourcesMapper.findParentResourceById(id);
	}

	/**
	 * 通过角色查询
	 */
	@Override
	public List<TResources> findByRole(Long roleId) {
		return resourcesMapper.findByRole(roleId);
	}

	/**
	 * 通过page进行查询
	 */
	@Override
	public Page<TResources> findBindByPage(Page<TResources> page) {
		List<TResources> list = resourcesMapper.findBindByPage(page);
		list.forEach(u -> {
			u.setChecked(u.getHasRole() > 0);
		});
		page.setResult(list);
		return page;
	}

	/**
	 * 非管理员用户查询角色权限
	 * 
	 * @param page
	 * @return
	 */
	public Page<TResources> findUserResBindByPage(Page<TResources> page) {
		List<TResources> list = resourcesMapper.findUserResBindByPage(page);
		list.forEach(u -> {
			System.out.println(JSON.toJSONString(u));
			try {
				if (u.getHasRole() == null) {
					u.setChecked(false);
				} else
					u.setChecked(u.getHasRole() > 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		page.setResult(list);
		return page;
	}

	/**
	 * 
	 */
	/*
	 * @Override public List<TResources> findAll(){ return
	 * resourcesMapper.findAll(); }
	 */

	/**
	 * 验证code是否已存在
	 */
	public boolean exitCode(String code) {
		TResources resources = new TResources();
		resources.setCode(code);
		int count = resourcesMapper.selectCount(resources);
		return count > 0;
	}

	/**
	 * 获取根节点数量
	 */
	public long getRootCount() {
		return resourcesMapper.getRootCount();
	}

	/**
	 * 获取根节点数量
	 */
	public List<TResources> findRoot(Page<TResources> page) {
		return resourcesMapper.findRoot(page);
	}

	/**
	 * 管理员角色查看资源
	 */
	public Page<TResources> findByAdmin(Page<TResources> page) {

		List<TResources> list = resourcesMapper.findBindByPage(page);
		list.forEach(u -> {
			if (u.getHasRole() == null) {
				u.setChecked(false);
			} else
				u.setChecked(u.getHasRole() > 0);
		});
		page.setResult(list);
		return page;
	}

	public List<TResources> getAllMenuResource() {
		return resourcesMapper.getAllMenuResource();
	}

	public List<TResources> getAllResource(TResources res) {
		return resourcesMapper.getAllResource(res);
	}

	public RetData addByList(List<TResources> resList) {
		if (resList.size() > 0) {
			try {
				resourcesMapper.addList(resList);
			} catch (Exception e) {
				return RetData.error("创建失败");
			}
		}
		return RetData.succuess(String.format("创建成功，添加%s个新资源", resList.size()));
	}

	private List<TResources> setResourceParent(List<TResources> resList, List<TResources> serviceResource) {
		resList = resList.stream().map(r -> {
			r.setParentid(getParentId(r.getCode(), serviceResource));
			return r;
		}).collect(Collectors.toList());
		return resList;
	}

	private Long getParentId(String code, List<TResources> serviceResource) {
		logger.info(code + "++++++++++++++++++++++++++ code");
		serviceResource = serviceResource.stream().filter(r -> code.startsWith(r.getCode()))
				.collect(Collectors.toList());

		if (null != serviceResource && serviceResource.size() > 0) {
			logger.info(serviceResource.get(0).getCode() + "+++++++++++++++ serviceCode");
			return serviceResource.get(0).getId();
		}
		logger.info("无++++++++++++++++++");
		return null;
	}

	/**
	 * 判断是否已存在
	 * 
	 * @param code
	 * @param existResource
	 * @return
	 */
	public boolean checkExist(String code, List<TResources> existResource) {
		boolean exist = true;
		for (TResources r : existResource) {
			if (code.equalsIgnoreCase(r.getCode())) {
				exist = false;
				break;
			}
		}
		return exist;
	}

	/**
	 * 查询全部资源
	 * 
	 * @return
	 */
	public List<TResources> findAllRes() {
		List<TResources> list = resourcesMapper.findAllRes();
		list.forEach(u -> {
			u.setChecked(true);
		});
		return list;
	}

	/**
	 * 修改资源父级
	 * 
	 * @param ids
	 * @param parentId
	 * @return
	 */
	public RetData updateNodeParent(List<String> ids, Long parentId, String moveType) {
		int startNum = 0;// 初始排序号
		TResources parentResource = findById(parentId);
		if (null != parentResource) {
			startNum = parentResource.getSortnum();
		}
		// 前后拖拽
		if (moveType.equalsIgnoreCase(UserConstans.TREE_DROP_TYPE_PREV)
				|| moveType.equalsIgnoreCase(UserConstans.TREE_DROP_TYPE_NEXT)) {
			// 获取移动资源的数据,过滤出和目标节点是否是在统一父级下的
			List<TResources> parents = resourcesMapper.findByIds(ids);

			if (parents.size() > 0) { // 跨父级拖拽,先将节点全部移入同一父级
				parents = parents.stream().filter(r -> !r.getParentid().equals(parentResource.getParentid()))
						.collect(Collectors.toList());
				if (parents.size() > 0) {
					// 如果移动的数据中有父节点不等于目标节点，且移动类型为prev|next ,则是跨父级上下移动,先要修改父节点
					RetData retData = updateParent(ids, parentResource.getParentid(), startNum);
					if (!retData.isSuccess()) {
						return retData;
					}
				}
			}
			if (moveType.equalsIgnoreCase(UserConstans.TREE_DROP_TYPE_NEXT)) {
				// 移到parentid后,将parentid同级和之后数据全部往后移动一位
				resourcesMapper.setSortToNext(parentResource.getParentid(), parentId, startNum);
				// 将选择的节点放在目标资源后一位
				resourcesMapper.updateNodeSortNum(ids, parentResource.getSortnum() + 1);
				return RetData.succuess();
			} else if (moveType.equalsIgnoreCase(UserConstans.TREE_DROP_TYPE_PREV)) {
				// 同父级下，排序号大于等于target的节点，全部往后移动一位
				resourcesMapper.setSortToPrev(parentResource.getParentid(), startNum);
				// 选择资源设置为目标资源排序
				resourcesMapper.updateNodeSortNum(ids, parentResource.getSortnum());
				return RetData.succuess();
			}
		}
		// 移入子级
		if (moveType.equalsIgnoreCase(UserConstans.TREE_DROP_TYPE_INNER)) {
			return updateParent(ids, parentId, startNum);
		}

		return RetData.error("修改失败");
	}

	private RetData updateParent(List<String> ids, Long parentId, Integer sortnum) {
		int count = 0;
		if (null == parentId) {
			count = resourcesMapper.updateNodeParentNull(ids, sortnum);
		} else {
			count = resourcesMapper.updateNodeParent(ids, parentId, sortnum);
		}
		if (count > 0) {
			return RetData.succuess();
		}
		return RetData.error("失败");
	}

	public List<TResources> excludeExist(List<TResources> resList) {
		List<String> codes = resList.stream().map(r -> r.getCode()).collect(Collectors.toList());
		List<TResources> resources = resourcesMapper.findByResourcesCodeList(codes);
		return resources;
	}

}