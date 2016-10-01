package com.kdyzm.comic.mkzhan.utils;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kdyzm.comic.mkzhan.config.ConfigUtils;

public class Utils {
	private static Logger logger = LoggerFactory.getLogger(Utils.class);

	// 获取Document对象的方法。
	public static Document getDocument(String url) {
		logger.info("start http request for url={}",url);
		Connection conn = Jsoup.connect(url);
		conn.timeout(3000);// 设置超时时间是3秒钟
		conn.header("Cookie", ConfigUtils.getConfig().getRequestConfigInfo().getCookie());
		conn.header("Host", "www.mkzhan.com");
		conn.header("Referer", "http://www.mkzhan.com/");
		conn.header("Upgrade-Insecure-Requests", "1");
		conn.header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");
		conn.header("Connection", "keep-alive");
		conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.header("Accept-Encoding", "gzip, deflate, sdch");
		conn.header("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6,ja;q=0.4");
		conn.header("Cache-Control", "max-age=0");
		conn.header("Connection", "keep-alive");
		Document document = null;
		for (int i = 1; i <= 5; i++) {
			try {
				document = conn.get();
				logger.info("end http request for url={}",url);
				return document;
			} catch (IOException e) {
				logger.warn("连接资源的时候超时！即将进行第 {}次尝试！", i, e);
			}
		}
		logger.error("尝试连接资源五次之后失败！");
		return null;
	}
}
