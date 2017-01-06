package com.liee.report.common;

import java.util.List;

/**
 * 柱状图数据 查询结果
 * @author lieeGin
 *
 */
public class ColumnResult {

	
	private String[] categories;
	
	private List<ColumnSerie> series;

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public List<ColumnSerie> getSeries() {
		return series;
	}

	public void setSeries(List<ColumnSerie> series) {
		this.series = series;
	}
	
}
