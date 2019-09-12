package cn.obcp.user.dto;


import java.util.Map;

import cn.obcp.base.utils.StringUtils;

public class OrgDto extends  BaseDto {
    private String name;
    private String code;



    public OrgDto( String name,String code){
        this.code = code;
        this.name = name;
    }

    public static Map<String, Object> getParamBuDto(OrgDto orgDto) {
        if (orgDto == null){
            orgDto = new OrgDto(null,null);
        }
        Map map = getBasePramMap(orgDto);
        if (StringUtils.isNotNullOrEmpty(orgDto.getCode())){
            map.put("code",orgDto.getCode());
        }
        if (StringUtils.isNotNullOrEmpty(orgDto.getName())){
            map.put("name",orgDto.getName());
        }
        return map;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
