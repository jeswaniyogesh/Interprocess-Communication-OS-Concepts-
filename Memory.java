//package CPUMEM;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Memory {
	
	
	private static int[] data=new int[2000];
	private int programbound=0;

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		Memory mem=new Memory();
		mem.run(args[0]);

	}
	
	
	public void run(String args) throws FileNotFoundException{   //Give back values to CPU
		
		if(args.length()>0){
			loadMemory(args);
		}
		
		 Scanner sc = new Scanner(System.in);
		
		while (sc.hasNextLine()) {
			String str = sc.nextLine();
			int address;
			//its a write instruction
			if(str.isEmpty() || str.startsWith("#")) {
			continue;
			}
			if (str.matches("(\\d+) (\\d+).*")) {
			Scanner strSc = new Scanner(str);
			address = Integer.valueOf(strSc.next());
			write(address, strSc.next());
			//its a read instruction
			} else {
			address = Integer.valueOf(str);
			System.out.println(read(address));
			}
			// lineCount++;
			}
		
	}
	
	String line=null;
	public void loadMemory(String args) throws FileNotFoundException{   //Load Initial Memory 
		
		File f=new File(args);
		
		
		Scanner scan = new Scanner(f);
		int i = 0;
		
	
		
		while (scan.hasNextLine() ) {
			
			line=scan.nextLine();
			if(line.equals("")|| line.startsWith(" ")){
				
			}else{
				
				if(line.contains(".")){
					int newadd=Integer.valueOf(line.replaceFirst(".*?(\\d+).*", "$1"));
					i=newadd;
					/*if(scan.hasNextLine())
					line=scan.nextLine();
					data[newadd]=Integer.parseInt(line);*/
				}else{
		   
				write(i, line);
		        i++;
				}
	}
			
		}
		scan.close();
		programbound = i;
		//int k=0;
		/*while(!(k==i)){
			System.out.println(data[k]+" "+ k);
			k++;
		}*/
		}
		
		
		
	public int read(int address) {
		return data[address];
		}
	
	
		public void write(int address, String value) {
		data[address] = Integer.valueOf(value.replaceFirst(".*?(\\d+).*", "$1"));
		}
		
		
		
	}


