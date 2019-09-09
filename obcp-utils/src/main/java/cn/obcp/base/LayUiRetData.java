package cn.obcp.base;

public class LayUiRetData {

    private String msg;
    private Long count;
    private String code;
    private Object data;

    public LayUiRetData() {

    }

    public LayUiRetData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static LayUiRetData error(String code, String msg) {
        return new LayUiRetData(code, msg);
    }


    public static LayUiRetData error(String msg) {
        LayUiRetData data = new LayUiRetData();
        data.setCode("505");
        data.setMsg(msg);
        return data;
    }

    public static LayUiRetData success(Object data){
        LayUiRetData retData = new LayUiRetData();
        retData.setData(data);
        retData.setCode("0");
        return retData;
    }

    public static LayUiRetData success(Object data,Long count){
        LayUiRetData retData = new LayUiRetData();
        retData.setCode("0");
        retData.setData(data);
        retData.setCount(count);
        return retData;
    }
}
