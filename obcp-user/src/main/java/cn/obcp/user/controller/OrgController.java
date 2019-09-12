package cn.obcp.user.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.user.Exception.VerifyException;
import cn.obcp.user.domain.TOrg;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.dto.OrgDto;
import cn.obcp.user.enmu.StatusEnmu;
import cn.obcp.user.service.OrgService;
import cn.obcp.user.service.UserExtendService;

// ##remain#import#

/**
 * <pre>
  * sys_org的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@RequestMapping("/v1/org/")
public class OrgController extends BaseController<TOrg, Long> {

	@Autowired
	private OrgService orgService;

	@Autowired
	private UserExtendService userExtendService;

	@Autowired
	@Override
	public void setEntityService(@Qualifier("scOrgService") BaseService<TOrg, Long> entityService) {
		this.entityService = entityService;

	}

	@RequestMapping(value = "orgList")
	public LayUiRetData orgList(OrgDto orgDto) {
		try {
			// 获取参数
			Map<String, Object> params = OrgDto.getParamBuDto(orgDto);
			Page<TOrg> page = PageUtils.getPageInfo(params);
			Page<TOrg> pageInfo = orgService.findByPage(page);
			return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return LayUiRetData.error("获取失败！");
		}
	}

	@RequestMapping("saveOrg")
	public RetData saveOrg(@Valid TOrg org, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new VerifyException(bindingResult.getAllErrors()).getErrMsg();
			} else {
				Date now = Calendar.getInstance().getTime();
				TUserExtend userExtend = userExtendService.getCurrentUser();
				org.setCreator(userExtend.getId());// 创建人
				if (org.getId() != null) {// todo:update
					org.setCode(null);//
					org.setUpdatetime(now);
					orgService.update(org);
					return RetData.succuess();
				} else {// todo:insert
					org.setId(uuidCreator.id());
					org.setStatus(StatusEnmu.NORMAL.getType());
					org.setCreatetime(now);
					orgService.save(org);
					return RetData.succuess();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RetData.error("服务端异常");
		}
	}

	@RequestMapping("orgInfo")
	public RetData orgInfo(@RequestParam Long userId) {
		try {
			TOrg org = orgService.findBySupervisor(userId);
			if (org == null) {
				return RetData.error("机构不存在");
			}
			return RetData.succuess(org);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RetData.error("服务端异常");
		}
	}
	// ##remain#property#

}
