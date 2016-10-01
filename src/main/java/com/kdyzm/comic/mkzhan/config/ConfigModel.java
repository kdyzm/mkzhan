package com.kdyzm.comic.mkzhan.config;

import java.util.List;

import com.kdyzm.comic.mkzhan.model.DownloadModel;
import com.kdyzm.comic.mkzhan.model.DownloadOptions;
import com.kdyzm.comic.mkzhan.model.RequestConfigInfo;

public class ConfigModel {
	private String defaultBaseDownloadUrlPrefix;
	private String baseDownloadFileDir;
	private List<DownloadModel> webSites;
	private RequestConfigInfo requestConfigInfo;
	private DownloadOptions downloadOptions;
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DownloadOptions getDownloadOptions() {
		return downloadOptions;
	}

	public void setDownloadOptions(DownloadOptions downloadOptions) {
		this.downloadOptions = downloadOptions;
	}

	public RequestConfigInfo getRequestConfigInfo() {
		return requestConfigInfo;
	}

	public void setRequestConfigInfo(RequestConfigInfo requestConfigInfo) {
		this.requestConfigInfo = requestConfigInfo;
	}

	public List<DownloadModel> getWebSites() {
		return webSites;
	}

	public void setWebSites(List<DownloadModel> webSites) {
		this.webSites = webSites;
	}

	public String getDefaultBaseDownloadUrlPrefix() {
		return defaultBaseDownloadUrlPrefix;
	}

	public void setDefaultBaseDownloadUrlPrefix(String defaultBaseDownloadUrlPrefix) {
		this.defaultBaseDownloadUrlPrefix = defaultBaseDownloadUrlPrefix;
	}

	public String getBaseDownloadFileDir() {
		return baseDownloadFileDir;
	}

	public void setBaseDownloadFileDir(String baseDownloadFileDir) {
		this.baseDownloadFileDir = baseDownloadFileDir;
	}
}
