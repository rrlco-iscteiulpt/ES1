package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import antiSpamFilter.Table_Model;
import antiSpamFilter.Table_object;

public class Table_ModelTest {

	private ArrayList <Table_object> objectos = new ArrayList<>();
	private Object objeto = new Object();

	@Test
	public void tableModelTest(){
		Table_Model tm = new Table_Model();

		assertEquals(tm.getObjectos().size(), tm.getRowCount());

		int column = tm.getColumnCount();
		int row = tm.getRowCount();
		String obj = tm.getColumnName(column);

		assertEquals(objectos.size(), tm.getRowCount());

		Table_object to = new Table_object(obj);
		objectos.add(to);

		assertNotEquals(objectos.size(), tm.getRowCount());
		tm.getRowCount();
		tm.add_regras(to);

		assertEquals(tm.getValueAt(row, column), tm.getValueAt(row, column) );
		assertFalse(tm.isCellEditable(row, column));
		int colum_new = tm.getColumnCount()+1;
		tm.isCellEditable(row, colum_new);
		int col=1;
		int row1=0;
		for(int i=0 ; i<objectos.size();i++){
			assertEquals(objectos.get(row).getRule(), objectos.get(i).getRule());
			assertEquals(tm.getValueAt(row, column), objectos.get(i).getRule());
			assertNotEquals(tm.getValueAt(row, column), objectos.get(i).getValue());
		}
		assertTrue(tm.isCellEditable(row1, col));
		assertFalse(tm.isCellEditable(col, row1));

		for(Table_object t: objectos){
			assertNotEquals(t.getRule(), tm.getValueAt(row, col));
			assertNotEquals(t.getValue(), tm.getValueAt(row, row1));
		}

		objectos.get(row).setValor(colum_new);
		tm.setValueAt(tm.getObjectos().size(), row1, column);

	
}

}
