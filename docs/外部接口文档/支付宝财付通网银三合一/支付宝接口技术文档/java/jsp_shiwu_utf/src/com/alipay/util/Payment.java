package com.alipay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
/**
 * 名称：支付主类
 * 功能：支付宝外部服务接口控制
 * 接口名称：标准实物双接口
 * 版本：2.0
 * 日期：2008-12-25
 * 作者：支付宝公司销售部技术支持团队
 * 联系：0571-26888888
 * 版权：支付宝公司
 * */
public class Payment {

    public static String CreateUrl(String paygateway,String service,String sign_type,String out_trade_no,
    		      String input_charset,String partner,String key,String seller_email,
                  String body,String subject,String price,String quantity,String show_url,String payment_type,
                  String discount,String logistics_type,String logistics_fee,String logistics_payment,
                  String return_url) {
                   //String notify_url,需要的请把参数加入以上的createurl
        Map params = new HashMap();
        params.put("service", service);
        params.put("out_trade_no", out_trade_no);
        params.put("show_url", show_url);
        params.put("quantity", quantity);
        params.put("partner", partner);
        params.put("payment_type", payment_type);
        params.put("discount", discount);
        params.put("body", body);
       // params.put("notify_url", notify_url);
        params.put("price", price);
        params.put("return_url", return_url);
        params.put("seller_email", seller_email);
        params.put("logistics_type", logistics_type);
        params.put("logistics_fee", logistics_fee);
        params.put("logistics_payment", logistics_payment);
        params.put("subject", subject);
        params.put("_input_charset", input_charset);
        String prestr = "";

        prestr = prestr + key;
        //System.out.println("prestr=" + prestr);

        String sign = com.alipay.util.Md5Encrypt.md5(getContent(params, key));

        String parameter = "";
        parameter = parameter + paygateway;
        //System.out.println("prestr="  + parameter);
        List keys = new ArrayList(params.keySet());
        for (int i = 0; i < keys.size(); i++) {
          	String value =(String) params.get(keys.get(i));
            if(value == null || value.trim().length() ==0){
            	continue;
            }
            try {
                parameter = parameter + keys.get(i) + "="
                    + URLEncoder.encode(value, input_charset) + "&";
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }

        parameter = parameter + "sign=" + sign + "&sign_type=" + sign_type;

        return sign;

    }


    private static String getContent(Map params, String privateKey) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";

		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}
        return prestr + privateKey;
    }
}