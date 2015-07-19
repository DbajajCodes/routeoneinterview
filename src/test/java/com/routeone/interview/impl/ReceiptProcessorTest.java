package com.routeone.interview.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.routeone.interview.Receipt;
import com.routeone.interview.valueobject.Item;

@RunWith(JUnit4.class)
public class ReceiptProcessorTest {
	
ReceiptProcessor processor = new ReceiptProcessor(Arrays.asList(
		new Item("PC1033", 200.0, "RAM"),
		new Item("PC800", 99.9, "RAM"),
		new Item("LCD", 750.0, "PERIPHERAL"),
		new Item("Keyboard", 150.0, "MISC")
		));

Receipt expectedReceipt ;

@Before
public void setUp() {
	expectedReceipt = 	this.processor.process();
}

@Test
public void testThatGetFormattedTotalContainsValidOutput() throws Exception {
	assertEquals("$1,199.90", expectedReceipt.getFormattedTotal());
}

@Test
public void testThatGetOrderedItemsReturnsListOfStringsInExpectedOrder() throws Exception {
	List<String> expectedList = Arrays.asList("LCD", "PC1033", "Keyboard", "PC800");
	assertThat(expectedReceipt.getOrderedItems(), Matchers.equalTo(expectedList));
}
	
}
