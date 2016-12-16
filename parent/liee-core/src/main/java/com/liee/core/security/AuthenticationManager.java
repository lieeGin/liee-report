package com.liee.core.security;

import java.util.List;

public class AuthenticationManager {

	private List<AbstractAuthFilter> filterList ;

	public List<AbstractAuthFilter> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<AbstractAuthFilter> filterList) {
		this.filterList = filterList;
	}
	
}
