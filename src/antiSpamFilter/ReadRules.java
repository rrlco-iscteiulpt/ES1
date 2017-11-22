package antiSpamFilter;
	
	import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

	public class ReadRules {
		
		ArrayList <String> rules;
		
		public static ArrayList<Table_object> lerRules(){
			ArrayList<Table_object> rules= new ArrayList<Table_object>();
			
			try{
				File f= new File("rules.cf");
				Scanner s= new Scanner(f);
				
				try{
					while(s.hasNextLine()){
						String line=s.nextLine();
						String[] tokens = line.split("	");
						if(line.contains(":")){
							line=line.split(":")[0];
						}
					
						rules.add(new Table_object(line));
					}
				}finally{
					s.close();
				}
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}
			//System.out.println(rules);
			return rules;
		}
		
		
		public ArrayList<String> getRules() {
			return rules;
		}


		public void setRules(ArrayList<String> rules) {
			this.rules = rules;
		}


		public static void main(String[] args) {
			lerRules();
		}
	}


