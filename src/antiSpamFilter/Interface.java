package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Interface {

	private ArrayList <Table_object> lista_regras;
	private JTable tabela;
	private JFrame frame;
	private Table_Model modelo_tabela;
	private Table_Model modelo_tabela2;
	private JButton evaluate;
	private JButton save;
	private JTextField FN;
	private JTextField FP;
	private JTextField FN2;
	private JTextField FP2;
	
 
	private ArrayList<Double> valores_pesos = new ArrayList<>();


	private HashMap<String, ArrayList> hash_ham;
	private HashMap<String, ArrayList> hash_ham_NEG;

	public Interface() {
		lista_regras = ReadRules.lerRules();
		addFrameContent();
	}

	private void addFrameContent() {

		frame = new JFrame("Filtro Anti-SPAM");
		frame.setLocation(100,25);
		frame.setSize(900, 650);
		frame.setLayout(new GridLayout(2,2));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



		//------------------ MANUAL PANEL ----------------------
		//Lista
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(1, 2));

		modelo_tabela = new Table_Model();

		for(Table_object obj : lista_regras){
			modelo_tabela.add_regras(obj);
		}


		tabela = new JTable(modelo_tabela);
		JScrollPane scroll_tabela = new JScrollPane (tabela);
		listPanel.add(scroll_tabela, BorderLayout.CENTER);

		//Text field
		JPanel NEG = new JPanel(new BorderLayout());
		JLabel FN_ = new JLabel("Falsos Negativos");
		FN = new JTextField();
		NEG.add(FN_, BorderLayout.WEST);
		NEG.add(FN, BorderLayout.CENTER);
		FN.setEditable(false);
		
		JPanel POS = new JPanel(new BorderLayout());
		JLabel FP_ = new JLabel("Falsos Positivos");
		FP = new JTextField();
		POS.add(FP_, BorderLayout.WEST);
		POS.add(FP, BorderLayout.CENTER);
		FP.setEditable(false);
		
		//Buttons
		JPanel buttonPanel = new JPanel(new GridLayout(2,2));
		evaluate = new JButton("Avaliar configuração");

		evaluate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcFP();
				calcFN();
			}
		});

		save = new JButton("Gravar configuração manual");

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {



				ArrayList <String> lista_para_escrever_no_ficheiro = new ArrayList<>();
				String s = new String();
				for(Table_object valor : modelo_tabela.getObjectos()){
					valores_pesos.add(valor.getValor());
					s = valor.getRegra() + ":" + valor.getValor();
					lista_para_escrever_no_ficheiro.add(s);

				}
				try {
					PrintWriter pw = new PrintWriter("rules.cf");
					for(String sss : lista_para_escrever_no_ficheiro){
						pw.println(sss);
					}
					pw.close();

				} catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println(lista_para_escrever_no_ficheiro);
				lista_para_escrever_no_ficheiro.clear();
			}
		});

		
		buttonPanel.add(evaluate);
		buttonPanel.add(save);
		buttonPanel.add(NEG);
		buttonPanel.add(POS);
		
		listPanel.add(buttonPanel);


		frame.add(listPanel);

		//------------------ AUTO PANEL-------------------
		//Lista
		JPanel listAUTOPanel = new JPanel();
		listAUTOPanel.setLayout(new GridLayout(1, 2));

		modelo_tabela2 = new Table_Model();

		for(Table_object obj : lista_regras){
			modelo_tabela2.add_regras(obj);
		}

		tabela = new JTable(modelo_tabela2);
		JScrollPane scroll_tabela2 = new JScrollPane (tabela);
		listAUTOPanel.add(scroll_tabela2, BorderLayout.WEST);

		//Text field
				JPanel NEG2 = new JPanel(new BorderLayout());
				JLabel FN_2 = new JLabel("Falsos Negativos");
				FN2 = new JTextField();
				NEG2.add(FN_2, BorderLayout.WEST);
				NEG2.add(FN2, BorderLayout.CENTER);
				FN2.setEditable(false);
				
				JPanel POS2 = new JPanel(new BorderLayout());
				JLabel FP_2 = new JLabel("Falsos Positivos");
				FP2 = new JTextField();
				POS2.add(FP_2, BorderLayout.WEST);
				POS2.add(FP2, BorderLayout.CENTER);
				FP2.setEditable(false);
				
		//Buttons
		JPanel buttonAUTOPanel = new JPanel(new GridLayout(2,2));
		evaluate = new JButton("Gerar configuração automática");

		evaluate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					AntiSpamFilterAutomaticConfiguration.main(null);
					String[] tokens=lerAntimSpamRS();
					int t=0;
					for(Table_object valor : modelo_tabela2.getObjectos()){
						valor.setValor(Double.valueOf(tokens[t]));
						t++;
						
					}
					listAUTOPanel.repaint();
				} catch (IOException e1) {
					
				e1.printStackTrace();
						
				}
			}
		});

		save = new JButton("Gravar configuração automática");

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {


				ArrayList <String> lista_para_escrever_no_ficheiro = new ArrayList<>();
				String s = new String();
				for(Table_object valor : modelo_tabela.getObjectos()){
					valores_pesos.add(valor.getValor());
					s = valor.getRegra() + ":" + valor.getValor();
					lista_para_escrever_no_ficheiro.add(s);

				}
				try {
					PrintWriter pw = new PrintWriter("rules.cf");
					for(String sss : lista_para_escrever_no_ficheiro){
						pw.println(sss);
					}
					pw.close();

				} catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println(lista_para_escrever_no_ficheiro);
				lista_para_escrever_no_ficheiro.clear();
			}
		});

		buttonAUTOPanel.add(evaluate);
		buttonAUTOPanel.add(save);
		buttonAUTOPanel.add(NEG2);
		buttonAUTOPanel.add(POS2);
		listAUTOPanel.add(buttonAUTOPanel, BorderLayout.EAST);


		frame.add(listAUTOPanel);

	}

	public void calcFP(){	//FALSOS POSITIVOS

		hash_ham = new HashMap<String, ArrayList>();

		try{
			File file_ham= new File("ham.log");
			Scanner scanner= new Scanner(file_ham);

			try{
				while(scanner.hasNextLine()){
					String line=scanner.nextLine();
					String[] partes = line.split("	");
					String email = partes[0];
					ArrayList <String> array_aux_ham = new ArrayList<>();

					//System.out.println("EMAILLL" + email);

					for(int i = 1; i<partes.length; i++){

						array_aux_ham.add(partes[i]);

					}
					hash_ham.put(partes[0], array_aux_ham);

				}
			}finally{
				scanner.close();
			}
		}catch(FileNotFoundException e1){
			e1.printStackTrace();
		}

		//est· proxima linha serve de teste do hashmap. È impresso na consola o valor da terceira key do hashmap
		//System.out.println(hash_ham.get("xval_initial/9/_ham_/00286.74f122eeb4cd901867d74f5676c85809"));

		//A partir daqui È para percorrer a tabela, ver as regras que coicidem e somar os respectivos pesos.

		int fp = 0;
		for (String key : hash_ham.keySet()) {   //iterar os emails

			double sumatorio_pesos_da_key = 0.0;
			ArrayList <String> array_aux_ham_2 = new ArrayList<>();
			array_aux_ham_2=hash_ham.get(key);
			//System.out.println(array_aux_ham_2);

			for(String regra_da_key : array_aux_ham_2){  //iterar as regras de cada email

				for(Table_object valor : modelo_tabela.getObjectos()){  //iterar a lista total de regras
					if(regra_da_key.equals(valor.getRegra())){
						sumatorio_pesos_da_key= sumatorio_pesos_da_key + valor.getValor();

					}

				}

			}

			if(sumatorio_pesos_da_key>5) {
				fp++;
			}

		}
		System.out.println("FALSOS POSITIVOS:  "+ fp);

		FP.setText(""+ fp);

	}
	
	public void calcFN(){	//FALSOS NEGATIVOS
		hash_ham_NEG = new HashMap<String, ArrayList>();

		try{
			File file_spam= new File("spam.log");
			Scanner s= new Scanner(file_spam);

			try{
				while(s.hasNextLine()){
					String line=s.nextLine();
					String[] tokens = line.split("	");
					String mail = tokens[0];
					ArrayList <String> array_aux_ham_NEG = new ArrayList<>();


					for(int i = 1; i<tokens.length; i++){

						array_aux_ham_NEG.add(tokens[i]);

					}
					hash_ham_NEG.put(tokens[0], array_aux_ham_NEG);

				}
			}finally{
				s.close();
			}
		}catch(FileNotFoundException e1){
			e1.printStackTrace();
		}

		//est· proxima linha serve de teste do hashmap. È impresso na consola o valor da terceira key do hashmap
		//System.out.println(hash_ham.get("xval_initial/9/_ham_/00286.74f122eeb4cd901867d74f5676c85809"));

		//A partir daqui È para percorrer a tabela, ver as regras que coicidem e somar os respectivos pesos.

		int fn = 0;
		for (String key : hash_ham_NEG.keySet()) {   //iterar os emails

			double sum_pesos_da_key = 0.0;
			ArrayList <String> array_aux_ham_NEG_2 = new ArrayList<>();
			array_aux_ham_NEG_2=hash_ham_NEG.get(key);

			for(String regra_da_key : array_aux_ham_NEG_2){  //iterar as regras de cada email

				for(Table_object valor : modelo_tabela.getObjectos()){  //iterar a lista total de regras
					if(regra_da_key.equals(valor.getRegra())){
						sum_pesos_da_key= sum_pesos_da_key + valor.getValor();
					}
				}
			}

			if(sum_pesos_da_key<5) {

				fn++;
			}

		}
		System.out.println("FALSOS NEGATIVOS:  "+ fn);
		
		FN.setText(""+ fn);
	}
	
	public static void main(String[] args) {
		Interface grid = new Interface();
		grid.open();
	}

}