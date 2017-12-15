package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import antiSpamFilter.Table_object;

public class Table_objectTest {

	@Test
	public void  table_objTest(){
		String regra = "objeto";
		Table_object to = new Table_object(regra);
		double regra1 = to.getValue();
		to.setValor(regra1);
		to.getRule();
	}

}
