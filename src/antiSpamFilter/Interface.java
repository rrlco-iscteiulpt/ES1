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

/**
 * 
 * @author João Martins, João Teixeira, Mariana Barros, Rodrigo Cortesão.
 * 			65217			64750				65297			64822
 *
 */
public class Interface {

	private ArrayList <Table_object> rulesListManual = new ArrayList<Table_object>();
	private ArrayList <Table_object> rulesListAuto = new ArrayList<Table_object>();
	public JFrame frame = new JFrame("Filtro Anti-SPAM");
	public JFrame paths = new JFrame("Escolha de paths");
	public Table_Model tableModel_MAN  = new Table_Model();
	public Table_Model tableModel_AUTO = new Table_Model();
	private JTable tableManual = new JTable(tableModel_MAN);
	private JTable tableAuto =  new JTable(tableModel_AUTO);
	public JButton evaluateMAN = new JButton("Avaliar configuração Manual");
	public JButton saveMAN = new JButton("Gravar configuração Manual");
	public JButton evaluateAUTO = new JButton("Gerar configuração automática");
	public JButton saveAUTO = new JButton("Gravar configuração Automática");
	public JButton fechar = new JButton("Fechar janela");

	public JTextField spamField = new JTextField(10);
	public JTextField rulesField = new JTextField(10);
	public JTextField hamField = new JTextField(10);
	private String spamFile=null;
	private String hamFile=null;
	private String rulesFile=null; 

	private JTextField manualFN = new JTextField();
	private JTextField manualFP = new JTextField();
	private JTextField autoFN = new JTextField();
	private JTextField autoFP = new JTextField();

	private JPanel listAUTOPanel = new JPanel();

	private ArrayList<Double> weightValues = new ArrayList<>();

	private HashMap<String, ArrayList> hash_ham = new HashMap<String, ArrayList>();
	private HashMap<String, ArrayList> 	hash_ham_NEG = new HashMap<String, ArrayList>();

	public Interface() {

		addFrameContent();
	}

	/**
	 * Constrói a janela.
	 * Cria e adiciona à primeira Frame todos os seus elementos relacionados com a escolha dos ficheiros
	 */

	private void addFrameContent() {

		//------------------------------PATHS------------------------------------
		paths.setLocation(100,25);
		paths.setSize(300, 200);
		paths.setLayout(new GridLayout(4,1));
		paths.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		JPanel panelRules = new JPanel();


		fechar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hamFile = hamField.getText();
				spamFile = spamField.getText();
				rulesFile = rulesField.getText();
				paths.dispose();
				open2();
			}
		});



		JLabel rulesLabel = new JLabel("Ficheiro das regras");
		panelRules.add(rulesLabel, BorderLayout.WEST);
		panelRules.add(rulesField);

		paths.add(panelRules);

		JPanel panelSpam = new JPanel();
		JLabel spamLabel = new JLabel ("Ficheiro do Spam");
		panelSpam.add(spamLabel, BorderLayout.WEST);
		panelSpam.add(spamField, BorderLayout.CENTER);

		paths.add(panelSpam);

		JPanel panelHam = new JPanel();
		JLabel hamLabel = new JLabel ("Ficheiro do Ham");
		panelHam.add(hamLabel, BorderLayout.WEST);
		panelHam.add(hamField, BorderLayout.CENTER);

		paths.add(panelHam);
		paths.add(fechar);



	}


	/**
	 * Cria e adiciona à segunda Frame todos os seus elementos relacionados com as regras e pesos da parte Manual e automática
	 */
	private void addFrameContent2() {
		//-------------------------------------------------------------------------------------

		frame.setLocation(100,25);
		frame.setSize(900, 650);
		frame.setLayout(new GridLayout(2,2));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//------------------ MANUAL PANEL ----------------------
		//Lista
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(1, 2));

		for(Table_object obj : rulesListManual){
			tableModel_MAN.add_regras(obj);
		}

		JScrollPane scroll_tabela = new JScrollPane (tableManual);
		listPanel.add(scroll_tabela, BorderLayout.CENTER);

		//Text field
		JPanel panelNeg = new JPanel(new BorderLayout());
		JLabel manFN_label = new JLabel("Falsos Negativos");
		panelNeg.add(manFN_label, BorderLayout.WEST);
		panelNeg.add(manualFN, BorderLayout.CENTER);
		manualFN.setEditable(false);

		JPanel panelPos = new JPanel(new BorderLayout());
		JLabel manFP_label = new JLabel("Falsos Positivos");
		panelPos.add(manFP_label, BorderLayout.WEST);
		panelPos.add(manualFP, BorderLayout.CENTER);
		manualFP.setEditable(false);

		//Buttons
		JPanel buttonPanel = new JPanel(new GridLayout(2,2));

		evaluateMAN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluateMAN();
			}
		});

		saveMAN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveMAN();
			}
		});


		buttonPanel.add(evaluateMAN);
		buttonPanel.add(saveMAN);
		buttonPanel.add(panelNeg);
		buttonPanel.add(panelPos);

		listPanel.add(buttonPanel);


		frame.add(listPanel);

		//------------------ AUTO PANEL-------------------
		//Lista
		listAUTOPanel.setLayout(new GridLayout(1, 2));


		for(Table_object obj : rulesListAuto){
			tableModel_AUTO.add_regras(obj);
		}

		tableAuto = new JTable(tableModel_AUTO);
		JScrollPane scroll_tabela2 = new JScrollPane (tableAuto);
		listAUTOPanel.add(scroll_tabela2, BorderLayout.WEST);

		//Text field
		JPanel auto_neg_panel = new JPanel(new BorderLayout());
		JLabel autoFN_label = new JLabel("Falsos Negativos");
		auto_neg_panel.add(autoFN_label, BorderLayout.WEST);
		auto_neg_panel.add(autoFN, BorderLayout.CENTER);
		autoFN.setEditable(false);

		JPanel auto_pos_panel = new JPanel(new BorderLayout());
		JLabel autoFP_label = new JLabel("Falsos Positivos");
		auto_pos_panel.add(autoFP_label, BorderLayout.WEST);
		auto_pos_panel.add(autoFP, BorderLayout.CENTER);
		autoFP.setEditable(false);

		//Buttons
		JPanel buttonAUTOPanel = new JPanel(new GridLayout(2,2));

		evaluateAUTO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluateAUTO();
				String[] params = new String[2];
				String[] envp = new String[1];
				
				params[0] = "C:\\Program Files\\R\\R-3.4.3\\bin\\x64\\Rscript.exe";
				params[1] = "C:\\Users\\Mariana Barros\\git\\ES1-2017-METIA1-52\\experimentBaseDirectory\\AntiSpamStudy\\R";
				envp[0] = "Path = C:\\Program Files\\R\\R-3.4.3\\bin\\x64";
				
				try {
					Process p = Runtime.getRuntime().exec(params,envp, new File("C:\\Users\\Mariana Barros\\git\\ES1-2017-METIA1-52\\experimentBaseDirectory\\AntiSpamStudy\\R"));
				} catch (IOException e1) {
				System.out.println("Erro a gerar os gráficos R");
				}
				
				
				String[] paramsLatex = new String[2];
				String[] envpLatex = new String[1];
				
				paramsLatex[0] = "C:\\Program Files\\MiKTeX 2.9\\miktex\\bin\\x64\\pdflatex.exe";
				paramsLatex[1] = "C:\\Users\\Mariana Barros\\git\\ES1-2017-METIA1-52\\experimentBaseDirectory\\AntiSpamStudy\\latex\\AntiSpamStudy.tex";
				envpLatex[0] = "Path = C:\\Program Files\\MiKTeX 2.9\\miktex\\bin\\x64";
				
				try {
					Process p = Runtime.getRuntime().exec(paramsLatex,envpLatex, new File("C:\\Users\\Mariana Barros\\git\\ES1-2017-METIA1-52\\experimentBaseDirectory\\AntiSpamStudy\\latex"));
				} catch (IOException e1) {
				System.out.println("Erro a gerar os gráficos latex");
				}
			}
		});

		saveAUTO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAUTO();
			}
		});

		buttonAUTOPanel.add(evaluateAUTO);
		buttonAUTOPanel.add(saveAUTO);
		buttonAUTOPanel.add(auto_neg_panel);
		buttonAUTOPanel.add(auto_pos_panel);
		listAUTOPanel.add(buttonAUTOPanel, BorderLayout.EAST);


		frame.add(listAUTOPanel);

	}

	/**
	 * Função que calcula o número de Falsos Positivos de um vector de pesos.
	 * 
	 * @param tableToRead Tabela Ã  qual se irÃ¡ buscar os pesos respetivos de cada regra
	 * @param fieldToWrite  Caixa de Texto onde serÃ¡ inserido o valor calculado dos Falsos Positivos
	 * @param file Ficheiro a ler
	 * @return falsos positivos calculados
	 */

	public int calcFP(Table_Model tableToRead, JTextField fieldToWrite,String file){	//FALSOS POSITIVOS

		try{
			File file_ham= new File(file);
			Scanner scanner= new Scanner(file_ham);
			while(scanner.hasNextLine()){
				String line=scanner.nextLine();
				String[] tokens = line.split("	");
				String email = tokens[0];
				ArrayList <String> array_aux_ham = new ArrayList<>();

				//System.out.println("EMAILLL" + email);

				for(int i = 1; i<tokens.length; i++){

					array_aux_ham.add(tokens[i]);

				}
				hash_ham.put(tokens[0], array_aux_ham);

			}
			scanner.close();
		}catch(FileNotFoundException e1){
			e1.printStackTrace();
		}

		//estÂ¬Â¨â€šÃ Ã« proxima linha serve de teste do hashmap. â€šÃ Ã¶âˆšâ€  impresso na consola o valor da terceira key do hashmap
		//System.out.println(hash_ham.get("xval_initial/9/_ham_/00286.74f122eeb4cd901867d74f5676c85809"));

		//A partir daqui â€šÃ Ã¶âˆšâ€  para percorrer a tabela, ver as regras que coicidem e somar os respectivos pesos.

		int fp = 0;
		for (String key : hash_ham.keySet()) {   //iterar os emails

			double keyWeightSum = 0.0;
			ArrayList <String> array_aux_ham_2 = new ArrayList<>();
			array_aux_ham_2=hash_ham.get(key);
			//System.out.println(array_aux_ham_2);

			for(String keyRule : array_aux_ham_2){  //iterar as regras de cada email

				for(Table_object value : tableToRead.getObjectos()){  //iterar a lista total de regras
					if(keyRule.equals(value.getRule())){
						keyWeightSum= keyWeightSum + value.getValue();

					}

				}

			}

			if(keyWeightSum>5) {
				fp++;
			}

		}
		System.out.println("FALSOS POSITIVOS:  "+ fp);

		fieldToWrite.setText(""+ fp);
		return fp;
	}

	/**
	 * Função que calcula o número de Falsos Negativos de um vector de pesos.
	 * 
	 * @param tableToRead Tabela Ã  qual se irÃ¡ buscar os pesos respetivos de cada regra
	 * @param fieldToWrite  Caixa de Texto onde serÂ· inserido o valor calculado dos Falsos negativos
	 * @param file Ficheiro a ler
	 * @return falsos negativos calculados
	 */

	public int calcFN(Table_Model tableToRead, JTextField fieldToWrite,String file){	//FALSOS NEGATIVOS

		try{
			File file_spam= new File(file);
			Scanner s= new Scanner(file_spam);


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

			s.close();

		}catch(FileNotFoundException e1){
			e1.printStackTrace();
		}


		int fn = 0;
		for (String key : hash_ham_NEG.keySet()) {   //iterar os emails

			double keyWeightSum = 0.0;
			ArrayList <String> array_aux_ham_NEG_2 = new ArrayList<>();
			array_aux_ham_NEG_2=hash_ham_NEG.get(key);

			for(String keyRule : array_aux_ham_NEG_2){  //iterar as regras de cada email

				for(Table_object valor : tableToRead.getObjectos()){  //iterar a lista total de regras
					if(keyRule.equals(valor.getRule())){
						keyWeightSum= keyWeightSum + valor.getValue();
					}
				}
			}

			if(keyWeightSum<5) {

				fn++;
			}

		}
		System.out.println("FALSOS NEGATIVOS:  "+ fn);

		fieldToWrite.setText(""+ fn);
		return fn;
	}

	/**
	 * Função que lê o AntiSpamFilterProblem.NGAII.rs
	 * @return Retorna as palavras separadas por espaços
	 * @throws FileNotFoundException
	 */

	public String[] lerRS() throws FileNotFoundException{

		File f= new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
		Scanner s= new Scanner(f);
		String line ="";
		while(s.hasNextLine()){
			line+=s.nextLine();
		}
		return line.split(" ");
	}



	/**
	 * Obtenção dos falsos positivos e falsos negativos da parte automÂ·tica para serem adicionados Ã  frame, calculados atravÃ©s das funÃ§Ãµes calcFP e calcFN
	 */
	public void writeFPFN(){

		try{

			Table_Model tableAux= new Table_Model();
			for(Table_object obj : rulesListAuto){
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

				int fp=calcFP(tableAux, autoFP,hamFile);
				int fn=calcFN(tableAux, autoFN,spamFile);


				pw.println(fp+" "+fn);
				System.out.println( "teste" + fp+" "+fn);

			}
			pw.close();
		}catch(FileNotFoundException e){

		}

	}

	/**
	 * InicializaÃ§Ã£o da primeira janela
	 */

	public void open() {
		paths.setVisible(true);
	}

	/**
	 * InicializaÃ§Ã£o da segunda janela
	 */
	public void open2(){
		rulesListManual = ReadRules.readRules(rulesFile);
		rulesListAuto = ReadRules.readRules(rulesFile);

		addFrameContent2();
		frame.setVisible(true);
	}

	/**
	 * Ler o ficheiro AntiSpamFilterProblem.NSGAII.rf
	 * @return Retorna o indicador do menor Falso Positivo
	 */
	public static int lerAntiSpamRF(){
		int index=0;
		int lowestvalue=600;  //alterei de -5.0 para 0 :RC

		try{
			File f= new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rf");
			Scanner s= new Scanner(f);
			int i=0;


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

			s.close();

		}catch(FileNotFoundException e){

		}
		return index;
	}

	/**
	 * Ler o ficheiro AntiSpamFilterProblem.NSGAII.rs
	 * @return a linha do ficheiro que corresponde ao indicador retornado pela função lerAntiSpamRF
	 */
	public String[] lerAntimSpamRS(){
		String[] tokens=null;

		try{
			File f= new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
			Scanner s= new Scanner(f);
			String line=null;

			for(int i=0; i<lerAntiSpamRF(); i++){
				line=s.nextLine();
			} 
			System.out.println(line);
			tokens=line.split(" ");

			s.close();

		}catch(FileNotFoundException e){

		}
		return tokens;
	}

	/**
	 * Avalia a configuração da parte manual
	 */
	public void evaluateMAN(){
		calcFP(tableModel_MAN, manualFP,hamFile);
		calcFN(tableModel_MAN, manualFN,spamFile);
	}

	/**
	 * Grava a configuração da parte automática escrevendo no ficheiro rules.cf
	 */
	public void saveAUTO(){
		ArrayList <String> listToWriteOnFile = new ArrayList<>();
		String s = new String();
		for(Table_object valor : tableModel_AUTO.getObjectos()){
			weightValues.add(valor.getValue());
			s = valor.getRule() + ":" + valor.getValue();
			listToWriteOnFile.add(s);
		}
		try {
			PrintWriter pw = new PrintWriter(rulesFile);
			for(String objects : listToWriteOnFile){
				pw.println(objects);
			}
			pw.close();
		} catch (Exception e) {
		}
		System.out.println(listToWriteOnFile);
		listToWriteOnFile.clear();
	}

	/**
	 * Avalia a configuração da parte automática
	 */
	public void evaluateAUTO(){
		try {
			AntiSpamFilterAutomaticConfiguration.main(null);
			writeFPFN();
			String[] tokens=lerAntimSpamRS();
			int t=0;
			for(Table_object valor : tableModel_AUTO.getObjectos()){
				valor.setValor(Double.valueOf(tokens[t]));
				t++;
			} 
			listAUTOPanel.repaint();
		} catch (IOException e1) {
		}
		calcFN(tableModel_AUTO, autoFN,spamFile);
		calcFP(tableModel_AUTO, autoFP,hamFile);
	}

	/**
	 * Grava a configuração da parte manual escrevendo no ficheiro rules.cf
	 */
	public void saveMAN(){
		ArrayList <String> listToWriteOnFile = new ArrayList<>();
		String s = new String();
		for(Table_object valor : tableModel_MAN.getObjectos()){
			weightValues.add(valor.getValue());
			s = valor.getRule() + ":" + valor.getValue();
			listToWriteOnFile.add(s);
		}
		try {
			PrintWriter pw = new PrintWriter(rulesFile);
			for(String sss : listToWriteOnFile){
				pw.println(sss);
			}
			pw.close();
		} catch (Exception e) {
		}
		System.out.println(listToWriteOnFile);
		listToWriteOnFile.clear();
	}

	public static void main(String[] args) {
		Interface grid = new Interface();
		grid.open();
	}
}