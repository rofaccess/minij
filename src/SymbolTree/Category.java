package SymbolTree;

import java.util.HashMap;

public abstract class Category {
	private Symbol symbol;	
	
	protected HashMap<String,Variable> variables;	
	protected HashMap<String,Method> metodos;	
	protected HashMap<String,Parameter> parametros;	
	
	public void printCategory(){
		symbol.printSymbol();
	}
	
	public void addMethod(Method m){
		String key = m.getIdentifier();

		//Si el metodo ya existe se muestra un mensaje
		if(metodos.containsKey(key)){
			int line = metodos.get(key).getLn();
			SemanticAnalyzer.methodAlreadyExist(m,line);
		}else{
			metodos.put(key, m);
		}
	}
	
	public Method getMethod(String id){
		Method metodo = metodos.get(id);
		
		if(metodo!=null) return metodo;
		else return null;		
	}
	
	public Variable getVariable(String id){
		Variable variable = variables.get(id);
		
		if(variable!=null) return variable;
		else return null;		
	}
	
	public void addVariable(Variable v){
		String key = v.getIdentifier();

		//Si la variable ya existe se muestra un mensaje
		if(variables.containsKey(key)){
			int line = variables.get(key).getLn();
			SemanticAnalyzer.variableAlreadyExist(v,line);
		}else{
			variables.put(key, v);
		}
	}
	
	public void addParameter(Parameter p){
		String key = p.getIdentifier();

		//Si la variable ya existe se muestra un mensaje
		if(parametros.containsKey(key)){
			SemanticAnalyzer.parameterAlreadyExist(p);
		}else{
			parametros.put(key, p);
		}
	}

	public void setSymbol(Symbol s, String cn){
		symbol = s;
		setCategoryName(cn);
	}
			
	public String getIdentifier() {
		return symbol.getIdentifier();
	}

	public void setIdentifier(String id) {
		symbol.setIdentifier(id);;
	}

	public Category getScope() {
		return symbol.getScope();
	}

	public void setScope(Category sc) {
		symbol.setScope(sc);;
	}

	public String getCategoryName() {
		return symbol.getCategoryName();
	}

	public void setCategoryName(String cn) {
		symbol.setCategoryName(cn);
	}

	public int getLn() {
		return symbol.getLn();
	}

	public void setLn(int lineNumber) {
		symbol.setLn(lineNumber);
	}
}
