package test;

import java.io.*;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.json.JSONObject;

public class TestRuby {
	private static String cmd = "curl  -H 'HOST: www.google.com' -s -H ': version:HTTP/1.1' -H 'ACCEPT: */*' -H 'ACCEPT-CHARSET: ISO-8859-1,utf-8;q=0.7,*;q=0.3' -H 'ACCEPT-ENCODING: gzip,deflate,sdch' -H 'ACCEPT-LANGUAGE: en-US,en;q=0.8' -H 'COOKIE: __utma=173272373.1445186433.1361185060.1364175333.1364189620.134;__utmb=173272373.7.9.1364189636729;__utmc=173272373;__utmz=173272373.1361185060.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none);_ga=1.2-2.1445186433.1361185060;PREF=ID=571ed4c7fd4d8354:U=5c98677f080c6ea2:FF=0:LD=en:TM=1355708988:LM=1361184896:GM=1:S=52mT7idIPaheVzVS;NID=67=sH7hLwzI6XPWTtoKRF9kg7pGmeQDGpsqzKsvk-En72hhcMjtATAlyrOQ7byP5x262tfnbBndo-WeAYfnEqs_bH2vcaX8TeaK_Qt2Yb5fUC0tOKEGXsMdWuaUlufY77Hxb7wTEJc_BMRnyP8IYgfI5sxrvP91C10itiSDqS1Fmd6r9L6f;HSID=ArVZjCXeIxR_nxt0E;SSID=AFNtG3HnvCCMWnEKH;APISID=K7mBsjmMFLJVmT8U/AxfGqhVCL97TSgv6G;SAPISID=3ABsYmKIp_tc7_nP/ApZPaQv6fKKkJ6DXI;SID=DQAAAP0AAAA96zhmjHdYqUCWWXxGqWLSEb2jZUM37YeWXYp6OMRe5fl-cSi6PllzPYD7T9LpRNQ5t0e86ibS-reQ-UlHbP1YuxtYZ5BqHM2GFsGw8eJNwQ7YTIu3IC7JZLe0gFN1eyuirW0F2gmgGA1FxQgXB_lZl0dTs8-8uUddpwUXjbBLIrIlUxFqxkob56fF9f2u10LyxFbDwFo6utunaEbGNQUfWVNDCQp3cmtYIYcxCyvrs7fszGefw6SUeCGjkadOD-wkjxN0XAQ3A6vF4LYtwiVUzzDFbnLD0WdGwTYUxpHsElzI6qLFEvqjK8LoYTjCQJMRPEr-gTNs0rVgP1cWoNwO;S=analytics-realtime-frontend=ciCwUJ8m1RhHYK2eStzoxg' -H 'REFERER: https://www.google.com/analytics/web/?authuser=0' -H 'USER-AGENT: Mozilla/5.0(Macintosh;IntelMacOSX10_7_5)AppleWebKit/537.22(KHTML,likeGecko)Chrome/25.0.1364.172Safari/537.22' -H 'X-CHROME-VARIATIONS: CMe1yQEIiLbJAQihtskBCKK2yQEIqLbJAQiptskBCK22yQEI/YPKAQ==' 'https://www.google.com/analytics/realtime/realtime/getData?key=a35991905w63934671p67445028&ds=a35991905w63934671p67445028&pageId=RealtimeReport/rt-overview&q=t:0%7C:1%7C:0:,t:11%7C:1%7C:5:,ot:0:0:4:,ot:0:0:3:,t:7%7C:1%7C:5:6%3D%3DREFERRAL;,t:7%7C:1%7C:5:6%3D%3DSOCIAL;,t:10%7C:1%7C:10:,t:18%7C:1%7C:10:,t:4%7C5%7C2%7C:1%7C:10:2!%3Dzz;,&f&hl=zh_CN' --compressed";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String test = ShellUtil.getShellResultInJSON(cmd);
		System.out.println("test---" + test);
	}

}
