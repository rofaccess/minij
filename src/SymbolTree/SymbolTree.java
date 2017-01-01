package SymbolTree;

import java.util.HashMap;
import java.util.Iterator;

public class SymbolTree {
	private HashMap<String,Class> clases;	
	
	public SymbolTree(){		
		clases = new HashMap<String, Class>();		
	}
	
	public void addClass(Class c){
		String key = c.getIdentifier();
		
		//Si la clase ya existe se muestra un mensaje
		if(clases.containsKey(key)){
			int line = clases.get(key).getLn();
			SemanticAnalyzer.classAlreadyExist(c,line);
		}else{
			clases.put(key, c);
		}
	}
	
	public Class getClass(String id){
		Class clase = clases.get(id);
		
		if(clase!=null) return clase;
		else return null;		
	}
				
	//Imprime el arbol de categorias
	public void printSymbolTree(){	
		System.out.println("\n#####  Symbol Tree #####");
		System.out.print("\nLine\tIdentifier\tScope\t\tCategory\tType\t\tValue\t\tReturn\n");
		
		Iterator<String> it = clases.keySet().iterator();
		while (it.hasNext()){
			String key = it.next();
			Class c = clases.get(key);
			
			c.printCategory();
		}	
			
	}
}
