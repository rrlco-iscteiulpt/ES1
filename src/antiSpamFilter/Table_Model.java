package antiSpamFilter;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class Table_Model extends AbstractTableModel{
	
	private int num_cols = 2;
	private ArrayList <Table_object> objectos = new ArrayList<>();
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return num_cols;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return objectos.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		switch (coluna) {
		case 0:
			return objectos.get(linha).getRegra();
			
		case 1:
			return objectos.get(linha).getValor();
		default:
			return null;
		}
		
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Regra";
		case 1:
			return "Peso Vector";

		default:
			return null;
		}
	}
	
	public void add_regras (Table_object table_object){
		objectos.add(table_object);
		fireTableDataChanged();
		
	}

	public ArrayList<Table_object> getObjectos() {
		return objectos;
	}

}