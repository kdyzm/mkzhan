package com.kdyzm.comic.mkzhan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kdyzm.comic.mkzhan.config.ConfigUtils;
import com.kdyzm.comic.mkzhan.service.DownloadService;
import com.kdyzm.comic.mkzhan.service.impl.DownloadServiceImpl;

public class App 
{
	private static Logger logger=LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
    	logger.info("初始化配置文件中。。。");
    	ConfigUtils.init();
    	logger.info("初始化配置文件完成。");
    	DownloadService downloadService=new DownloadServiceImpl();
    	logger.info("下载链接到文件。");
    	downloadService.startDownload();
    }
}
