
package cn.obcp.user.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.user.domain.TOrg;
import cn.obcp.user.domain.TOrgManager;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.dto.OrgManagerDto;
import cn.obcp.user.service.OrgManagerService;
import cn.obcp.user.service.OrgService;
import cn.obcp.user.service.UserDepartmentService;
import cn.obcp.user.service.UserExtendService;
import io.swagger.annotations.ApiOperation;

// ##remain#import#

/**
 * <pre>
 * sys_user_department的Controller
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@RequestMapping("/v1/orgManager/")
public class OrgManagerController extends BaseController<TOrgManager, Long> {

    Logger logger = LoggerFactory.getLogger(OrgManagerController.class);

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Autowired
    private OrgManagerService orgManagerService;

    @Autowired
    private UserExtendService userExtendService;

    @Autowired
    private OrgService orgService;

    @Autowired
    @Override
    public void setEntityService(
            @Qualifier("scorgManagerService") BaseService<TOrgManager, Long> entityService) {
        this.entityService = entityService;

    }

    @ApiOperation("机构管理员列表")
    @RequestMapping("index")
    public LayUiRetData index(OrgManagerDto orgManagerDto){
        try {
            Map<String, Object> params = OrgManagerDto.getParamBuDto(orgManagerDto);
            Page<TOrgManager> page = PageUtils.getPageInfo(params);
            Page<TOrgManager> pageInfo = orgManagerService.findByOrgManagerPage(page);
            return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
        }catch (Exception e){
            return LayUiRetData.error("0","获取失败");
        }
    }

    @ApiOperation("添加机构管理人")
    @RequestMapping("addOrgManagerByList")
    public RetData addOrgManagerByList(@RequestParam(value = "userIds[]")  List<String> userIds,@RequestParam Long orgId){
        try{
            TOrg org = orgService.findById(orgId);
            TUserExtend userExtend = userExtendService.getCurrentUser();
            if (null == org){
                return RetData.error("机构不存在！");
            }
            if (!String.valueOf(userExtend.getId()).equalsIgnoreCase(org.getSupervisor()) && !userExtendService.currIsAdmin()){
                return RetData.error("非机构负责人不能添加机构管理员");
            }
            List<TUserExtend> userExtends = userExtendService.findByIds(String.join(",", userIds));
            if (userExtends != null && userExtends.size() != userIds.size()) {
                return RetData.error("选择用户不匹配，请重新确认！");
            }
            boolean res = orgManagerService.addManager(userIds,orgId);
            if (res)
                return RetData.succuess();
            else
                return RetData.error("添加失败");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return RetData.error("添加失败！");
        }
    }

    /**
     *  移除管理员
     * @param id
     * @return
     */
    @ApiOperation(" 移除机构管理员")
    @RequestMapping("remove")
    public RetData remove(@RequestParam Long id){
        try{
            TOrgManager orgManager = orgManagerService.findById(id);
            if (orgManager != null){
                TUserExtend userExtend = userExtendService.getCurrentUser();
                TOrg org = orgService.findById(Long.valueOf(orgManager.getOrgId()));
                if (org == null){
                    return RetData.error("无效的机构");
                }
                if (org.getSupervisor().equalsIgnoreCase(userExtend.getId().toString())
                    || userExtendService.currIsAdmin()){
                    boolean res = orgManagerService.delete(orgManager);
                    if (res){
                        return RetData.succuess("删除成功！");
                    }else {
                        return RetData.error("删除失败！");
                    }
                }else {
                    return RetData.error("非机构负责人不能删除机构管理员！");
                }
            }
            return RetData.error("管理员不存在！");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return RetData.error("服务端异常！");
        }
    }

    // ##remain#property#

}