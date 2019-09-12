package cn.obcp.user.util;

public class CommandUtils {
	
	public static void exec(String path) throws Exception {
//		String os = System.getProperty("os.name");
		Process process = Runtime.getRuntime().exec(path); 
		int p = process.waitFor();
		
	}
	
	/*public static void main(String[] args) throws Exception {
		exec("F://kill-port-service.bat");
	}*/
	
}
