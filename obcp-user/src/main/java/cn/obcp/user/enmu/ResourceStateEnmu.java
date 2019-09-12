/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月13日
 */
package cn.obcp.user.enmu;

/**
 * @author lmf
 *
 */
public enum ResourceStateEnmu {
	// 0:待审核 1:正常  2:禁用   -1：已删除
	VERIFY(0), NORMAL(1),DISABLED(2),DELETED(-1);

    private int status;

    ResourceStateEnmu(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
