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
public enum ResourceAddrType {

	// 1：页面端 2： 服务端 
	PAGES("pages"),SERVICE("service");

    private String type;

    ResourceAddrType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
