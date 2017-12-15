package antiSpamFilter;

/**
 * 
 * @author João Martins, João Teixeira, Mariana Barros, Rodrigo Cortesão.
 * 			65217			64750				65297			64822
 *
 */

public class Table_object {

	private String rule;
	private double value;

	public Table_object (String regra){
		this.rule = regra;
		value = 0;
	}

	/**
	 * Função para obter um valor de um objeto da tabela
	 * @return o valor do objeto
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Função para pôr um certo valor associado a um objeto da tabela
	 * @param valor
	 */
	public void setValor(double valor) {
		this.value = valor;
	}

	/**
	 * Função para obter uma regra
	 * @return a regra
	 */
	public String getRule() {
		return rule;
	}

}
