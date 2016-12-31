package SymbolTree;

import java.util.HashMap;
import java.util.Iterator;

public class Class extends Category{		

	public Class(Symbol s){		
		variables = new HashMap<String, Variable>();	
		metodos = new HashMap<String, Method>();
		setSymbol(s,"Class");
	}
	
	@Override
	public void printCategory() {
		super.printCategory();
		
		System.out.println();
		
		Iterator<String> it = variables.keySet().iterator();
		while (it.hasNext()){
			String key = it.next();
			Variable v = variables.get(key);
			
			v.printCategory();
		}		
				
		it = metodos.keySet().iterator();
		while (it.hasNext()){
			String key = it.next();
			Method m = metodos.get(key);
			
			m.printCategory();
		}	
		//System.out.println();
	}	
}
