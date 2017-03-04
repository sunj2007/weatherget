package com.jj.yy.weatherget;

import java.util.ArrayList;

public class InfoItem {
	private String city;
	private String desc;
	private ArrayList<Forecast> forecastList;
	private String ganmao;
	private String status;
	private String wendu;

	public String getCity() {
		return this.city;
	}

	public String getDesc() {
		return this.desc;
	}

	public ArrayList<Forecast> getForecastList() {
		return this.forecastList;
	}

	public String getGanmao() {
		return this.ganmao;
	}

	public String getStatus() {
		return this.status;
	}

	public String getWendu() {
		return this.wendu;
	}

	public void setCity(String paramString) {
		this.city = paramString;
	}

	public void setDesc(String paramString) {
		this.desc = paramString;
	}

	public void setForecastList(ArrayList<Forecast> paramArrayList) {
		this.forecastList = paramArrayList;
	}

	public void setGanmao(String paramString) {
		this.ganmao = paramString;
	}

	public void setStatus(String paramString) {
		this.status = paramString;
	}

	public void setWendu(String paramString) {
		this.wendu = paramString;
	}
}