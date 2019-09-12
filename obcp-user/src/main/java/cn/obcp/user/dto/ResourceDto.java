package cn.obcp.user.dto;

import java.util.Map;

import cn.obcp.base.utils.StringUtils;

public class ResourceDto extends BaseDto {

    private String code;

    private String name;
    
    private String parentid;

    public static Map<String, Object> getParamBuDto(ResourceDto resourceDto) {
        if (resourceDto == null){
            resourceDto = new ResourceDto();
        }
        Map map = getBasePramMap(resourceDto);
        if (StringUtils.isNotNullOrEmpty(resourceDto.getCode())){
            map.put("code",resourceDto.getCode());
        }
        if (StringUtils.isNotNullOrEmpty(resourceDto.getName())){
            map.put("name",resourceDto.getName());
        }
        if (StringUtils.isNotNullOrEmpty(resourceDto.getParentid())){
            map.put("parentid",resourceDto.getParentid());
        }
        
        return map;
    }


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


	public String getParentid() {
		return parentid;
	}


	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
    
}
