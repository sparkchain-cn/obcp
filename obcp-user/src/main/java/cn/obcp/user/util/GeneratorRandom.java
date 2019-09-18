/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月15日
 */
package cn.obcp.user.util;

import java.util.Random;

/**
 * @author lmf
 *
 */
public class GeneratorRandom {

	    private static final String ALPHABET ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    private static final int    BASE     = ALPHABET.length();

	    private String sailt;
	   

		public  GeneratorRandom(String sailt) {
	    	this.sailt = sailt;
		}
		
		/**
		 *  随机盐
		 * TODO
		 * @return
		 * String
		 * lmf 创建于2018年11月30日
		 */
		public static String build() {
			Random r = new Random(); 
			StringBuilder sb = new StringBuilder(16); 
			sb.append(r.nextInt(99999999)).append(r.nextInt(99999999)); 
			int len = sb.length(); 
			if (len < 16) { 
				for (int i = 0; i < 16 - len; i++) { 
					sb.append("0"); 
				} 
			}
			return sb.toString();
		}
		
	    public String getSailt() {
				return sailt;
			}

			public void setSailt(String sailt) {
				this.sailt = sailt;
			}
}
