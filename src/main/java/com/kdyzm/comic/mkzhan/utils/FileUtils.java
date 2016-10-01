package com.kdyzm.comic.mkzhan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kdyzm.comic.mkzhan.config.ConfigUtils;

public class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 判断文件夹是否存在，如果不存在则创建一个新文件夹
	 * 
	 * @param dir
	 * @return
	 */
	public static File createDirIfNotExists(String dir) {
		File file = new File(dir);
		return createDirIfNotExists(file);
	}

	public static File createDirIfNotExists(File dir, String urlsDirName) {
		File file = new File(dir, urlsDirName);
		return createDirIfNotExists(file);
	}

	private static File createDirIfNotExists(File file) {
		if (!file.exists()) {
			logger.info("目录<{}>不存在，即将创建新目录。", file.getPath());
			file.mkdirs();
			return file;
		}
		return file;
	}

	public static int size(File chapterPicsDir) {
		if (chapterPicsDir.isDirectory()) {
			return chapterPicsDir.listFiles().length;
		}
		logger.error("文件<{}>非目录！", chapterPicsDir.getPath());
		return 0;
	}

	public static File createFileIfNotExists(File urlsDir, String charpterName) {
		File file = new File(urlsDir, charpterName);
		file=createFileIfNotExists(file);
		if(!file.exists()){
			String errorFile="章节名称错误.txt";
			logger.warn("即将尝试创建文件  {} 修复 文件 <> 错误。",errorFile,charpterName);
			file = new File(urlsDir, errorFile);
			createFileIfNotExists(file);
		}
		return file;
	}

	private static File createFileIfNotExists(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error("创建文件<{}>失败！", file.getPath(), e);
			}
		}
		return file;
	}

	public static PrintWriter getPrintWriter(File file) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(file), true);
			return pw;
		} catch (FileNotFoundException e) {
			logger.error("文件<{}>不存在。", file.getPath(), e);
		}
		return null;
	}

	public static void writeToFile(File urlFile, String baseDownloadUrlPrefix, List<String> list) {
		PrintWriter pw = getPrintWriter(urlFile);
		for (String url : list) {
			pw.println((baseDownloadUrlPrefix == null || baseDownloadUrlPrefix.trim().equals(""))
					? ConfigUtils.getConfig().getDefaultBaseDownloadUrlPrefix() + url
					: baseDownloadUrlPrefix.trim() + url);
		}
		pw.close();
	}

	public static File exists(File urlsDir, String string) {
		File file = new File(urlsDir, string);
		if (file.exists()) {
			return file;
		}
		return null;
	}

	public static List<String> readUrlFile(File charpterUrlFile) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(charpterUrlFile)));
			List<String> list = new ArrayList<String>();
			String str = null;
			while ((str = br.readLine()) != null) {
				list.add(str);
			}
			br.close();
			return list;
		} catch (FileNotFoundException e) {
			logger.error("文件<{}>不存在！", charpterUrlFile, e);
		} catch (IOException e) {
			logger.error("读写异常！", e);
		}
		return null;
	}

	public static void downLoadFile(List<String> list, File chapterPicsDir) {
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			try {
				URL url = new URL(str);
				InputStream is = url.openStream();
				String fileName = StringUtils.getZeroPrifixFileName(i) + str.substring(str.lastIndexOf("."));
				File file = new File(chapterPicsDir, fileName);
				OutputStream os = new FileOutputStream(file);
				int length = -1;
				byte[] buff = new byte[10240];
				while ((length = is.read(buff)) != -1) {
					os.write(buff, 0, length);
				}
				is.close();
				os.close();
			} catch (MalformedURLException e) {
				logger.error("格式不正确的url={}", str);
			} catch (IOException e) {
				logger.error("读写异常,url={}", str, e);
			}
		}
	}
}
