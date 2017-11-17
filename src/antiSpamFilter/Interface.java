package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
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
	private Table_Model modelo_tabela2;
	private JButton evaluate;
	private JButton save;
	private ArrayList<Integer> valores_pesos = new ArrayList<>();

	public Interface() {
		lista_regras = ReadRules.lerRules();
		addFrameContent();
	}

	private void addFrameContent() {

		frame = new JFrame("Filtro Anti-SPAM");
		frame.setLocation(50,50);
		frame.setSize(600, 500);
		frame.setLayout(new GridLayout(2,2));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



		//------------------ MANUAL PANEL ----------------------
		//Lista
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());

		modelo_tabela = new Table_Model();

		for(Table_object obj : lista_regras){
			modelo_tabela.add_regras(obj);
		}


		tabela = new JTable(modelo_tabela);
		JScrollPane scroll_tabela = new JScrollPane (tabela);
		listPanel.add(scroll_tabela, BorderLayout.CENTER);

		//Buttons
		JPanel buttonPanel = new JPanel();
		evaluate = new JButton("Avaliar configuração");

		evaluate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});

		save = new JButton("Gravar configuração manual");

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});

		buttonPanel.add(evaluate, BorderLayout.CENTER);
		buttonPanel.add(save, BorderLayout.SOUTH);
		listPanel.add(buttonPanel, BorderLayout.EAST);


		frame.add(listPanel);

		//------------------ AUTO PANEL-------------------
		//Lista
		JPanel listAUTOPanel = new JPanel();
		listAUTOPanel.setLayout(new BorderLayout());

		modelo_tabela2 = new Table_Model();

		for(Table_object obj : lista_regras){
			modelo_tabela2.add_regras(obj);
		}

		tabela = new JTable(modelo_tabela2);
		JScrollPane scroll_tabela2 = new JScrollPane (tabela);
		listAUTOPanel.add(scroll_tabela2, BorderLayout.CENTER);


		//Buttons
				JPanel buttonAUTOPanel = new JPanel();
				evaluate = new JButton("Avaliar configuração");

				evaluate.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//TODO
					}
				});

				save = new JButton("Gravar configuração auto");

				save.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//TODO
					}
				});

				buttonAUTOPanel.add(evaluate, BorderLayout.CENTER);
				buttonAUTOPanel.add(save, BorderLayout.SOUTH);
				listAUTOPanel.add(buttonAUTOPanel, BorderLayout.EAST);


				frame.add(listAUTOPanel);

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