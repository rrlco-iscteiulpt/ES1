package jUnitTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import antiSpamFilter.ReadRules;
import antiSpamFilter.Table_object;

public class ReadRulesTest {

	@Test
	public void testelerRules(){
		ReadRules r = new ReadRules();
		ArrayList<Table_object> testRules= ReadRules.readRules("rules.cf");

		assertEquals("BAYES_00", testRules.get(0).getRule());
		assertNotEquals("BAYES_00", testRules.get(1).getRule());

		ArrayList<Table_object> teste2 =ReadRules.readRules("novo.txt");
		assertNotSame(ReadRules.readRules("readrules"), ReadRules.readRules("rules.cf"));
	}
}




