package com.routeone.interview.valueobject;

import java.util.List;

import lombok.Data;

@Data
public class ReturnReceipt {

	private List<String> orderedItemsList;
	private Double totalCost;
	
}
