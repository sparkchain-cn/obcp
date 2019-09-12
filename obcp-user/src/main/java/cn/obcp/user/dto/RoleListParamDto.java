package cn.obcp.user.dto;

import cn.obcp.base.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RoleListParamDto  extends  BaseDto{
    String code;
    String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map<String,Object> getParamBuDto(RoleListParamDto roleListParamDto){
        Map<String,Object> map = new HashMap<String, Object>();
        if (roleListParamDto != null){
            map.put("pagesize",roleListParamDto.getPagesize());
            map.put("pagenum",roleListParamDto.getPagenum());
        }else {
            roleListParamDto = new RoleListParamDto();
        }

        if (StringUtils.isNotNullOrEmpty(roleListParamDto.getCode())){
            map.put("code",roleListParamDto.getCode());
        }
        if (StringUtils.isNotNullOrEmpty(roleListParamDto.getName())){
            map.put("name",roleListParamDto.getName());
        }
        return map;
    }

}
