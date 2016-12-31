import Scanner.*;
import SymbolTable.SymbolTable;
import Parser.*;
import AST.*;
import AST.Visitor.*;
import java_cup.runtime.Symbol;
/* Todo lo que respecta a Symbol Table esta descontinuada. El análisis semántico con Symbol Tree es más completo */
public class TestSymbolTable {
	public static void main(String [] args) {
		try {
			// create a scanner on the input file
			scanner s = new scanner(TestScanner.file("MinTypeCheckingErrors.java"));
			parser p = new parser(s);
			Symbol root;
			// replace p.parse() with p.debug_parse() in next line to see trace of
			// parser shift/reduce actions during parse
			
			root = p.parse();
			Program program = (Program) root.value;
			SymbolTableVisitor stv = new SymbolTableVisitor();
			program.accept(stv);			
			
			SymbolTable st=stv.getSymbolTable();
	        
	       
	        SemanticAnalyzerWithSimbolTableVisitor sav = new SemanticAnalyzerWithSimbolTableVisitor(st);
	        program.accept(sav);
			
	        st.printSymbolTable();
	        
			System.out.print("\n");
			System.out.print("\nTest Symbol Table Completed"); 
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