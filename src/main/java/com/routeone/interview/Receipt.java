package com.routeone.interview;
import java.util.List;
public interface Receipt {

	/**
     * @return Currency formatted total ($X.XX) of all items
     */
    public String getFormattedTotal();
 
    /**
     * @return List of all items in descending order by amount
     */
    public List<String> getOrderedItems();

}