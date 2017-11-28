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
	private Table_Model modelo_tabela_MAN;
	private Table_Model modelo_tabela_AUTO;
	private JButton evaluateMAN;
	private JButton saveMAN;
	private JButton evaluateAUTO;
	private JButton saveAUTO;

	private JTextField FN_MAN;
	private JTextField FP_MAN;
	private JTextField FN_AUTO;
	private JTextField FP_AUTO;


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

		modelo_tabela_MAN = new Table_Model();

		for(Table_object obj : lista_regras){
			modelo_tabela_MAN.add_regras(obj);
		}


		tabela = new JTable(modelo_tabela_MAN);
		JScrollPane scroll_tabela = new JScrollPane (tabela);
		listPanel.add(scroll_tabela, BorderLayout.CENTER);

		//Text field
		JPanel NEG = new JPanel(new BorderLayout());
		JLabel FN_1 = new JLabel("Falsos Negativos");
		FN_MAN = new JTextField();
		NEG.add(FN_1, BorderLayout.WEST);
		NEG.add(FN_MAN, BorderLayout.CENTER);
		FN_MAN.setEditable(false);

		JPanel POS = new JPanel(new BorderLayout());
		JLabel FP = new JLabel("Falsos Positivos");
		FP_MAN = new JTextField();
		POS.add(FP, BorderLayout.WEST);
		POS.add(FP_MAN, BorderLayout.CENTER);
		FP_MAN.setEditable(false);

		//Buttons
		JPanel buttonPanel = new JPanel(new GridLayout(2,2));
		evaluateMAN = new JButton("Avaliar configuração Manual");

		evaluateMAN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcFP(modelo_tabela_MAN, FP_MAN);
				calcFN(modelo_tabela_MAN, FN_MAN);
			}
		});

		saveMAN = new JButton("Gravar configuração Manual");

		saveMAN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {



				ArrayList <String> lista_para_escrever_no_ficheiro = new ArrayList<>();
				String s = new String();
				for(Table_object valor : modelo_tabela_MAN.getObjectos()){
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


		buttonPanel.add(evaluateMAN);
		buttonPanel.add(saveMAN);
		buttonPanel.add(NEG);
		buttonPanel.add(POS);

		listPanel.add(buttonPanel);


		frame.add(listPanel);

		//------------------ AUTO PANEL-------------------
		//Lista
		JPanel listAUTOPanel = new JPanel();
		listAUTOPanel.setLayout(new GridLayout(1, 2));

		modelo_tabela_AUTO = new Table_Model();

		for(Table_object obj : lista_regras){
			modelo_tabela_AUTO.add_regras(obj);
		}

		tabela = new JTable(modelo_tabela_AUTO);
		JScrollPane scroll_tabela2 = new JScrollPane (tabela);
		listAUTOPanel.add(scroll_tabela2, BorderLayout.WEST);

		//Text field
		JPanel NEG2 = new JPanel(new BorderLayout());
		JLabel FN_2 = new JLabel("Falsos Negativos");
		FN_AUTO = new JTextField();
		NEG2.add(FN_2, BorderLayout.WEST);
		NEG2.add(FN_AUTO, BorderLayout.CENTER);
		FN_AUTO.setEditable(false);

		JPanel POS2 = new JPanel(new BorderLayout());
		JLabel FP_2 = new JLabel("Falsos Positivos");
		FP_AUTO = new JTextField();
		POS2.add(FP_2, BorderLayout.WEST);
		POS2.add(FP_AUTO, BorderLayout.CENTER);
		FP_AUTO.setEditable(false);

		//Buttons
		JPanel buttonAUTOPanel = new JPanel(new GridLayout(2,2));
		evaluateAUTO = new JButton("Gerar configuração automática");

		evaluateAUTO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					AntiSpamFilterAutomaticConfiguration.main(null);
					writeFPFN();
					String[] tokens=lerAntimSpamRS();
					int t=0;
					for(Table_object valor : modelo_tabela_AUTO.getObjectos()){
						valor.setValor(Double.valueOf(tokens[t]));
						t++;

					}
					listAUTOPanel.repaint();
				} catch (IOException e1) {

					e1.printStackTrace();

				}

				calcFN(modelo_tabela_AUTO, FN_AUTO);
				calcFP(modelo_tabela_AUTO, FP_AUTO);
				
			}
		});

		saveAUTO = new JButton("Gravar configura��o Autom�tica");

		saveAUTO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {


				ArrayList <String> lista_para_escrever_no_ficheiro = new ArrayList<>();
				String s = new String();
				for(Table_object valor : modelo_tabela_AUTO.getObjectos()){
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

		buttonAUTOPanel.add(evaluateAUTO);
		buttonAUTOPanel.add(saveAUTO);
		buttonAUTOPanel.add(NEG2);
		buttonAUTOPanel.add(POS2);
		listAUTOPanel.add(buttonAUTOPanel, BorderLayout.EAST);


		frame.add(listAUTOPanel);

	}

	public int calcFP(Table_Model tabela_a_ler, JTextField caixa_de_texto2){	//FALSOS POSITIVOS

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

		//est¬∑ proxima linha serve de teste do hashmap. √à impresso na consola o valor da terceira key do hashmap
		//System.out.println(hash_ham.get("xval_initial/9/_ham_/00286.74f122eeb4cd901867d74f5676c85809"));

		//A partir daqui √à para percorrer a tabela, ver as regras que coicidem e somar os respectivos pesos.

		int fp = 0;
		for (String key : hash_ham.keySet()) {   //iterar os emails

			double sumatorio_pesos_da_key = 0.0;
			ArrayList <String> array_aux_ham_2 = new ArrayList<>();
			array_aux_ham_2=hash_ham.get(key);
			//System.out.println(array_aux_ham_2);

			for(String regra_da_key : array_aux_ham_2){  //iterar as regras de cada email

				for(Table_object valor : tabela_a_ler.getObjectos()){  //iterar a lista total de regras
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

		caixa_de_texto2.setText(""+ fp);
		return fp;
	}

	public int calcFN(Table_Model tabela_a_ler, JTextField caixa_de_texto){	//FALSOS NEGATIVOS
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

		//est¬∑ proxima linha serve de teste do hashmap. √à impresso na consola o valor da terceira key do hashmap
		//System.out.println(hash_ham.get("xval_initial/9/_ham_/00286.74f122eeb4cd901867d74f5676c85809"));

		//A partir daqui √à para percorrer a tabela, ver as regras que coicidem e somar os respectivos pesos.

		int fn = 0;
		for (String key : hash_ham_NEG.keySet()) {   //iterar os emails

			double sum_pesos_da_key = 0.0;
			ArrayList <String> array_aux_ham_NEG_2 = new ArrayList<>();
			array_aux_ham_NEG_2=hash_ham_NEG.get(key);

			for(String regra_da_key : array_aux_ham_NEG_2){  //iterar as regras de cada email

				for(Table_object valor : tabela_a_ler.getObjectos()){  //iterar a lista total de regras
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

		caixa_de_texto.setText(""+ fn);
		return fn;
	}

	public String[] lerRS() throws FileNotFoundException{

			File f= new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
			Scanner s= new Scanner(f);
			String line ="";
       		while(s.hasNextLine()){
			line+=s.nextLine();
       		}
       		return line.split(" ");
	}
	
	public void writeFPFN(){
		
		try{

			Table_Model tableAux= new Table_Model();
			for(Table_object obj : lista_regras){
				tableAux.add_regras(obj);
			}

			String[] tokens=lerRS();
			PrintWriter pw = new PrintWriter("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rf");

			for(int i=0;i<tokens.length;i=i+335){
				int t=i;

				for(Table_object valor : tableAux.getObjectos()){
					valor.setValor(Double.valueOf(tokens[t]));
					t++;	
				}

				int fp=calcFP(tableAux, FP_AUTO);
				int fn=calcFN(tableAux, FN_AUTO);


				pw.println(fp+" "+fn);
				System.out.println( "teste" + fp+" "+fn);

			}
			pw.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
			
	}

	public void open() {
		frame.setVisible(true);
	}

	public static int lerAntiSpamRF(){
		int index=0;
		int lowestvalue=600;  //alterei de -5.0 para 0 :RC

		try{
			File f= new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rf");
			Scanner s= new Scanner(f);
			int i=0;

			try{
				while(s.hasNextLine()){
					String line=s.nextLine();
					String[] tokens=line.split(" ");
					String FPstring= tokens[0];
					String FNstring= tokens[1];
					int FP= Integer.parseInt(FPstring);
					int FN= Integer.parseInt(FNstring);
					if(lowestvalue>FP){
						lowestvalue=FP;
						index=i+1;
					}
					if(lowestvalue<FP){
						lowestvalue=lowestvalue;
					}
					i++;
				}
			}finally{
				s.close();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return index;
	}


	public String[] lerAntimSpamRS(){
		String[] tokens=null;
		
		try{
			File f= new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
			Scanner s= new Scanner(f);
			String line=null;

			try{

				for(int i=0; i<lerAntiSpamRF(); i++){
					line=s.nextLine();
				}

				System.out.println(line);
				tokens=line.split(" ");

			}finally{
				s.close();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return tokens;

	}

	public static void main(String[] args) {
		Interface grid = new Interface();
		grid.open();
	}

}