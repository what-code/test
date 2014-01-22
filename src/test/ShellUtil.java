package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class ShellUtil {
	// 基本路径
	private static final String basePath = "/tmp/";

	// 记录Shell执行状况的日志文件的位置(绝对路径)
	private static final String executeShellLogFile = basePath
			+ "executeShell.log";

	// 发送文件到Kondor系统的Shell的文件名(绝对路径)
	private static final String sendKondorShellName = basePath
			+ "sendKondorFile.sh";
	//Shell
	private String cmd = "curl  -H 'HOST: www.google.com' -s -H ': version:HTTP/1.1' -H 'ACCEPT: */*' -H 'ACCEPT-CHARSET: ISO-8859-1,utf-8;q=0.7,*;q=0.3' -H 'ACCEPT-ENCODING: gzip,deflate,sdch' -H 'ACCEPT-LANGUAGE: en-US,en;q=0.8' -H 'COOKIE: __utma=173272373.1445186433.1361185060.1364175333.1364189620.134;__utmb=173272373.7.9.1364189636729;__utmc=173272373;__utmz=173272373.1361185060.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none);_ga=1.2-2.1445186433.1361185060;PREF=ID=571ed4c7fd4d8354:U=5c98677f080c6ea2:FF=0:LD=en:TM=1355708988:LM=1361184896:GM=1:S=52mT7idIPaheVzVS;NID=67=sH7hLwzI6XPWTtoKRF9kg7pGmeQDGpsqzKsvk-En72hhcMjtATAlyrOQ7byP5x262tfnbBndo-WeAYfnEqs_bH2vcaX8TeaK_Qt2Yb5fUC0tOKEGXsMdWuaUlufY77Hxb7wTEJc_BMRnyP8IYgfI5sxrvP91C10itiSDqS1Fmd6r9L6f;HSID=ArVZjCXeIxR_nxt0E;SSID=AFNtG3HnvCCMWnEKH;APISID=K7mBsjmMFLJVmT8U/AxfGqhVCL97TSgv6G;SAPISID=3ABsYmKIp_tc7_nP/ApZPaQv6fKKkJ6DXI;SID=DQAAAP0AAAA96zhmjHdYqUCWWXxGqWLSEb2jZUM37YeWXYp6OMRe5fl-cSi6PllzPYD7T9LpRNQ5t0e86ibS-reQ-UlHbP1YuxtYZ5BqHM2GFsGw8eJNwQ7YTIu3IC7JZLe0gFN1eyuirW0F2gmgGA1FxQgXB_lZl0dTs8-8uUddpwUXjbBLIrIlUxFqxkob56fF9f2u10LyxFbDwFo6utunaEbGNQUfWVNDCQp3cmtYIYcxCyvrs7fszGefw6SUeCGjkadOD-wkjxN0XAQ3A6vF4LYtwiVUzzDFbnLD0WdGwTYUxpHsElzI6qLFEvqjK8LoYTjCQJMRPEr-gTNs0rVgP1cWoNwO;S=analytics-realtime-frontend=ciCwUJ8m1RhHYK2eStzoxg' -H 'REFERER: https://www.google.com/analytics/web/?authuser=0' -H 'USER-AGENT: Mozilla/5.0(Macintosh;IntelMacOSX10_7_5)AppleWebKit/537.22(KHTML,likeGecko)Chrome/25.0.1364.172Safari/537.22' -H 'X-CHROME-VARIATIONS: CMe1yQEIiLbJAQihtskBCKK2yQEIqLbJAQiptskBCK22yQEI/YPKAQ==' 'https://www.google.com/analytics/realtime/realtime/getData?key=a35991905w63934671p67445028&ds=a35991905w63934671p67445028&pageId=RealtimeReport/rt-overview&q=t:0%7C:1%7C:0:,t:11%7C:1%7C:5:,ot:0:0:4:,ot:0:0:3:,t:7%7C:1%7C:5:6%3D%3DREFERRAL;,t:7%7C:1%7C:5:6%3D%3DSOCIAL;,t:10%7C:1%7C:10:,t:18%7C:1%7C:10:,t:4%7C5%7C2%7C:1%7C:10:2!%3Dzz;,&f&hl=zh_CN' --compressed";
	
	public static String[] executeShell(String shellCommand) throws IOException {
		String[] result = new String[3];
		int success = 1;
		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer stringBuffer1 = new StringBuffer();
		BufferedReader bufferedReader = null;
		// 格式化日期时间，记录日志时使用
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS ");

		try {
			stringBuffer.append(dateFormat.format(new Date()))
					.append("准备执行Shell命令 ").append(shellCommand)
					.append(" \r\n");

			Process pid = null;
			String[] cmd = { "/bin/sh", "-c", shellCommand };
			// 执行Shell命令
			pid = Runtime.getRuntime().exec(cmd);
			if (pid != null) {
				stringBuffer.append("进程号：").append(pid.toString())
						.append("\r\n");
				 //bufferedReader用于读取Shell的输出内容 
				bufferedReader = new BufferedReader(new InputStreamReader(pid.getInputStream()),
				 1024);
				pid.waitFor();
			} else {
				stringBuffer.append("没有pid\r\n");
			}
			stringBuffer.append(dateFormat.format(new Date())).append(
					"Shell命令执行完毕\r\n执行结果为：\r\n");
			String line = null;
			// 读取Shell的输出内容，并添加到stringBuffer中
			while (bufferedReader != null
					&& (line = bufferedReader.readLine()) != null) {
				stringBuffer1.append(line).append("\r\n");
			}
		} catch (Exception ioe) {
			stringBuffer.append("执行Shell命令时发生异常：\r\n").append(ioe.getMessage())
					.append("\r\n");
			success = 0;
		} finally {
			if (bufferedReader != null) {
				OutputStreamWriter outputStreamWriter = null;
				try {
					bufferedReader.close();
					// 将Shell的执行情况输出到日志文件中
					/*OutputStream outputStream = new FileOutputStream(
							executeShellLogFile);
					outputStreamWriter = new OutputStreamWriter(outputStream,
							"UTF-8");*/
					//outputStreamWriter.write(stringBuffer.toString());
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					//outputStreamWriter.close();
				}
			}
			success = 1;
		}
		result[0] = success + "";
		result[1] = stringBuffer1.toString();
		result[2] = stringBuffer.toString();
		
		return result;
	}
	
	public static String getShellResultInJSON(String scmd){
		String[] arr = new String[3];
		try {
			arr = ShellUtil.executeShell(scmd);
			if("1".equals(arr[0])){
				JSONObject obj = new JSONObject(arr[1]);
				String str = new JSONObject(obj.get("t:10|:1|:10:").toString()).get("metricTotals").toString();
				return str.replace("[", "").replace("]", "");
			}else{
				return arr[2];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr[2];
	}
	
}