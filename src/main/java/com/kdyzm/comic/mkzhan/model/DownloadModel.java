package com.kdyzm.comic.mkzhan.model;

public class DownloadModel {
	private String url;
	private String name;
	private String baseDownloadUrlPrefix;

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
