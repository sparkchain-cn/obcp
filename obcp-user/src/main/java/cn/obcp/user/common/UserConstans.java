package cn.obcp.user.common;

public class UserConstans {

	public  static  final  Long SYSTEM_USER_ACCOUNT = 9L;//默认系统创建资源，角色使用appid
	
	public static final String FREE_END_TIME = "FREE_END_TIME"; //用户默认免费截止时间配置KEY
	
	//邀请码redis KEY 
	public static final String INVITE_CODE_KEY = "INVITE_CODE_KEY";
	
	public static final int USER_TYPE_PERSONAL = 0;
	
	
	public static final int USER_TYPE_COMPANY = 1;
	
	
	public static final int USER_TYPE_VIP = 1;
	
	public static final int USER_TYPE_NORMAL = 0;
	
	//res公钥
	public static final String RES_PUBLIC_KEY = "RSA_PUBLIC_KEY";
	//res私钥
	public static final String RES_PRIVATE_KEY = "RSA_PRIVATE_KEY";

	//资源分组（控制台）
	public static final int RESOURCE_CONSOULE_GROUP = 1;
	//资源分组（应用端）
	public static final int RESOURCE_APP_GROUP = 2;

	//树形拖拽类型
	public static final  String TREE_DROP_TYPE_INNER = "inner";//成为子节点
	public static final  String TREE_DROP_TYPE_PREV = "prev";//前一节点
	public static final  String TREE_DROP_TYPE_NEXT = "next";//后一节点

	public static  final  String USER_ACESSTOKEN_KEY ="SPARKCHAIN::ACCESSTOKEN";

	public static final String DEFAULT_SESSION_KEY_PREFIX = "shiro:session:";

	public static final String USER_TOKEN = "USER_TOKEN";
	
	/** shiro 默认拦截地址前缀  */
	public static String SHIRO_DEF_FILTER_URL_PREFIX = "/v1/**";
}
