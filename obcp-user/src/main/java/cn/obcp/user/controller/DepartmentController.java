package cn.obcp.user.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.user.Exception.VerifyException;
import cn.obcp.user.VO.OrgTreeNode;
import cn.obcp.user.domain.TDepartment;
import cn.obcp.user.enmu.StatusEnmu;
import cn.obcp.user.service.DepartmentService;

// ##remain#import#

/**
 * <pre>
  * sys_department的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@RequestMapping("/v1/department/")
public class DepartmentController extends BaseController<TDepartment, Long> {

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	@Override
	public void setEntityService(@Qualifier("scDepartmentService") BaseService<TDepartment, Long> entityService) {
		this.entityService = entityService;
	}

	@RequestMapping("deptTree")
	public List<OrgTreeNode> deptTree(@RequestParam("orgId") String orgId) {
		try {
			Map params = new HashMap(){{put("orgId",orgId);}};
			Page<TDepartment> page = PageUtils.getPageInfo(params);
			page.setNeedPage(false);
			page = entityService.findByPage(page);
			List<TDepartment> departments =  page.getResult();
			List<OrgTreeNode> orgTreeNodes = new OrgTreeNode().bulid(departments);
			return orgTreeNodes;
		}catch (Exception e){
			logger.error(e.getMessage());
			return null;
		}
	}

	@RequestMapping("saveDept")
	public RetData saveDept(@Valid TDepartment department, BindingResult bindingResult){
		try {
			if (bindingResult.hasErrors()) {
				return new VerifyException(bindingResult.getAllErrors()).getErrMsg();
			} else {
				if (department.getId() != null){
					entityService.update(department);
					return RetData.succuess(); 
				}else {
					//todo:save
					department.setId(uuidCreator.id());
					department.setCreatetime(Calendar.getInstance().getTime());
					department.setStatus(StatusEnmu.NORMAL.getType());
					entityService.save(department);
					return RetData.succuess();
				}
			}
		}catch (Exception e){
			return RetData.error("服务端异常");
		}
	}

	@RequestMapping("deptInfo")
	public RetData deptInfo(@RequestParam Long id){
		try{
			TDepartment department = entityService.findById(id);
			return RetData.succuess(department);
		}catch (Exception e){
			return RetData.error("服务端异常");
		}
	}

	// ##remain#property#

}