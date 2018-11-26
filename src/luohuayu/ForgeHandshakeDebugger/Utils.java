package luohuayu.ForgeHandshakeDebugger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static void log(String from,Object msg) {
		System.out.println("["+from+"] "+msg);
	}
	
	public static void log(Object msg) {
		System.out.println(msg);
	}

	public static void log(Object... msg) {
		for (Object o : msg) {
			System.out.println(o);
		}
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Matcher matches(String str,String regex) {
		Pattern mPattern=Pattern.compile(regex);
		Matcher mMatcher=mPattern.matcher(str);
		return mMatcher;
	}
}