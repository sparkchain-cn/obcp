/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月14日
 */
package cn.obcp.user.enmu;

/**
 * @author lmf
 *
 */
public enum ResourceType {

	// 页面文件夹：1  页面：2  数据接口：3 
	PAGEFOLDER("1"),PAGE("2"),DATAPI("2");
	private String type;

	ResourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
