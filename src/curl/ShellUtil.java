package curl;

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
	//默认Shell
	private static String cmd = "";
	
	public static String[] executeShell(String cookie) throws IOException {
		String newCmd = "curl -s -w %{http_code}:%{time_namelookup}:%{time_connect}:%{time_starttransfer}:%{time_total} \"http://taoy.stage.bang5mai.com\"";
		System.out.println("new_cmd---->" + newCmd);
		String[] result = new String[3];
		int success = 1;
		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer stringBuffer1 = new StringBuffer();
		BufferedReader bufferedReader = null;
		// 格式化日期时间，记录日志时使用
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS ");

		try {
			stringBuffer.append(dateFormat.format(new Date()))
					.append("准备执行Shell命令 ").append(newCmd)
					.append(" \r\n");

			Process pid = null;
			String[] cmd = { "/bin/sh", "-c", newCmd };
			// 执行Shell命令
			pid = Runtime.getRuntime().exec(cmd);
			if (pid != null) {
				stringBuffer.append("进程号：").append(pid.toString())
						.append("\r\n");
				 //bufferedReader用于读取Shell的输出内容 
				bufferedReader = new BufferedReader(new InputStreamReader(pid.getInputStream()),
				 1024*1024);
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
				stringBuffer1.append(line);
				System.out.println("line---" + line);
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
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					//outputStreamWriter.close();
				}
			}
		}
		result[0] = success + "";
		result[1] = stringBuffer1.toString();
		result[2] = stringBuffer.toString();
		
		return result;
	}
	
	public static String getShellResultInJSON(String cookie){
		String[] arr = new String[3];
		try {
			arr = ShellUtil.executeShell(cookie);
			System.out.println("------>"+arr[2]);
			return "";
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("google_rs_failed-->" + arr[1] + "--why?--");
			return "-1";
		}
	}
	
	public static void main(String[] args){
		getShellResultInJSON("");
	}
}
