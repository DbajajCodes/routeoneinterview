package com.routeone.interview.valueobject;

import lombok.Data;

@Data
public class Item {

	public Item(String name, double price, String category) {
		this.componentName=name;
		this.price=price;
		this.category=category;
	}
	private String componentName;
	private Double price;
	private String category;
	
}

