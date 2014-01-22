package test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


import com.taobao.api.*;
import com.taobao.api.domain.TaobaokeReport;
import com.taobao.api.domain.TaobaokeReportMember;
import com.taobao.api.request.TaobaokeReportGetRequest;
import com.taobao.api.response.TaobaokeReportGetResponse;

public class InitDataJob extends TimerTask {
	static String url = "http://gw.api.taobao.com/router/rest";
	static String appkey = "21416726";
	static String secret = "db2f0d165a42d0aa5934fd922187b6e9";

	@Override
	public void run() {
		Date curr = new Date();
		long mis = curr.getTime() - 24 * 60 * 60 * 1000;
		Date yesterday = new Date(mis);
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String temp = sd.format(yesterday);
		try {
			// 同步昨天的数据至DB(包括明细和汇总)，最好每天凌晨2：00左右做此定时任务
			initSumToDb(getDaySum(temp));
			initDetailToDb(temp);
			System.out.println("----timer-----" + new Date().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getRecsNum(String date) throws ApiException {
		DefaultTaobaoClient client = new DefaultTaobaoClient(url, appkey,
				secret);
		TaobaokeReportGetRequest req = new TaobaokeReportGetRequest();
		req.setFields("trade_parent_id,trade_id");
		req.setDate(date);
		TaobaokeReportGetResponse rs = client.execute(req);
		TaobaokeReport report = rs.getTaobaokeReport();
		System.out.println("dfdf----" + report.getTotalResults());
		return report.getTotalResults();
	}

	/**
	 * 淘宝客明细list
	 * 
	 * @param date
	 * @return
	 * @throws ApiException
	 */
	public List getDetailData(String date,long page) throws ApiException {
		DefaultTaobaoClient client = new DefaultTaobaoClient(url, appkey,
				secret);
		TaobaokeReportGetRequest req = new TaobaokeReportGetRequest();
		req.setFields("trade_parent_id,trade_id,real_pay_fee,commission_rate,commission,app_key,outer_code,create_time,pay_time,pay_price,num_iid,item_title,item_num,category_id,category_name,shop_title,seller_nick");
		req.setDate(date);
		req.setPageNo(page);
		req.setPageSize(100l);
		TaobaokeReportGetResponse rs = client.execute(req);
		TaobaokeReport report = rs.getTaobaokeReport();
		List<TaobaokeReportMember> list = report.getTaobaokeReportMembers();
		System.out.println("list-----" + list.size());
		return list;
	}

	/**
	 * 淘宝客汇总
	 * 
	 * @param date
	 * @return
	 * @throws ApiException
	 */
	public String[] getDaySum(String date) throws ApiException {
		long ALL_RECS_COUNT = getRecsNum(date);
		long temp1 = ALL_RECS_COUNT % 100;
		long temp2 = 0;
		if (temp1 == 0) {
			temp2 = ALL_RECS_COUNT / 100;
		} else {
			temp2 = ALL_RECS_COUNT / 100 + 1;
		}
		double d1 = 0, d2 = 0, d3 = 0;
		String[] arr = new String[7];
		for(int j = 1;j <= temp2;j++){
			List<TaobaokeReportMember> r = getDetailData(date,j);
			for (int i = 0; i < r.size(); i++) {
				TaobaokeReportMember trm = r.get(i);
				d1 = d1 + Double.parseDouble(trm.getRealPayFee());
				d2 = d2 + Double.parseDouble(trm.getCommission());
			}
		}
		double avgFee = d2 / getRecsNum(date);
		double feeRate = (d2 / d1) * 100;
		double customTrade = avgFee / (feeRate / 100);

		System.out.println(d1 + "---" + d2 + "---" + temp2 + "---" + ALL_RECS_COUNT);
		
		arr[0] = date;
		arr[1] = ALL_RECS_COUNT + "";
		arr[2] = formatDouble(d1) + "";
		arr[3] = formatDouble(d2) + "";

		arr[4] = formatDouble(customTrade) + "";
		arr[5] = formatDouble(avgFee) + "";
		arr[6] = formatDouble(feeRate) + "";
		return arr;
	}

	/**
	 * 保存汇总数据至db
	 * 
	 * @param arr
	 */
	public void initSumToDb(String[] arr) {
		/*TaobaokeSumBean tsb = new TaobaokeSumBean();
		tsb.setTradeDate(arr[0]);
		tsb.setTradeNum(arr[1]);
		tsb.setTradeAmount(arr[2]);
		tsb.setTradeFee(arr[3]);
		tsb.setTradePrice(arr[4]);
		tsb.setTradeAvgFee(arr[5]);
		tsb.setTradeFeeRate(arr[6]);
		tsb.setNote("");
		Ebean.save(tsb);*/
	}

	/**
	 * 保存明细数据至db
	 * 
	 * @param arr
	 * @throws ApiException
	 */
	public void initDetailToDb(String date) throws ApiException {
		/*TaobaokeDetailBean tsb = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm:ss");

		long ALL_RECS_COUNT = getRecsNum(date);
		long temp1 = ALL_RECS_COUNT % 100;
		long temp2 = 0;
		if (temp1 == 0) {
			temp2 = ALL_RECS_COUNT / 100;
		} else {
			temp2 = ALL_RECS_COUNT / 100 + 1;
		}
		for(int j = 1;j <= temp2;j++){
			List list = getDetailData(date,temp2);
			for (int i = 0; i < list.size(); i++) {
				tsb = new TaobaokeDetailBean();
				TaobaokeReportMember obj = (TaobaokeReportMember) list.get(i);
				tsb.setTradeId(obj.getTradeId().toString());
				tsb.setTradeDate(date);
				tsb.setTradeFatherId(obj.getTradeParentId().toString());
				tsb.setRealPay(obj.getRealPayFee());
				tsb.setFeeRate(obj.getCommissionRate());
				tsb.setFee(obj.getCommission().toString());
				tsb.setAppAuthKey(obj.getAppKey());
				tsb.setPromotionChannels(obj.getOuterCode());
				tsb.setOrderCrtTime(sdf.format(obj.getCreateTime()));
				tsb.setOrderConfirmTime(sdf.format(obj.getPayTime()));
				tsb.setOrderPrice(obj.getPayPrice());
				tsb.setProductId(obj.getNumIid().toString());
				tsb.setProductName(obj.getItemTitle());
				tsb.setProductNum(obj.getItemNum().toString());
				tsb.setProductCatid(obj.getCategoryId().toString());
				tsb.setProductCatname(obj.getCategoryName());
				tsb.setMchtName(obj.getShopTitle());
				tsb.setMchtNickName(obj.getSellerNick());
				tsb.setRecordCrtTime(sdf.format(new Date()));
				Ebean.save(tsb);
			}
		}*/
	}

	// 格式化double数据，四舍五入，并格式化为12，345，345.00
	public String formatDouble(double num) {
		// BigDecimal的构造函数参数类型是double
		BigDecimal deSource = new BigDecimal(num);
		// deSource.setScale(0,BigDecimal.ROUND_HALF_UP) 返回值类型 BigDecimal
		// intValue() 方法将BigDecimal转化为int
		return deSource.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	public static void main(String[] args){
		try {
			String[] arr = new InitDataJob().getDaySum("20130402");
			for(int i = 0;i < arr.length;i++){
				System.out.println(arr[i]);
			}
			
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
