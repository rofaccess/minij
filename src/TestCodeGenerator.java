import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java_cup.runtime.Symbol;
import AST.Program;
import AST.Visitor.JasminCodeGeneratorVisitor;
import AST.Visitor.SemanticAnalyzerWithSymbolTreeVisitor;
import AST.Visitor.SymbolTreeVisitor;
import Parser.parser;
import Scanner.scanner;
import SymbolTree.SymbolTree;


public class TestCodeGenerator {
	public static void main(String [] args) {
		try {
			// create a scanner on the input file
			scanner s = new scanner(TestScanner.file("Jasmin.java"));
			parser p = new parser(s);
			Symbol root;
			// replace p.parse() with p.debug_parse() in next line to see trace of
			// parser shift/reduce actions during parse
			
			root = p.parse();
			Program program = (Program) root.value;
			SymbolTreeVisitor stv = new SymbolTreeVisitor();
			program.accept(stv);			
			
			SymbolTree st=stv.getSymbolTree();	      
			st.printSymbolTree();
			
	        SemanticAnalyzerWithSymbolTreeVisitor sav = new SemanticAnalyzerWithSymbolTreeVisitor(st);
	        program.accept(sav);
	        
	        System.out.println();
	        JasminCodeGeneratorVisitor jcgv = new JasminCodeGeneratorVisitor(st);
	        program.accept(jcgv);
	        /*  */		
	        System.out.println("### Jasmin Code Generated ###\n");
	        
	        ArrayList<String> jasminFileNames = jcgv.getJasminCodeFileNames();	       
	        ArrayList<String> jasminCodes = jcgv.getJasminCodes();
			for (int i = 0; i < jasminFileNames.size(); i++) {				
				TestCodeGenerator.createFile(jasminFileNames.get(i),jasminCodes.get(i));			
			}       
	        
			System.out.print("\nTest Code Generator Completed"); 
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
	   ** Este método permite escribir un archivo de la carpeta GeneratedCode
	   */
	   public static void createFile(String fileName, String text) throws IOException{
		   	String pathAux = "src/TestScanner.java";
		   	File f = new File(pathAux);
	   		String absolutePath = f.getAbsolutePath();
	    	String filePath= absolutePath.replaceAll(pathAux, "GeneratedCode/"+fileName+".j");
	    	
	    	BufferedWriter bw = null;
			FileWriter fw = null;

			try {
				fw = new FileWriter(filePath);
				bw = new BufferedWriter(fw);
				bw.write(text);

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}    	

	    	System.out.println("Generated Jasmin Code File: "+filePath+"\n");
	   }
}
