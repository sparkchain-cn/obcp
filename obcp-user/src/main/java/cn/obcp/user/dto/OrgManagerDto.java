package cn.obcp.user.dto;

import cn.obcp.base.utils.StringUtils;

import java.util.Map;

public class OrgManagerDto extends BaseDto {

    private Long orgId;

    private String userName;

    private String orgName;

    public static Map<String, Object> getParamBuDto(OrgManagerDto orgManagerDto) {
        if (orgManagerDto == null){
            orgManagerDto = new OrgManagerDto();
        }
        Map map = getBasePramMap(orgManagerDto);
        if (orgManagerDto.getOrgId() != null){
            map.put("orgId",orgManagerDto.getOrgId());
        }
        if (StringUtils.isNotNullOrEmpty(orgManagerDto.getUserName())){
            map.put("userName",orgManagerDto.getUserName());
        }
        if (StringUtils.isNotNullOrEmpty(orgManagerDto.getOrgName())){
            map.put("orgName",orgManagerDto.getOrgName());
        }

        return map;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
