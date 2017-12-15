package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author João Martins, João Teixeira, Mariana Barros, Rodrigo Cortesão.
 * 			65217			64750				65297			64822
 *
 */
public class ReadRules {

	ArrayList <String> rules;

	/**
	 * Função que lê as Regras de Spam
	 * @param file Ficheiro a ler
	 * @return a lista de regras
	 */
	public static ArrayList<Table_object> readRules(String file){
		ArrayList<Table_object> rules= new ArrayList<Table_object>();
		try{
			File f= new File(file);
			Scanner s= new Scanner(f);
			while(s.hasNextLine()){
				String line=s.nextLine();
				String[] tokens = line.split("	");
				if(line.contains(":")){
					line=line.split(":")[0];
				}
				rules.add(new Table_object(line));
			}
			s.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return rules;
	}

	/**
	 * Função para obter uma lista de regras
	 * @return lista de regras
	 */
	public ArrayList<String> getRules() {
		return rules;
	}

	/**
	 * Função para pôr uma lista de regras
	 * @param rules regras de uma lista
	 */
	public void setRules(ArrayList<String> rules) {
		this.rules = rules;
	}

}


