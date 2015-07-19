package com.routeone.interview;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.routeone.interview.exceptions.InvalidInputListException;
import com.routeone.interview.exceptions.NoItemFoundException;
import com.routeone.interview.impl.ReceiptProcessor;
import com.routeone.interview.valueobject.Item;
public class StoreRegister {
 
	ReceiptProcessor processor;
	List<Item> itemsFromInventory = new ArrayList<Item>();
	private static final String DELIMITER = ",";
	BufferedReader fileReader = null;
	List<Item> filteredItemsFromInventory = new ArrayList<Item>();
	
    public void loadInventory(File inventoryFile) throws NoItemFoundException, IOException, InvalidInputListException{
    	
    	
    	if(inventoryFile==null){
    		throw new NoItemFoundException("There is no valid input file to read");
    	}
    	fileReader = new BufferedReader(new FileReader(inventoryFile));
    	String nextLine;
    	while((nextLine = fileReader.readLine())!=null){
    		String[] itemEntry = nextLine.split(DELIMITER);
    		if(itemEntry.length!=3){
    			throw new InvalidInputListException("Input List has more than 3 entries");
    		}
    		Item item = new Item(itemEntry[0],new Double(itemEntry[1]),itemEntry[2]);
    		this.itemsFromInventory.add(item);
    	}
    }
	
	public Receipt checkoutOrder(List<String> items) throws InvalidInputListException {
		validateAndFilter(items);
		processor = new ReceiptProcessor(filteredItemsFromInventory);
        return this.processor.process();
    }

	private void validateAndFilter(List<String> items) throws InvalidInputListException{
		for(String item:items){
			Iterator<Item> itemIterator = this.itemsFromInventory.iterator();
			validateInputInCategoryList(item, itemIterator);
		}
	}

	private void validateInputInCategoryList(String item,
			Iterator<Item> itemIterator) throws InvalidInputListException {
		boolean itemPresent=false;
		while(itemIterator.hasNext()){
			Item currentItem = itemIterator.next();
			String itemNameInList = currentItem.getComponentName();
			if(itemNameInList.equalsIgnoreCase(item)){
			itemPresent =  true;
			this.filteredItemsFromInventory.add(currentItem);
			break;
			}
		}
		if(!itemPresent){
			throw new InvalidInputListException("Invalid Input provided >> "+item);
		}
	}
}