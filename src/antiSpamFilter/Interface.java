package antiSpamFilter;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Interface {

private ArrayList <Table_object> lista_regras;

	private JTable tabela;
	private JFrame frame;
	private Table_Model modelo_tabela;
	private JPanel painel_sul;
	
	private JButton botao_gravar;
	private JButton botao_avaliar;
	
	public Interface() {
		lista_regras = ReadRules.lerRules();
		addFrameContent();
	}

	private void addFrameContent() {

		frame = new JFrame("Filtro Anti-SPAM");
		frame.setLocation(50,50);
		frame.setSize(600, 500);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		//panelCenterWest.add();
		
		modelo_tabela = new Table_Model();
		
		for(Table_object obj : lista_regras){
			modelo_tabela.add_regras(obj);
		}
		
		tabela = new JTable(modelo_tabela);
		JScrollPane scroll_tabela = new JScrollPane (tabela);
		panel.add(scroll_tabela);
		frame.add(panel,BorderLayout.CENTER);
		
		painel_sul = new JPanel();
		frame.add(painel_sul, BorderLayout.SOUTH);
		
		botao_avaliar = new JButton ("Avaliar");
		botao_gravar = new JButton ("Gravar");
		
		painel_sul.add(botao_avaliar);
		painel_sul.add(botao_gravar);
		


	}
				
		public void open() {
			frame.setVisible(true);
		}
		//		
	
	public static void main(String[] args) {
		Interface grid = new Interface();
		grid.open();
	}

}