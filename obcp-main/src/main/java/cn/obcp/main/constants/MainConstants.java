package cn.obcp.main.constants;

public class MainConstants {

	public static final String FILESERVER_UPLOAD_PATH_KEY = "FILESERVER_UPLOAD_PATH";
	
	public static final String FILESERVER_DOWN_PATH_KEY = "FILESERVER_DOWN_PATH";

	public static final String PARAM_TOKEN_KEY = "signature"; //校验token时参数token的参数名

	public static final String PARAM_PARAMS_KEY = "params"; //前端参数加密后传输的参数明

	public static  final  String PARAM_RSAENCODE_URL = "/v1/userextend/login";//需要进行参数解密接口地址

	public static  final  String PARAM_TOKEN_URL = "/proxy/*"; //需要 验证token的地址
	public static  final  String PARAM_V1_URL = "/v1/*";
	public static  final  String PARAM_V0_URL = "/v0/*";
	public static  final  String PARAM_V2_URL = "/v2/*";


	public static final String TOKEN_RSA_PUBLICKEY = "" +
			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCOEQi/c/wDlRRjdwLWbTmDqtPm\n" +
			"rCYyIT/KV5qng5D5G8f6MnKhiFgGjSU69U2lj3ifzUx9fEqSGJWGUIAIsoh6sILy\n" +
			"SdKvDq+9AWe10jTy9h3P8zm/ssDDntRZ1eGfeoSUJ1I3P6SHXAK9n5WxXnzYcRpA\n" +
			"7FT+xd0jWi/HvIpNnQIDAQAB\n" +
			"";

	public static final String TOKEN_RSA_PRIVATEKEY = "" +
			"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI4RCL9z/AOVFGN3\n" +
			"AtZtOYOq0+asJjIhP8pXmqeDkPkbx/oycqGIWAaNJTr1TaWPeJ/NTH18SpIYlYZQ\n" +
			"gAiyiHqwgvJJ0q8Or70BZ7XSNPL2Hc/zOb+ywMOe1FnV4Z96hJQnUjc/pIdcAr2f\n" +
			"lbFefNhxGkDsVP7F3SNaL8e8ik2dAgMBAAECgYAAzMXA1jVYC9HWkYf5jxngzOhu\n" +
			"hw+b/qXuTLn5MXtye56PoRMWJ79fIhJQl6r9QKaMN8qzdiyxT+QfonpmEiNEk2tX\n" +
			"6a931F3cQCeSNr5T6LyZebZ0h3o0Adb/hJbtdwGkconi73lIsdJzmd8lrLIOBsOf\n" +
			"R1Ve+CpJArlSJWqSYQJBANKykzrlpLQ9p8JbFCyGKfmvnDqEG0RMgQg7H+H4PpBe\n" +
			"31vDlNEo+SJYl5tycUWxQnxZk3mnG0sTFaDN9qNPh9kCQQCsnM1Y5Q0+N2DbRUPF\n" +
			"OGRYWT3pUkQLbl+yWHU1gqnOUnx5dgYVhzuX6mARfnLZ2adtBKMSfR1iVK9dkyoV\n" +
			"Qz1lAkB2MNwsLsPco7Vmbib77uq++IafwJl3D59WGayB2vpljAKpfCYPyncukBnD\n" +
			"hkgKOyw7ixgSJu5Fh9gbE05mP+fJAkBtvO5sIort0AU6welY/AYHiQzsRgnOfqhG\n" +
			"WSiDEYPlENnj2l3519TSOwZCePKJf2+KxpTqDzLcLdyjkAkEJZBlAkEAjE1w/ZsO\n" +
			"o3Vlj22lMtM+nfsDGFD0sj++PON5jaC65EPx7sKgujPIQc4WyEaInxT2Pccz6UJr\n" +
			"5lAb3wB2nuFQaQ==\n" +
			"";
}
