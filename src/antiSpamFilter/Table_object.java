package antiSpamFilter;

public class Table_object {
	
	private String regra;
	private double valor;
	
	public Table_object (String regra){
		this.regra = regra;
		valor = 0;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getRegra() {
		return regra;
	}

}
