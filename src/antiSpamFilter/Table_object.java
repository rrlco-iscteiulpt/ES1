package antiSpamFilter;

public class Table_object {
	
	private String regra;
	private int valor;
	
	public Table_object (String regra){
		this.regra = regra;
		valor = 0;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getRegra() {
		return regra;
	}

}
