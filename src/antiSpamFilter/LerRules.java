package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LerRules {

	public static ArrayList<String> lerRules(){
		ArrayList<String> rules= new ArrayList<String>();
		
		try{
			File f= new File("rules.cf");
			Scanner s= new Scanner(f);
			
			try{
				while(s.hasNextLine()){
					String line=s.nextLine();
					rules.add(line);
				}
			}finally{
				s.close();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		System.out.println(rules);
		return rules;
	}
	
	public static void main(String[] args) {
		lerRules();
	}
}
