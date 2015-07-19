package com.routeone.interview;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.routeone.interview.exceptions.InvalidInputListException;
import com.routeone.interview.exceptions.NoItemFoundException;

public class InventoryRunner {

	
	public static void main(String[] args) throws NoItemFoundException, IOException, InvalidInputListException {
		String pathToFile;
		if((args.length==0)||(!isNotBlank(args[0]))){
			pathToFile= StoreRegister.class.getResource("/InputFiles/sample-inventory.csv").getFile();
		}else
		{
			pathToFile=args[0];
		}
		File fileToLoad= new File(pathToFile);
		StoreRegister register = new StoreRegister();
		List<String> inputItemsList = Arrays.asList("PC1033", "GenericMotherboard",
				"Mouse","LCD");
		register.loadInventory(fileToLoad);
		register.checkoutOrder(inputItemsList);
		
	}

}
