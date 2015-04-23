//package CPUMEM;
import java.io.*;
import java.util.Scanner;


public class CPU {
	
	
	static int PC=0;
	static int SP=1000;
	static int IR=0;
	static int AC=0;
	static int X=0;
	static int Y=0;
	private final int instructionswithAdd[]={1,2,3,4,5,7,9,20,23};
	boolean USERMODE=true;
	boolean exit=false;
	final int mem_bound=999;
	InputStream is;
	OutputStream os;
	int k=0;
	PrintWriter pw;
	 Process proc;
	
	
    public CPU(String args) throws IOException{   //Initialize Memory 
	
	Runtime rt = Runtime.getRuntime();
	 proc = rt.exec("java Memory"+" "+args);
	
	  is = proc.getInputStream();
	  os = proc.getOutputStream();
	  
	   pw=new PrintWriter(os);
	   int k=0;
	 //  while(k!=235){
	 //  int k=read(3);
	  // if(k==4)
		//   System.out.println(read(k) +" "+k);
	 //  else
		//   System.out.println("no");
		 //  k++;
	 //  }
	  // write(1000,30);
	 //  System.out.println(read(1000));
	   }
	   /*k=1500;
	   while(k!=1530){
		   System.out.println(read(k)+" "+k);
		   k++;
	   }
	   k=1000;
	   while(k!=1040){
		   System.out.println(read(k)+" "+k);
		   k++;
	   }*/
	   
	 
    
    

    public void execute(String args){
    	
    	int count=0;
    	while(true){    //Check for the End Statement
    		
    		if(USERMODE){
    			count++;
    		}
    		
    		Startloading(args);
    		
    		
    		
            if(count==Integer.parseInt(args)){            //Timer will execute from here
    			
    			count=0;
    			PC--;
    		Startprocessing(29,1);
    			
    		}
    		
    		
    		
    		if(exit)
    			break;
    		
    		
    		
    	}
    	
    	
    }
	
    public void Startloading(String args){    //Fetch values from Memory and Implement
    	
    	//int temp=Integer.parseInt(args);
    	
    	boolean hasaddr=false;
    	 IR=read(PC);
    	// System.out.println(PC+"hi");
    	// System.out.println("IR"+" "+IR);
    	 
    	 for(int i=0;i<instructionswithAdd.length;i++){
    		 if(IR==instructionswithAdd[i]){
    			// System.out.println("true value");
    			 hasaddr=true;
    		 break;
    		 }
    	 }
    	 
         if(hasaddr){
    		 PC++;
    	 Startprocessing(IR,read(PC));
    	 }else
    		 Startprocessing(IR,0);
    	 
        /* if(count==temp){
        	// System.out.println(args);
     		Startprocessing(29,1);
     		count=0;
     		
     		System.out.println(count+" "+"count");
     	}*/
    	// System.out.println(ir);
    	 PC++;
    	
    	
    	 }
    
    private void isValidAdd(int add){    //Check if address is valid
    	
    	if(USERMODE){
    		if(add<0|| add>mem_bound ){
    			System.out.println("Invalid Memory Address"+" "+add);
    			Startprocessing(50,0);
    		}
    	}
    }
    
    
    public void Startprocessing(int instruction,int nextval){     //Check for different opcodes
    	
    	
    	switch(instruction){
    	        
    	
    	        //Load the Value in AC
    	case 1: 
    		   AC=nextval;
    		    // System.out.println("case1"+" "+AC);
    	          break;
    	        
    	        
    	        //Load the Value at the Address into the Ac
    	case 2: isValidAdd(nextval); 
    		   AC=read(nextval);
    	       // System.out.println("case2");
    	        break;
    		
    	        //Load the value from Add found in add into ac
    	case 3: isValidAdd(nextval); 
    		    int temp=read(nextval);
    		     AC=read(temp);
    	        // System.out.println("case3");
    	         break;
    		
    	         
    	         //LoadIdxXaddr:Load value at add+x into ac.
    	case 4:  isValidAdd(nextval); 
    		     int tempadd=nextval+X;
    		      AC=read(tempadd);
    	        // System.out.println("case4"+ " "+AC);
    	         //System.out.println(AC+"news");
    	         break;
    	         
    		    //LoadIdxYaddr:Load value at add+y into ac.
    	case 5: isValidAdd(nextval);   
    		   AC=read(nextval+Y);
    	         //System.out.println("case5");
    	         break;
    		  
    		
    		   //LoadSpx:Load from sp+x into ac.
    	case 6: isValidAdd(SP+X); 
    		   AC=read(SP+X);
    	        //System.out.println("case6"+" "+AC);
    	        break;
    		
    		
    		  //Storeadd: Store the value in AC into add:
    	case 7: isValidAdd(nextval); 
    		    write(nextval,AC);
    	        // System.out.println("case7");
    	         break;
    		
    		//Get: Get a random int from 1:100 into AC.
    	case 8: int val=(int) (Math.random()%100+1);
    	          AC=val;
    	          //System.out.println("case8");
    	         break;
    		
    		
    		//PutPort: if 1, writes ac as int, if 2, writes ac as char.
    	case 9: 
    		if(nextval==1){
    		     System.out.print(AC);
    		    // System.out.println("case9");
    	       }else if(nextval==2){
    		    System.out.print((char)AC);
    		   // System.out.println("case9");
    	        }
    	        break;
    		
    		
    		//AddX:Add value in X to ac.
    	case 10:AC+=X;
    	      //  System.out.println("case10");
    	        break;
    		
    		
    		//AddY:Add the value in Y to ac.
    	case 11:AC+=Y;
    	        //System.out.println("case11");
    	        break;
    		
    		
    		//SubX:Sub value in X from ac.
    	case 12:AC-=X;
    	       // System.out.println("case12");
    	        break;
    		
    		
    		//SubY: Sub value in Y from ac.
    	case 13:AC-=Y;
    	        //System.out.println("case13");
    	        break;
    		
    		
    		//CopyToX:Copy the value in AC to X. 
    	case 14: X=AC;
    	        // System.out.println("case14"+" "+X);
    	         break;
    	
    		
    		
    		//CopyfromX:Copy the value in X to AC.
    	case 15:AC=X;
    	        //System.out.println("case15"+" "+AC);
    	        break;
    		
    		
    		//CopyToY: Copy value in AC to Y.
    	case 16:Y=AC;
    	       // System.out.println("case16");
    	        break;
    		
    		
    		//CopyfromY:Copy the value in Y to ac.
    	case 17:AC=Y;
    	      //  System.out.println("case17");
    	        break;
    		
    		
    		//CopytoSP:Copy the value in AC to SP:
    	case 18:SP=AC;
    	        //System.out.println("case18");
    	        break;
    		
    		
    		//CopyfromSP:Copy value in SP to AC:
    	case 19:AC=SP;
    	       // System.out.println("case19");
    	        break;
    		
    		
    		//Jumpaddr: Jump to the addr
    	case 20:isValidAdd(nextval); 
    		    PC=nextval-1;
    	        
    		      
    		  //System.out.println("case20"+ PC);
    	       // System.out.println(PC+"new value");
    	      //  System.out.println(read(PC));
    		    break;
    		
    		//Jumpifequaladdr:jump to add only if value in ac is 0.
    	case 21: isValidAdd(nextval); 
    		       if(AC==0){
    		        nextval=++PC;
    		        int t2=read(nextval);
    		       // System.out.println("next val"+ nextval);
    		        PC=t2-1;
    		       // System.out.println("case21"+ PC);
    		        break;
    	       // System.out.println("acc is zero");
    	}else{
    		PC++;
    		break;
    	}
    	       
    		
    		
    		//Jumpifnotequaladdr: Jump to addr only if value in acc is not 0.
    	case 22:  isValidAdd(nextval); 
    		  if(AC!=0){
    		//System.out.println("case22");
    		nextval=++PC;
    		int t1=read(nextval);
    		PC=t1-1;
    		//System.out.println(PC+"new val");
    	}else PC++;
    	        break;
    		
    		
    		//call addr: Push return addr on to stack , jump to addr.
    	case 23:isValidAdd(nextval); 
    		    SP--;
    	        write(SP,PC);
    	       // SP--;
    	        PC=nextval-1;
    	       // System.out.println("case23"+" "+PC);
    	         break;
    		
    		
    		//Ret:pop return addr from stack,jump to addr
    	case 24://SP++;
    		int temp1=read(SP);
    	//System.out.println("case24"+" "+temp1);
                PC=temp1;
                SP++;
                break;
    		
    		//IncX: Increment the value in X:
    	case 25:X++;
    	//System.out.println("case25"+X);
    	       // System.out.println(X);
    	        break;
    		
    		
    		//DecX:Decrement value in X.
    	case 26:X--;
    	//System.out.println("case26"+" "+X);
    	       break;
    		
    		
    		//Push: Push AC onto stack.
    	case 27:SP--;
    	        write(SP,AC);
    	        //SP--;
    	       // System.out.println("case27"+ " "+AC);
    	        break;
    		
    		
    		//Pop:Pop from stack into AC
    	case 28://SP++;
    		AC=read(SP);
    	       SP++;
    	       // System.out.println("case28");
    	        break;
    		
    		
    		//Int:Set systemMode,switch stack,push sp and pc,set new Sp and pc.
    	case 29:
    		
    		    if(!USERMODE){
    		    	Startprocessing(50,0);
    		    }
    		//System.out.println("case29");
    		     int userSP=SP;
    		     USERMODE=false;
    		     SP=1999;
    		     write(SP,userSP);
    		     SP--;
    		     write(SP,PC);
    		     SP--;
    		     write(SP,AC);
    		     SP--;
    		     write(SP,IR);
    		     SP--;
    		     write(SP,X);
    		     SP--;
    		     write(SP,Y);
    		     if(nextval==0){
    		    	 PC=1499;
    		     }else if(nextval==1){
    		    	 
    		    	 PC=1000;
    		    	// System.out.println("1 loop"+ PC);
    		     }
    		     
    		break;
    		
    		//Iret:Restore Registers,set user mode
    	case 30:
    		//System.out.println("case30");
    		Y=read(SP);
    		SP++;
    		X=read(SP);
    		SP++;
    		IR=read(SP);
    		SP++;
    		AC=read(SP);
    		SP++;
    		    int t=read(SP);
    		    PC=t;
    		  //  System.out.println("PC is +"+" "+PC);
    		    SP++;
    		    SP=read(SP);
    		   // System.out.println("Sp is +"+" "+SP);
    		    //SP++;
    		    USERMODE=true;
    		
    		break;
    	case 50: exit=true;
    	         break;
    	         
    	default: System.out.println("Instruction passed"+ " "+"PC"+PC+"IR"+IR);
    	         break;
    	        	 
    	}
    }
    
    
    
    
    String line=null;
    public int read(int address){   //Read from Memory
    	
    	PrintWriter pw=new PrintWriter(os);
  		 pw.write(String.format("%d\n", address));
  		 pw.flush();
  		 Scanner sc = new Scanner(is);
  		 
  		   line = sc.nextLine();
  		return Integer.parseInt(line);
  		 
  		  }
    
    public void write(int address,int val){    //Write to memory using Outputstream
    	PrintWriter pw=new PrintWriter(os);
    	pw.write(String.format("%d %d\n", address, val));
    	pw.flush();
    }
    
    public void test(){
    	
    	pw.write(String.format("%d %d\n", 20,10));
    	pw.flush();
    	
    	System.out.println(read(0)+ "manually stored");
    }
    
    
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		 
		CPU c=new CPU(args[0]);
		c.execute(args[1]);
		//c.test();
		
		
 }

}
