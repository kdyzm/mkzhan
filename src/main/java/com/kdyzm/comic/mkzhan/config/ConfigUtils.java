package com.kdyzm.comic.mkzhan.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.kdyzm.comic.mkzhan.utils.FileUtils;

public class ConfigUtils {
	private static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
	private static ConfigModel configModel;

	/**
	 * 初始化配置文件
	 */
	public static void init() {
		File file = new File("config/config.json");
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file), "utf-8");
		} catch (FileNotFoundException e) {
			logger.error("目标配置文件不存在！", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("不支持的编码格式！", e);
		}
		Gson gson = new Gson();
		configModel = gson.fromJson(reader, ConfigModel.class);
		checkDirIsExists(configModel);
	}

	/**
	 * 检查目录是否存在，不存在则新建
	 * 
	 * @param configModel2
	 */
	private static void checkDirIsExists(ConfigModel configModel2) {
		FileUtils.createDirIfNotExists(configModel2.getBaseDownloadFileDir());
	}

	public static ConfigModel getConfig() {
		return configModel;
	}
}
