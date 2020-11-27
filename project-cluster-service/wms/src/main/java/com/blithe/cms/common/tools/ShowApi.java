package com.blithe.cms.common.tools;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class ShowApi {

	static final String sa_url = "http://route.showapi.com/";
	static final String sa_appid = "70706";
	static final String sa_sign = "99a1fdab0d204eaf888ec2ad634de95e";
	static final String f_131_46 = "131-46?";//股票，实时行情批量
	static final String f_131_49 = "131-49?";//股票，实时分时线
	static final String f_131_50 = "131-50?";//股票，实时K线图
	static final String f_131_45 = "131-45?";//大盘股指，实时行情批量
	static final String f_131_51 = "131-51?";//大盘股指，实时分时线
	static final String f_131_52 = "131-52?";//大盘股指，实时K线图

	public static void main(String[] args) throws Exception {
		List<String> stocks = new ArrayList<>();
		stocks.add("sh600000");
		stocks.add("sh600001");
		stocks.add("sz000001");
		stocks.add("sz000002");
		System.out.println(_131_46(stocks));
		String stock = "300377";
//		System.out.println(_131_49(stock));
//		System.out.println(_131_50(stock));
//		List<String> indexs = new ArrayList<>();
//		indexs.add("sh000001");
//		indexs.add("sz399001");
//		System.out.println(_131_45(indexs));
//		String index = "000001";
//		System.out.println(_131_51(index));
//		System.out.println(_131_52(index));
	}

	/**
	 * 131-46?股票，实时行情批量
	 * @param stocks （sz300377,sh600000）股票编码 
	 * @return
	 */
	public static String _131_46(List<String> stocks){
		//参数（sz300377,sh600000）
		StringBuffer buf = new StringBuffer();
		for(String s : stocks){
			buf.append(s).append(",");
		}
		buf.setLength(buf.length()-1);
		String stocksStr = buf.toString();
		
		//请求URL
		buf.setLength(0);
		buf.append(sa_url).append(f_131_46);
		buf.append("showapi_appid=").append(sa_appid).append("&showapi_sign=").append(sa_sign);
		buf.append("&needIndex=1&stocks=").append(stocksStr);
		
		//加该参数可替代131-45接口的数据。
		//buf.append("&needIndex=1");
		return getData(buf.toString());
	}
	
	/**
	 * 131-49?股票，实时分时线
	 * @param stock 300377股票编码，不需要写市场名称
	 * @return
	 */
	public static String _131_49(String stock){
		//请求URL
		StringBuffer buf = new StringBuffer();
		buf.append(sa_url).append(f_131_49);
		buf.append("showapi_appid=").append(sa_appid).append("&showapi_sign=").append(sa_sign);
		buf.append("&code=").append(stock);
		return getData(buf.toString());
	}
	
	/**
	 * 131-50?股票，实时K线图
	 * @param stock 300377股票编码，不需要写市场名称
	 * @return
	 */
	public static String _131_50(String stock){
		//请求URL
		StringBuffer buf = new StringBuffer();
		buf.append(sa_url).append(f_131_50);
		buf.append("showapi_appid=").append(sa_appid).append("&showapi_sign=").append(sa_sign);
		buf.append("&beginDay=").append("20000101");
		buf.append("&time=day").append("&type=bfq");
		buf.append("&code=").append(stock);
		return getData(buf.toString());
	}
	
	/**
	 * 131-45?大盘股指，实时行情批量
	 * @param indexs （sh000001,sz399001,sz399005,sz399006）股指编码
	 * @return
	 */
	public static String _131_45(List<String> indexs){
		//参数（sz300377,sh600000）
		StringBuffer buf = new StringBuffer();
		for(String s : indexs){
			buf.append(s).append(",");
		}
		buf.setLength(buf.length()-1);
		String stocksStr = buf.toString();
		
		//请求URL
		buf.setLength(0);
		buf.append(sa_url).append(f_131_45);
		buf.append("showapi_appid=").append(sa_appid).append("&showapi_sign=").append(sa_sign);
		buf.append("&stocks=").append(stocksStr);
		return getData(buf.toString());
	}
	
	/**
	 * 131-51?大盘股指，实时分时线
	 * @param index 000001股指编码
	 * @return
	 */
	public static String _131_51(String index){
		//请求URL
		StringBuffer buf = new StringBuffer();
		buf.append(sa_url).append(f_131_51);
		buf.append("showapi_appid=").append(sa_appid).append("&showapi_sign=").append(sa_sign);
		buf.append("&code=").append(index);
		return getData(buf.toString());
	}
	
	/**
	 * 131-52?大盘股指，实时K线图
	 * @param index 000001股指编码
	 * @return
	 */
	public static String _131_52(String index){
		//请求URL
		StringBuffer buf = new StringBuffer();
		buf.append(sa_url).append(f_131_52);
		buf.append("showapi_appid=").append(sa_appid).append("&showapi_sign=").append(sa_sign);
		buf.append("&beginDay=20000101&time=day");
		buf.append("&code=").append(index);
		return getData(buf.toString());
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	private static String getData(String url){
		String res = "-1";
		try {
			URL u = new URL(url);
			InputStream in = u.openStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				byte bt[] = new byte[1024];
				int read = 0;
				while ((read = in.read(bt)) > 0) {
					out.write(bt, 0, read);
				}
			} finally {
				if (in != null) {
					in.close();
				}
			}
			byte b[] = out.toByteArray();
			res = new String(b, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
}
