package cn.obcp.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class BaseDto {
    @JsonProperty(value = "token")
    String token;// 参数加密token
    @JsonProperty(value = "d")
    String d;//时间戳
    @JsonProperty(value = "pagenum")
    String pagenum = "1";
    @JsonProperty(value = "pagesize")
    String pagesize = "10";
    @JsonProperty(value = "signature")
    String signature = "";


    public static Map getBasePramMap(BaseDto dto) {
        int number = Integer.valueOf(dto.getPagenum()) > 0 ? Integer.valueOf(dto.getPagenum()) : 0;
        int size = Integer.valueOf(dto.getPagesize()) > 0 ? Integer.valueOf(dto.getPagesize()) : 10;
        return new HashMap(){{put("pagesize",dto.getPagesize());put("pagenum",dto.getPagenum());put("skip",(number-1)*size);}};
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getPagenum() {
        return pagenum;
    }

    public void setPagenum(String pagenum) {
        this.pagenum = pagenum;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
