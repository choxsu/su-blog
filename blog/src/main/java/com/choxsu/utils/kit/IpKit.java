

package com.choxsu.utils.kit;

import com.alibaba.fastjson.JSONObject;
import com.choxsu.common.pageview.AddressVo;
import com.jfinal.kit.HttpKit;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * IpKit 获取 ip 地址的工具类
 */
public class IpKit {

	private static final String addrUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=[ip地址字串]";
	
	public static String getRealIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getRealIpV2(HttpServletRequest request) {
		String accessIP = request.getHeader("x-forwarded-for");
        if (null == accessIP)
            return request.getRemoteAddr();
        return accessIP;
	}
	
	/**
	 * 获取本机 ip
	 * @return 本机IP
	 */
	public static String getLocalIp() throws SocketException {
		String localip = null;		// 本地IP，如果没有配置外网IP则返回
		String netip = null;		// 外网IP
		
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false;		// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
				}
			}
		}
		
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}


	public static AddressVo getAddress(String ip){
		String url = addrUrl.replace("[ip地址字串]", ip);
		String addressJson = HttpKit.get(url);
		JSONObject jsonObject = JSONObject.parseObject(addressJson);
		if (jsonObject.getInteger("code") == 0) {
			JSONObject data = jsonObject.getJSONObject("data");
			String country = data.getString("country");
			String city = data.getString("city");
			String region = data.getString("region");
			String isp = data.getString("isp");
			return AddressVo.builder().address(country + "/" + city + "/" + region + "["+ isp +"]").addressJson(addressJson).build();
		}
		return AddressVo.builder().address("XX").addressJson("{}").build();
	}

}





