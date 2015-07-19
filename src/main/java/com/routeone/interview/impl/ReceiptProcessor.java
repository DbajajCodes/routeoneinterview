package com.routeone.interview.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.routeone.interview.Receipt;
import com.routeone.interview.valueobject.Item;
import com.routeone.interview.valueobject.ReturnReceipt;

public class ReceiptProcessor implements Receipt {

	private static final String DOLLAR_SIGN = "$";
	ReturnReceipt returnReceipt = new ReturnReceipt();
	List<Item> filteredItemsFromInventory = new ArrayList<Item>();

	private String formattedTotal;
	private List<String> orderedItems = new ArrayList<String>();

	public ReceiptProcessor(List<Item> filteredItemsFromInventory) {
		this.filteredItemsFromInventory = filteredItemsFromInventory;
	}

	private void setFormattedTotal() {
		Double total = 0d;
		for (Item item : this.filteredItemsFromInventory) {
			total += item.getPrice();
		}
		this.formattedTotal = formatTotalInExpectedFormat(total);
		System.out.println("Formatted total for all the valid input items >>"+this.formattedTotal);
	}

	private String formatTotalInExpectedFormat(Double total) {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		df.setMinimumIntegerDigits(1);
		return DOLLAR_SIGN + df.format(total).toString();
	}

	private void setOrderedItems() {
		for (int i = 0; i < this.filteredItemsFromInventory.size() - 1; i++) {
			for (int next = i + 1; next < this.filteredItemsFromInventory.size(); next++) {
				Item firstItem = filteredItemsFromInventory.get(i);
				Item nextItem = filteredItemsFromInventory.get(next);
				if (firstItem.getPrice() < nextItem.getPrice()) {
					filteredItemsFromInventory.set(next, firstItem);
					filteredItemsFromInventory.set(i, nextItem);
				}
			}
		}

		for (Item item : this.filteredItemsFromInventory) {
			this.orderedItems.add(item.getComponentName());
		}
		System.out.println("Item names ordered descending per price >>"+this.orderedItems);
	}

	public String getFormattedTotal() {
		return this.formattedTotal;
	}

	public List<String> getOrderedItems() {
		return this.orderedItems;
	}

	public Receipt process() {
		this.setFormattedTotal();
		this.setOrderedItems();
		return this;
	}

}
