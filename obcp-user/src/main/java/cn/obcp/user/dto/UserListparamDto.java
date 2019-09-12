package cn.obcp.user.dto;

import cn.obcp.base.utils.StringUtils;
import cn.obcp.user.common.UserConstans;

import java.util.HashMap;
import java.util.Map;

public class UserListparamDto  extends  BaseDto{

    public UserListparamDto(){

    }

    Integer organization = UserConstans.USER_TYPE_PERSONAL;

    Long id;
    String mobile ;
    String nickname;
    int state = 1;

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static Map<String,Object> getParamBuDto(UserListparamDto userListparamDto){
        if (userListparamDto == null){
            userListparamDto = new UserListparamDto();
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pagesize",userListparamDto.getPagesize());
        map.put("pagenum",userListparamDto.getPagenum());
        map.put("state",userListparamDto.getState());
        if (StringUtils.isNotNullOrEmpty(userListparamDto.getMobile())){
            map.put("mobile",userListparamDto.getMobile());
        }
        if (StringUtils.isNotNullOrEmpty(userListparamDto.getNickname())){
            map.put("name",userListparamDto.getNickname());
        }
        if (null != userListparamDto.getId()){
            map.put("id",userListparamDto.getId());
        }
        return map;
    }
}
