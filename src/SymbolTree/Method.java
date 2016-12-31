package SymbolTree;

import java.util.HashMap;
import java.util.Iterator;

import AST.Exp;

public class Method extends Category{	
	private String type;	  //Se refiere al tipo de retorno esperado 	
	private Exp returnType;//Se refiere al tipo de la expresion retornada
	
	public Method(Symbol s, String type, Exp returnType){
		parametros = new HashMap<String,Parameter>();
		variables = new HashMap<String, Variable>();
		this.type = type;
		this.returnType = returnType;
		setSymbol(s,"Method");	
	}	
	
	public String getType(){
		return type;
	}
	
	public void setType(String t){
		type = t;
	}
		
	@Override
	public void printCategory() {
		super.printCategory();	
		
		int la = getCategoryName().length();
		while(la < 16){
			System.out.print(" ");
			la++;
		}
		
		System.out.print(type);
		
		la = type.length();
		while(la < 32){
			System.out.print(" ");
			la++;
		}
		if(returnType!=null) System.out.println(returnType.show());
		else System.out.println("null");
		
		Iterator<String> it = parametros.keySet().iterator();
		while (it.hasNext()){
			String key = it.next();
			Parameter p = parametros.get(key);
			
			p.printCategory();
		}		
				
		it = variables.keySet().iterator();
		while (it.hasNext()){
			String key = it.next();
			Variable v = variables.get(key);
			
			v.printCategory();
		}	
		//System.out.println();
	}	
}
