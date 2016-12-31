package SymbolTree;

import java.beans.Expression;

import SymbolTable.SymbolTable;
import AST.Call;
import AST.CallMoreSimple;
import AST.CallSimple;
import AST.Exp;
import AST.Formal;
import AST.Identifier;
import AST.IdentifierExp;
import AST.IntegerLiteral;
import AST.Type;

public class SemanticAnalyzer {
	/** 3. Variables duplicadas **/
	
	/* Imprime un mensaje si se define dos clases con el mismo nombre */
	public static void classAlreadyExist(Class c, int ln){
		Category sc = c.getScope();
		if(sc==null){
			System.out.println("Line "+c.getLn()+": Class \""+c.getIdentifier()+"\" already exist inside file in line "+ln);
		}else{
			System.out.println("Line "+c.getLn()+": Class \""+c.getIdentifier()+"\" already exist inside "
					  +sc.getCategoryName()+" \""+sc.getIdentifier()+"\" in line "+ln);
		}
		
	}
	
	/* Imprime un mensaje si un metodo ya existe en un mismo bloque */
	public static void methodAlreadyExist(Method m, int ln){		
		System.out.println("Line "+m.getLn()+": Method \""+m.getIdentifier()+"\" already exist inside "
								  +m.getScope().getCategoryName()+" \""+m.getScope().getIdentifier()+"\" in line "+ln);
	}
	
	/* Imprime un mensaje si una variable ya existe en un mismo bloque */
	public static void variableAlreadyExist(Variable v, int ln){		
		System.out.println("Line "+v.getLn()+": Variable \""+v.getIdentifier()+"\" already exist inside "
								  +v.getScope().getCategoryName()+" \""+v.getScope().getIdentifier()+"\" in line "+ln);
	}
	
	/* 
	 * Verifica si el padre de subclass existe. Necesariamente la clase padre debe estar definido antes de subclass
	 * el scope de subclass guarda a la clase padre
	 */
	public static void classExtendExist(Class subClass, String classExtendIdentifier){		
		Category sc = subClass.getScope();
		if(sc==null){
			System.out.println("Line "+subClass.getLn()+": Parent Class \""+classExtendIdentifier+"\" is not defined for Class \""+subClass.getIdentifier()+"\"");
		}
		
	}
	
	/* Muestra un mensaje si un metodo se define con identificadores de parametros iguales */
	public static void parameterAlreadyExist(Parameter p){		
		Category sc = p.getScope();
		System.out.println("Line "+p.getLn()+": Parameter \""+p.getIdentifier()+"\" already defined for"
								  +sc.getCategoryName()+" \""+sc.getIdentifier());
	}
	
	/**
	 * 4. Asignación de variables del tipo incorrecto (type checking)
	 * Incorporamos subtipos (herencia) y scope checking.
	 **/	
	public static void typeCheckVariable(Identifier id,Exp ex, SymbolTree st, Category currentScope){		
		
		/* Verifica en la asignación a variable, si la variable fue declarada */		
		Variable varDeclared = currentScope.getVariable(id.s);	
		
		if(varDeclared==null){
			//Verifica si esta declarado en el scope del scope actual
			varDeclared = currentScope.getScope().getVariable(id.s);			
			if(varDeclared==null){					
				System.out.println("Line "+id.ln()+": Variable \""+id.s+"\" is not declared");
			}	
		}		
	
		if(ex instanceof IdentifierExp){			
			Variable varAssigned = currentScope.getVariable(ex.show());
			/* Verifica si la variable que se asigna esta declarada */			
			if(varAssigned==null){
				//Verifica si esta declarado en el scope del scope actual
				varAssigned=currentScope.getScope().getVariable(ex.show());
				/*if(varAssigned==null){	
					System.out.println("Line "+ex.ln()+": Variable \""+ex.show()+"\" is not declared");
				}*/
			}
			
			//Si ambas variables fueron declaradas se compara sus tipos			
			if(varDeclared!=null && varAssigned!=null){
				//Si el tipo de la variable declarada es distinto a la variable asignada
				if(!varDeclared.getType().equalsIgnoreCase(varAssigned.getType())){
					System.out.println("Line "+id.ln()+": Incompatible Types, \""+id.s+"\" is type \""+varDeclared.getType()+"\" and \""+ex.show()+"\" is type \""+varAssigned.getType()+"\"");
				}
			}				
		}
	}
	
	/* Verifica que las variables usadas para una operación sean del mismo tipo, asume que las operaciones se realizan sólo entre enteros */
	public static void checkOperation(Exp ex1,Exp ex2, SymbolTree st, Category currentScope){	
		//Las expresiones deben ser int o identifier para realizar el chequeo
		if(ex1 instanceof IdentifierExp && ex2 instanceof IntegerLiteral){
			Variable varEx1 = currentScope.getVariable(ex1.show());
			if(varEx1!=null){
				if(!varEx1.getType().equalsIgnoreCase("int")){
					System.out.println("Line "+ex1.ln()+": Operation not Permitted, \""+ex1.show()+"\" is type \""+varEx1.getType()+"\" and \""+ex2.show()+"\" is type \"int\"\"");
				}
			}
		}
		
		if(ex1 instanceof IntegerLiteral && ex2 instanceof IdentifierExp){
			Variable varEx2 = currentScope.getVariable(ex2.show());
			if(varEx2!=null){
				if(!varEx2.getType().equalsIgnoreCase("int")){
					System.out.println("Line "+ex1.ln()+": Operation not Permitted, \""+ex1.show()+"\" is type \"int\" and \""+ex2.show()+"\" is type \""+varEx2.getType()+"\"");
				}
			}	
		}		
	}
	
	/** 7. Chequeo de metodos existentes **/
	
	/* Cuando se hace una llamada Call se verifica si es un objeto llamando a un metodo
	 * Imprime mensajes acorde a si la clase existe y si el metodo pertenece a la clase existente
	 */
	public static void existMethodInClass(Identifier id,Exp ex, SymbolTree st, Category currentScope){		
		//System.out.println(currentScope.getIdentifier());		
		if(ex instanceof IdentifierExp){
			Variable objeto = currentScope.getVariable(ex.show());//Verificar si el objeto existe en el scope actual
																  //la mejora seria ver el scope del scope actual si fuera una subclase
			//System.out.println(objeto.getType());	
			if(objeto!=null){
				Class clase = st.getClass(objeto.getType());//El type del objeto declarado es una clase y se lo busca en la tabla
				
				if(clase!=null){
					//System.out.println(ex.show());
					if(!clase.metodos.containsKey(id.s)){
						System.out.println("Line "+ex.ln()+": \""+objeto.getIdentifier()+"\" is type \""+objeto.getType()+", Method \""+id.s+"\" no exist for Class \""+clase.getIdentifier()+"\"");
					}
				}else System.out.println("Line "+ex.ln()+": \""+objeto.getIdentifier()+"\" is type \""+objeto.getType()+"\", Class \""+objeto.getType()+"\" no exist");
				
				
			}else System.out.println("Line "+ex.ln()+": Variable \""+ex.show()+"\" is not declared");				
		}		
	}
	
	/* Cuando se hace una llamada Call se compara el tipo que devuelve con el 
	 * tipo de la variable a la que se asigna
	 */
	public static void checkMethodCallReturnType(Identifier id,Exp ex, SymbolTree st, Category currentScope){	
		Variable varDeclared = currentScope.getVariable(id.s);
		if(varDeclared!=null){
			if(ex instanceof CallMoreSimple){				
				Exp e1 = ((CallMoreSimple) ex).e1;				
				Variable objeto = currentScope.getVariable(e1.show());
				if(objeto!=null){
					Class clase = st.getClass(objeto.getType());
					Method metodo = clase.getMethod(((CallMoreSimple) ex).i.s);
					if(metodo!=null){
						if(!metodo.getType().equalsIgnoreCase(varDeclared.getType())){
							System.out.println("Line "+id.ln()+": Incompatible Types, \""+id.s+"\" is type \""+varDeclared.getType()+"\" and \""+((CallMoreSimple) ex).i.s+"\" return type \""+objeto.getType()+"\"");
						}
					}					
				}	
			}
			
			if(ex instanceof CallSimple){				
				Exp e1 = ((CallSimple) ex).e1;				
				Variable objeto = currentScope.getVariable(e1.show());
				if(objeto!=null){
					Class clase = st.getClass(objeto.getType());
					Method metodo = clase.getMethod(((CallSimple) ex).i.s);
					if(metodo!=null){
						if(!metodo.getType().equalsIgnoreCase(varDeclared.getType())){
							System.out.println("Line "+id.ln()+": Incompatible Types, \""+id.s+"\" is type \""+varDeclared.getType()+"\" and \""+((CallSimple) ex).i.s+"\" return type \""+objeto.getType()+"\"");
						}
					}					
				}	
			}
			
			if(ex instanceof Call){				
				Exp e1 = ((Call) ex).e1;				
				Variable objeto = currentScope.getVariable(e1.show());
				if(objeto!=null){
					Class clase = st.getClass(objeto.getType());
					Method metodo = clase.getMethod(((Call) ex).i.s);
					if(metodo!=null){
						if(!metodo.getType().equalsIgnoreCase(varDeclared.getType())){
							System.out.println("Line "+id.ln()+": Incompatible Types, \""+id.s+"\" is type \""+varDeclared.getType()+"\" and \""+((Call) ex).i.s+"\" return type \""+objeto.getType()+"\"");
						}
					}					
				}	
			}
		}
	}
	
	public static void checkMethodArgs(Identifier id,Exp ex, SymbolTree st, Category currentScope){	
		
	}
	
	/* Chequea que el valor retornado sea del tipo de retorno esperado */
	public static void checkReturnType(Formal f,Exp ex, SymbolTree st, Category currentScope){		
		if(ex instanceof IdentifierExp){
			Variable exVar = currentScope.getVariable(ex.show());
			if(exVar!=null){
				if(!exVar.getType().equalsIgnoreCase(f.t.show())){
					System.out.println("Line "+f.ln()+": Return type error, Method return type is \""+exVar.getType()+"\" and must be type \""+f.t.show()+"\"");
				}
			}
		}
		
		if(ex instanceof IntegerLiteral){
			if(!f.t.show().equalsIgnoreCase("int")){
				System.out.println("Line "+f.ln()+": Return type error, Method return type is \"int\" and must be type \""+f.t.show()+"\"");
			}			
		}	
	}
}
