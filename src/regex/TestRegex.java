package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String reg = "^(([\\d])+\\.)+([\\d]+)$";
		//非数字
		String reg1 = "\\D^\\.";
		Pattern pattern = Pattern.compile(reg);
		Pattern pattern1 = Pattern.compile(reg);
		Matcher matcher = pattern.matcher("4.3.9.6");
		if(matcher.matches()){
			System.out.println(matcher.group());
		}else{
			System.out.println("----not match");
		}
		
		//重置
		matcher.reset();
		
		if(matcher.find()){
			System.out.println(matcher.group() + "----" + matcher.start() + "-----" + matcher.end());
		}else{
			System.out.println("----not find");
		}
	}

}
