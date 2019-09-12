/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月30日
 */
package cn.obcp.user.util;

import com.google.common.base.Strings;

/**
 * @author lmf
 *
 */
public class CheckStrength {

	/**
	 * 定义数据类型以及相应分数
	 */
	private static final int NUM = 1;
    private static final int SMALL_LETTER = 2;
    private static final int CAPITAL_LETTER = 3;
    private static final int OTHER_CHAR = 4;
	/**
	 *  验证字符类型，判断属于：数字，大写字母，小写字母，其他字符
	 * TODO
	 * @param c
	 * @return
	 * int
	 * lmf 创建于2018年11月30日
	 */
    private static int checkCharacterType(char c) {
    	System.out.println(c);
    	
        if (c >= 48 && c <= 57) {
            return NUM;
        }
        if (c >= 65 && c <= 90) {
            return CAPITAL_LETTER;
        }
        if (c >= 97 && c <= 122) {
            return SMALL_LETTER;
        }
        return OTHER_CHAR;
    }
    
    /**
     * 返回字符串中，参数字符类型的字符数量
     * TODO
     * @param passwd
     * @param type
     * @return
     * int
     * lmf 创建于2018年11月30日
     */
    private static int countLetter(String passwd, int type) {
        int count = 0;
        if (null != passwd && passwd.length() > 0) {
            for (char c : passwd.toCharArray()) {
                if (checkCharacterType(c) == type) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     *  验证密码强度
     * TODO
     * @param password
     * @return
     * int
     * lmf 创建于2018年11月30日
     */
    public static int score(String password) {
    	int total = 0;
    	if(Strings.isNullOrEmpty(password)) {
    		return total;
    	}else {
    		int length = password.length();//长度
    		//int numCount = countLetter(password, 1);
    		int smallCount = countLetter(password, 2);
    		//int capitalCount = countLetter(password, 3);
    	//	int otherCount = countLetter(password, 4);
    		
    		if(length < 6 || length > 18 || smallCount == 0) { //
    			total = -1;
    		}
    		
    		/*if(length >= 8) {total += 20;} //  位数大于等于8,加20分
    		
    		if(numCount > 1) {total += total += 20;}  //存在两个或以上数字，加20分
    		else if(numCount > 0) {total += 10;} //存在一个数字，加10分
    		
    		if(smallCount > 1) {total += total += 20;} //存在两个或以上小写字母，加20分
    		else if(smallCount > 0) {total += 10;}//存在一个小写字母，加10分
    		
      		if(capitalCount > 1) {total += total += 20;} //存在两个或以上大写字母	，加20分
    		else if(capitalCount > 0) {total += 10;} //存在一个大写字母，加10分
      		
      		if(otherCount > 1) {total += total += 25;} //存在两个或以上字母数字外的字符，加25分
    		else if(otherCount > 0) {total += 10;} //存在一个字母数字外的字符，加25分
      		
      		if(otherCount > 0 && capitalCount > 0 && smallCount > 0 && numCount > 0) { //存在其他字符，大写，小写，数字：奖励5分
      			total += 5;
      		}else if(otherCount > 0 && numCount >0 && (capitalCount >0 || smallCount > 0)) { //存在其他字符，字母，数字，奖励三分
      			total += 3;
      		}else if(numCount > 0 && (capitalCount >0 || smallCount > 0)) { //存在 数字，字母 ，奖励2分
      			total += 2;
      		}*/
    		return total;
    	}
    }
    
    public static void main(String args[]) {
		System.out.println("51d9fb3dd118fb8fd7c3be1726a8497d".length());
	}
    
}
