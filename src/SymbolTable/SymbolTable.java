package SymbolTable;

import java.util.HashMap;
import java.util.Iterator;

public class SymbolTable {
	private HashMap<Symbol,Symbol> symbols;		
	
	public SymbolTable(){		
		symbols = new HashMap<Symbol, Symbol>();
	}
	
	public void addSymbol(Symbol s){
		SemanticAnalyzer sa = new SemanticAnalyzer();
		
	    /* Si el simbolo no existe en el hash, se lo agrega */
		if(!sa.symbolExists(s, symbols)) symbols.put(s, s);
	}
	
	public Symbol getSymbol(Symbol s){
		return symbols.get(s);
	}
	
	public boolean existSymbolKey(Symbol s){
		return symbols.containsKey(s);
	}
	
	//Imprime la tabla de simbolos
	public void printSymbolTable(){		
		System.out.println("Line\tIdentifier\tScope\t\tCategory\tType\t\tValue\t\tReturn\t\tArg\n");
		
		Iterator<Symbol> it = symbols.keySet().iterator();
		while (it.hasNext()){
			Symbol key = it.next();
			Symbol s = symbols.get(key);
			
			s.printSymbol();
		}	
			
	}
}
