package com.routeone.interview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.routeone.interview.exceptions.InvalidInputListException;
import com.routeone.interview.exceptions.NoItemFoundException;
import com.routeone.interview.impl.ReceiptProcessor;
import com.routeone.interview.valueobject.Item;

@RunWith(MockitoJUnitRunner.class)
public class StoreRegisterTest {

	String fileLoadPath;
	private static String PATH_TO_BAD_FILE;

	@InjectMocks
	private StoreRegister storeRegister = new StoreRegister();

	@Mock
	ReceiptProcessor processor;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		PATH_TO_BAD_FILE = this.getClass().getResource("/InputFiles/sample-inventory-BAD.csv").getFile();
		fileLoadPath = this.getClass().getResource("/InputFiles/sample-inventory.csv").getFile();
		
	}

	@Test(expected = NoItemFoundException.class)
	public void testLoadInventoryThrowsAnExceptionIfFileInputIsEmpty()
			throws Exception {
		this.storeRegister.loadInventory(null);
	}

	@Test
	public void testLoadInventoryReadsTheValidInputFileCorrectly()
			throws Exception {
		this.storeRegister.loadInventory(new File(fileLoadPath));
		String csvEntry = "PC1033, 19.99, RAM";
		Item actualItemRead = (Item) this.storeRegister.itemsFromInventory.toArray()[0];
		String actualItemReadStr = actualItemRead.getComponentName()+", "+actualItemRead.getPrice()+", "+actualItemRead.getCategory();
		assertEquals(csvEntry, actualItemReadStr);
	}
	
	@Test(expected=InvalidInputListException.class)
	public void testLoadInventoryFileThrowsInvalidExceptionIfInputFileHasMoreThan3EntriesPerItemLine() throws Exception{
		createBadInputFile();
		this.storeRegister.loadInventory(new File(PATH_TO_BAD_FILE));
		
	}

	private void createBadInputFile() throws IOException {
		String badEntry= "PC1033, 20.0, RAM, DUMMY";
		String goodEntry= "PC1033, 20.0, RAM";
		FileWriter fileWriter = new FileWriter(PATH_TO_BAD_FILE);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		writer.write(badEntry);
		writer.newLine();
		writer.write(goodEntry);
		writer.flush();
		writer.close();
		fileWriter.close();
	}

	@Test
	public void testLoadInventoryReadsTheValidInputFileCorrectlyAndLoadsDataIntoItemsList()
			throws Exception {
		loadInputInventoryFile(fileLoadPath);
		Item testEntry = new Item("PC1033", 19.99, "RAM");
		assertTrue(this.storeRegister.itemsFromInventory.contains(testEntry));
	}

	private void loadInputInventoryFile(String fileLoadPath)
			throws NoItemFoundException, IOException, InvalidInputListException {
		this.storeRegister.loadInventory(new File(fileLoadPath));
	}

	@Test
	public void assertThatCheckoutOrderCallsReceiptProcessor() throws Exception {
		loadInputInventoryFile(fileLoadPath);
		List<String> validInputItemsList = Arrays.asList("PC1033", "LCD",
				"KeyBoard");
		Receipt expected = this.storeRegister.checkoutOrder(validInputItemsList);
		assertEquals("$233.99", expected.getFormattedTotal());

	}

	@Test(expected = InvalidInputListException.class)
	public void assertThatCheckOutOrderValidatesInputBeforeCallingReceiptProcessor()
			throws Exception {
		loadInputInventoryFile(fileLoadPath);
		List<String> invalidInputItemsList = Arrays.asList(
				RandomStringUtils.random(3), RandomStringUtils.random(3),
				RandomStringUtils.random(3));
		this.storeRegister.checkoutOrder(invalidInputItemsList);
	}

}
