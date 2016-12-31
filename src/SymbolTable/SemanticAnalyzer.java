package SymbolTable;

import java.util.HashMap;

import SymbolTable.Category;
import SymbolTable.SymbolTable;
import SymbolTable.Symbol;
import SymbolTable.Variable;
import SymbolTree.Class;
import AST.Exp;
import AST.Identifier;

public class SemanticAnalyzer {

	/**
	 * 1. Variables no utilizadas.
	 * 
	 **/
	public void notUsedVar(){

	}

	/**
	 * 3. Variables Duplicadas
	 * Verifica si el simbolo existe en el hash
	 * Devuelve true cuando ya existe e imprime un mensaje
	 **/
	public boolean symbolExists(Symbol s, HashMap<Symbol,Symbol> symbols){			
		
		if(symbols.containsKey(s)){
			String category = s.getCategory().getCategoryName(); 

			if(category.equalsIgnoreCase("Variable")){
				System.out.println("Linea "+s.getLn()+": La variable \""+s.getIdentifier()+"\" ya esta definida");
			}
			else if(category.equalsIgnoreCase("Method")){				
				System.out.println("Linea "+s.getLn()+": El metodo \""+s.getIdentifier()+"\" ya esta definido");
			}
			else if(category.equalsIgnoreCase("Class")){
				System.out.println("Linea "+s.getLn()+": La clase \""+s.getIdentifier()+"\" ya esta definida");
			}

			return true;

		}else return false;
	}

	/**
	 * 4. Asignación de variables del tipo incorrecto (type checking)
	 * Incorporamos subtipos (herencia) y scope checking.
	 **/
	public void typeCheckVar(Identifier id, Exp ex, String currentScope, SymbolTable st){		
		//Todo esto para sacar el type de id
		Category v = new Variable(null,null);		
		Symbol s = new Symbol(id.s,currentScope,v,id.ln());	
		Symbol sId = st.getSymbol(s);

		if(sId!=null){		

			Variable typeId = (Variable) sId.getCategory();

			String typeOfId = typeId.getType();

			String className = ex.getClass().toString();		
			//Cuando la expresion es una variable
			if(className.equalsIgnoreCase("class AST.IdentifierExp")){
				//Todo esto para sacar el type de exp
				v = new Variable(null,null);		
				s = new Symbol(ex.show(),currentScope,v,ex.ln());	
				Symbol sExp = st.getSymbol(s);

				//Verifica si la expresion esta definida en el scope actual
				if(sExp!=null){

					Variable typeExp = (Variable) sExp.getCategory();

					String typeOfExp = typeExp.getType();
					//Verifica si el tipo de id es igual al tipo de expression
					if(!typeOfId.equalsIgnoreCase(typeOfExp)){
						System.out.println("Linea "+id.ln()+": Tipos incompatibles, \""+id.s+"\" es de tipo \""+typeOfId+"\" y \""+ex.show()+"\" es de tipo \""+typeOfExp+"\"");
					}
				}else System.out.println("Linea "+id.ln()+": La expresion, \""+s.getIdentifier()+"\" no esta declarada");
				//Cuando la expresion es un numero entero
			}else if(className.equalsIgnoreCase("class AST.IntegerLiteral")){			
				//Verifica si el tipo de id es igual al tipo de expression
				if(!typeOfId.equalsIgnoreCase("int")){
					System.out.println("Linea "+id.ln()+": Tipos incompatibles, \""+id.s+"\" es de tipo \""+typeOfId+"\" y \""+ex.show()+"\" es de tipo \"int\"");
				}
			}
			//Cuando la expresion es un metodo, se debe verificar el tipo de retorno del metodo								
		}
	}	
}
