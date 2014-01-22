package test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Test1 {
	private static ThreadPoolExecutor executor;
	final static AtomicInteger counter = new AtomicInteger(0);

	private static void init() {
		ThreadPoolExecutor executor;
		int N_CPUS = Runtime.getRuntime().availableProcessors();
		executor = new ThreadPoolExecutor(N_CPUS, N_CPUS + 30, 1L,
				TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(100000),
				new ThreadPoolExecutor.CallerRunsPolicy());
		System.out.println("----begin version count ");
		executor.execute(new Runnable() {
			public void run() {
				while (counter.get() < 50) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					counter.incrementAndGet();
					System.out.println("----version count " + counter.get());
				}
				// 所有
				System.out.println("----end version count ");
			}
		});
	}

	private static double EARTH_RADIUS = 6371004;// 地球半径:m
	
	public static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	//计算两个点之间的距离：纬度、经度
	public static double getDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	//获取以某点为中心(纬度,经度)，半径为radius的酒店
	public static String getDistanceRange(double lat1, double lng1,int radius){
		//纬度每秒的距离,单位:m
		double distancePerLatitudeSecs = 30.9;
		//经度每秒的距离,单位:m
		double distancePerLongitudeSecs = distancePerLatitudeSecs * Math.cos(Math.toRadians(lat1));
		
		//纬度的范围
		double latitudeRange = radius/distancePerLatitudeSecs;
		//经度的范围
		double longitudeRange = radius/distancePerLongitudeSecs;
		
		System.out.println(distancePerLatitudeSecs + "---" + distancePerLongitudeSecs + "----" 
		+ latitudeRange/3600 + "----" + longitudeRange/3600);
		
		System.out.println("latitudeRange:" + (lat1 - latitudeRange/3600) + "~" + (lat1 + latitudeRange/3600) +
				"------->longitudeRange:" + (lng1 - longitudeRange/3600) + "~" + (lng1 + longitudeRange/3600));
		return null;
	}
	
	//public static void main(String[] args) {
		//System.out.println("distance--->" + getDistance(31.0,122.0,32.0,122.0) + " m");
		//getDistanceRange(31.230393000000,121.473704000000,1000);
		//System.out.println("distance01--->" + Math.cos(Math.toRadians(31)) + "----" + Math.cos(Math.toRadians(53)));
	//}
	
	public static void main(String[] args) {
		int i = 1;
		while (i <= 200000) {
			try {
				Long l = null;
				l.toString();
			} catch (Exception e) {
				//System.out.println("length-->" + e.getStackTrace().length);
				e.printStackTrace();
				if (e.getStackTrace().length == 0) {
					System.out.println("count is-->" + i);
					break;
				}
			}
			i++;
		}
	}

}
