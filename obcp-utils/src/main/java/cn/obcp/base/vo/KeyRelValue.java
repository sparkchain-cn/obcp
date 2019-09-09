package cn.obcp.base.vo;

import java.io.Serializable;

public class KeyRelValue implements Serializable {

    private String key;
    private String val;
    private String relative="=";

    public KeyRelValue(String key, String val) {
        super();
        this.key = key;
        this.val = val;
    }

    public KeyRelValue(String key, String val, String relative) {
        this.key = key;
        this.val = val;
        this.relative = relative;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }


    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }
}

