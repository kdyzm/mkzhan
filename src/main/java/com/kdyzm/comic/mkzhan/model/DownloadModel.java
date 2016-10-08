package com.kdyzm.comic.mkzhan.model;

public class DownloadModel {
	private String url;
	private String name;
	private String baseDownloadUrlPrefix;
	private Boolean enable;
	
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBaseDownloadUrlPrefix() {
		return baseDownloadUrlPrefix;
	}

	public void setBaseDownloadUrlPrefix(String baseDownloadUrlPrefix) {
		this.baseDownloadUrlPrefix = baseDownloadUrlPrefix;
	}
}
