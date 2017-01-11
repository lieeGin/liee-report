package com.liee.report.common;

import java.util.List;

/**
 * 图形数据 查询结果
 * @author lieeGin
 *
 */
public class ChartResult<T extends ChartSerie> {

	
	private String[] categories;
	
	private List<T> series;

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public List<T> getSeries() {
		return series;
	}

	public void setSeries(List<T> series) {
		this.series = series;
	}
	
}
