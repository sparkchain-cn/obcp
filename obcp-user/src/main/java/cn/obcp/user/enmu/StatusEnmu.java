package cn.obcp.user.enmu;

public enum StatusEnmu {

    NORMAL(0),DELETED(-1);

    int status;

    StatusEnmu(int status) {
        this.status = status;
    }

    public int getType() {
        return status;
    }
}
