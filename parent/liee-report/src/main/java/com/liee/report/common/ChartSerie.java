package com.liee.report.common;

import java.math.BigDecimal;

/**
 * 图形数据对象
 * @author lieeGin
 *
 */
public class ChartSerie {

	
	private String name;
	
	private BigDecimal[] data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal[] getData() {
		return data;
	}

	public void setData(BigDecimal[] data) {
		this.data = data;
	}
	
	
}
