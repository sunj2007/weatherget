package com.jj.yy.weatherget;

public class Forecast {
	private String date;
	private String fengli;
	private String fengxiang;
	private String high;
	private String low;
	private String type;

	public String getDate() {
		return this.date;
	}

	public String getFengli() {
		return this.fengli;
	}

	public String getFengxiang() {
		return this.fengxiang;
	}

	public String getHigh() {
		return this.high;
	}

	public String getLow() {
		return this.low;
	}

	public String getType() {
		return this.type;
	}

	public void setDate(String paramString) {
		this.date = paramString;
	}

	public void setFengli(String paramString) {
		this.fengli = paramString;
	}

	public void setFengxiang(String paramString) {
		this.fengxiang = paramString;
	}

	public void setHigh(String paramString) {
		this.high = paramString;
	}

	public void setLow(String paramString) {
		this.low = paramString;
	}

	public void setType(String paramString) {
		this.type = paramString;
	}
}
