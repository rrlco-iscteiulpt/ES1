package antiSpamFilter;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author João Martins, João Teixeira, Mariana Barros, Rodrigo Cortesão.
 * 			65217			64750				65297			64822
 *
 */

public class Table_Model extends AbstractTableModel{

	private int num_cols = 2;
	private ArrayList <Table_object> objects = new ArrayList<>();

	/**
	 * Função para obter o número de colunas da tabela 
	 * @return o número de colunas
	 */
	@Override
	public int getColumnCount() {
		return num_cols;
	}

	/**
	 * Função para obter o número de linhas da tabela
	 * @return tamanho da lista de objetos logo o número de linhas
	 */
	@Override
	public int getRowCount() {
		return objects.size();
	}

	/**
	 * Função para obter um objeto numa certa linha e coluna se estiver na primeira coluna é um objeto do tipo regra e na segunda coluna um valor
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
	 * Função para obter o nome da coluna
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
	 * Adiciona um objeto regra à lista de objetos
	 * @param table_object objeto regra
	 */
	public void add_regras (Table_object table_object){
		objects.add(table_object);
		fireTableDataChanged();

	}

	/**
	 * Função para pôr um determinado valor numa certa coluna e linha
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
	 * Função que torna a primeira coluna edit·vel e segunda não-editável.
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
	 * Função para obter os objetos de uma lista de objetos
	 * @return os objetos
	 */
	public ArrayList<Table_object> getObjectos() {
		return objects;
	}




}