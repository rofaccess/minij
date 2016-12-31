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
	       	
	        SemanticAnalyzerWithSymbolTreeVisitor sav = new SemanticAnalyzerWithSymbolTreeVisitor(st);
	        program.accept(sav);
	        
	        System.out.println();
	        JasminCodeGeneratorVisitor jcgv = new JasminCodeGeneratorVisitor(st);
	        program.accept(jcgv);
	        
	        System.out.println();
	        //System.out.println(jcgv.getJasminCode());
	        
	        st.printSymbolTree();
	        
			System.out.print("\n");
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
}
