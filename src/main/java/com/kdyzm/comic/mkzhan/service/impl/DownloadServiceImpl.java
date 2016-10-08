package com.kdyzm.comic.mkzhan.service.impl;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kdyzm.comic.mkzhan.config.ConfigUtils;
import com.kdyzm.comic.mkzhan.model.DownloadModel;
import com.kdyzm.comic.mkzhan.service.DownloadService;
import com.kdyzm.comic.mkzhan.utils.FileUtils;
import com.kdyzm.comic.mkzhan.utils.StringUtils;
import com.kdyzm.comic.mkzhan.utils.Utils;

public class DownloadServiceImpl implements DownloadService {
	private Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);

	@Override
	public boolean startDownload() {
		List<DownloadModel> list = ConfigUtils.getConfig().getWebSites();
		for (DownloadModel download : list) {
			if(!download.getEnable()){
				logger.warn("《{}》被设置跳过下载",download.getName());
				continue;
			}
			logger.info("开始解析 《{}》 的所有章节url。。。", download.getName());
			File comicDir = FileUtils
					.createDirIfNotExists(ConfigUtils.getConfig().getBaseDownloadFileDir() + "/" + download.getName());
			File urlsDir = FileUtils.createDirIfNotExists(comicDir,
					ConfigUtils.getConfig().getDownloadOptions().getUrlsDirName());
			File picsDir = FileUtils.createDirIfNotExists(comicDir,
					ConfigUtils.getConfig().getDownloadOptions().getPicsDirName());
			Document document = Utils.getDocument(download.getUrl());
			Element charpterSummary = document.getElementsByClass("Chapter_lists").first();
			Elements allChapterUrl = charpterSummary.getElementsByTag("a");
			for (int i = 0; i < allChapterUrl.size(); i++) {
				Element charpter = allChapterUrl.get(i);
				String charpterUrl = ConfigUtils.getConfig().getUrl() + charpter.attr("href").trim();
				String charpterName = StringUtils.getZeroPrifixFileName(i) + "_" + charpter.attr("title").trim();
				File chapterPicsDir = FileUtils.createDirIfNotExists(picsDir, charpterName);
				if (FileUtils.size(chapterPicsDir) != 0) {
					logger.warn("章节《{}》已经下载完成，即将跳过", charpterName);
					continue;
				}
				File charpterUrlFile = getUrlsFileByCharpterUrl(charpterUrl, download.getBaseDownloadUrlPrefix(), urlsDir, charpterName);
				downloadComicsFromUrlFile(charpterUrlFile,chapterPicsDir);
			}
		}
		return true;
	}
	
	private void downloadComicsFromUrlFile(File charpterUrlFile,File chapterPicsDir) {
		List<String> list=FileUtils.readUrlFile(charpterUrlFile);
		FileUtils.downLoadFile(list,chapterPicsDir);
	}

	/**
	 * 
	 * @param charpterUrl
	 *            获取章节所有pics必须的条件
	 * @param urlsDir
	 *            创建url File的条件
	 * @param charpterName
	 *            创建url File的条件
	 * @return
	 */
	private File getUrlsFileByCharpterUrl(String charpterUrl, String baseDownloadUrlPrefix, File urlsDir, String charpterName) {
		File urlFile = FileUtils.exists(urlsDir, charpterName+".txt");
		if(urlFile!=null){
			logger.warn("文件 <{}> 已经存在 .",urlFile.getPath());
			return urlFile;
		}
		urlFile=FileUtils.createFileIfNotExists(urlsDir, charpterName+".txt");
		Document document = Utils.getDocument(charpterUrl);
		String html = document.html();
		String regex = "comicimg.*(\\[.*\\])";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(html);
		while(matcher.find()){
			String result=matcher.group(1);
			List<String> list=new Gson().fromJson(result, new TypeToken<List<String>>(){}.getType());
			FileUtils.writeToFile(urlFile,baseDownloadUrlPrefix,list);
		}
		return urlFile;
	}

}
