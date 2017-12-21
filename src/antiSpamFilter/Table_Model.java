package antiSpamFilter;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author Jo�o Martins, Jo�o Teixeira, Mariana Barros, Rodrigo Cortes�o.
 * 			65217			64750				65297			64822
 *
 */

public class Table_Model extends AbstractTableModel{

	private int num_cols = 2;
	private ArrayList <Table_object> objects = new ArrayList<>();

	/**
	 * Fun��o para obter o n�mero de colunas da tabela 
	 * @return o n�mero de colunas
	 */
	@Override
	public int getColumnCount() {
		return num_cols;
	}

	/**
	 * Fun��o para obter o n�mero de linhas da tabela
	 * @return tamanho da lista de objetos logo o n�mero de linhas
	 */
	@Override
	public int getRowCount() {
		return objects.size();
	}

	/**
	 * Fun��o para obter um objeto numa certa linha e coluna se estiver na primeira coluna � um objeto do tipo regra e na segunda coluna um valor
	 */
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return objects.get(row).getRule();

		case 1:
			return objects.get(row).getValue();
		default:
			return null;
		}	
	}

	/**
	 * Fun��o para obter o nome da coluna
	 */
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

	/**
	 * Adiciona um objeto regra�� lista de objetos
	 * @param table_object objeto regra
	 */
	public void add_regras (Table_object table_object){
		objects.add(table_object);
		fireTableDataChanged();

	}

	/**
	 * Fun��o para p�r um determinado valor numa certa coluna e linha
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		double value = Double.parseDouble(aValue.toString());
		if(value<=5 && value>=-5){
			super.setValueAt(aValue, rowIndex, columnIndex);
			objects.get(rowIndex).setValor(Double.parseDouble(aValue.toString()));
			fireTableDataChanged();
		}
	}

	/**
	 * Fun��o que torna a primeira coluna edit�vel e segunda n�o-edit�vel.
	 */
	public boolean isCellEditable(int row, int col) { 
		switch (col) {
		case 1:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Fun��o para obter os objetos de uma lista de objetos
	 * @return os objetos
	 */
	public ArrayList<Table_object> getObjectos() {
		return objects;
	}




}