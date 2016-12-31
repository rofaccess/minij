import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Scanner.*;
import Parser.sym;
import java_cup.runtime.Symbol;

public class TestScanner {
    public static void main(String [] args) {
        try {
            // create a scanner on the input file                    	     
        	scanner s = new scanner(file("MinTypeCheckingErrors.java"));
            printTockens(s);
        } catch (Exception e) {
            // yuck: some kind of error in the compiler implementation
            // that we're not expecting (a bug!)
            System.err.println("Unexpected internal compiler error: " + 
                        e.toString());
            // print out a stack dump
            e.printStackTrace();
        }
   }
   
   /* 
   ** Este método permite leer un archivo de la carpeta SamplePrograms
   */
   public static FileReader file(String fileName) throws FileNotFoundException{
	   	String pathAux = "src/TestScanner.java";
	   	File f = new File(pathAux);
   		String absolutePath = f.getAbsolutePath();
    	String newPath= absolutePath.replaceAll(pathAux, "SamplePrograms/"+fileName);
    	FileReader fr = new FileReader(newPath);
    	System.out.println("Compile Source File: "+newPath+"\n");
    	return fr;    	
   }
   
   /*
    * Imprime los tockens con indentación, mostrando un formato similar al archivo 
    * java original
    */
   public static void printTockens(scanner s) throws IOException{
	   int depth = 0;      
	   String indent = "";
	   Symbol t = s.next_token();
       while (t.sym != sym.EOF){           
           String symbolString = s.symbolToString(t);                               
    	   
           if(symbolString.equalsIgnoreCase("LKEY")){    		   
        	   depth++;
               indent = newIndent(depth);
        	   System.out.print(symbolString + "\n" + indent);   		   
               t = s.next_token(); 
           }else if(symbolString.equalsIgnoreCase("RKEY")){    			   
        	   depth--;
               indent = newIndent(depth);
               
               t = s.next_token();
    		   String symbolS = s.symbolToString(t);  
    		   
    		   if(symbolS.equalsIgnoreCase("RKEY")) System.out.print(indent + symbolString + "\n");
    		   else System.out.print(indent + symbolString + "\n" + indent);
    		   
        	   
        	  
    	   }else if(symbolString.equalsIgnoreCase("SEMICOLON")){
    		   t = s.next_token();
    		   String symbolS = s.symbolToString(t);  
    		   if(symbolS.equalsIgnoreCase("RKEY")) System.out.print(symbolString + "\n");
    		   else System.out.print(symbolString + "\n" + indent);
    		        		   
    	   }else{
    		   System.out.print(symbolString + " ");
    		   t = s.next_token(); 
    	   }    	      	   
           
           
       }
       System.out.print("\nLexical analysis completed"); 
   }
   
   /* Calcula una nueva indentanción dependiendo de la profundidad */
   public static String newIndent(int depth){
	   String indent = "";
	   for(int i = 0; i<depth; i++){
		   indent += "\t";
	   } 
	   return indent;
   }
}


