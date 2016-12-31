package AST.Visitor;

import SymbolTree.*;
import SymbolTree.Class;
import AST.*;

public class SymbolTreeVisitor implements Visitor {
	private SymbolTree symbolTree;
	private Category currentScope;
	private Symbol symbol;
	private Class clase;
	private Method metodo;
	private Variable variable;
	private Parameter parametro;
	
	//Constructor
	public SymbolTreeVisitor(){
		symbolTree = new SymbolTree();	
		currentScope = null;
	}
	
	//
	public SymbolTree getSymbolTree(){
		return symbolTree;
	}
	
	//Display
	public void visit(Display n) {
		n.e.accept(this);
	}
	
	//Program
	public void visit(Program n) {
		
	} 

	//ProgramComplete
	//MainClass m;
	//ClassDeclList cl;
	public void visit(ProgramComplete n) {
		n.m.accept(this);
		for ( int i = 0; i < n.cl.size(); i++ ) {
			n.cl.get(i).accept(this);
		}
	}

	//ProgramMainClass
	//MainClass m;
	public void visit(ProgramMainClass n) {
		n.m.accept(this);    
	}

	//ProgramClassDeclList
	//ClassDeclList cl;
	public void visit(ProgramClassDeclList n) {  
		for ( int i = 0; i < n.cl.size(); i++ ) {
			n.cl.get(i).accept(this);
		}
	}

	//MainClass
	public void visit(MainClass n) {
		
	}

	//MainClassStatementList
	//Identifier i1,i2;
	//StatementList sl;
	public void visit(MainClassStatementList n) {
		n.i1.accept(this);
		n.i2.accept(this);
		
		symbol = new Symbol(n.i1.s,currentScope,n.i1.ln());
		clase = new Class(symbol);		
		currentScope = clase;
		
		symbol = new Symbol("main",currentScope,n.i2.ln());
		metodo = new Method(symbol,"void",null);			
		currentScope = metodo;	
		
		symbol = new Symbol(n.i2.s,currentScope,n.i2.ln());
		parametro = new Parameter(symbol,"String[]");
		metodo.addParameter(parametro);
		
		clase.addMethod(metodo);
		
		for ( int i = 0; i < n.sl.size(); i++ ) {	
			n.sl.get(i).accept(this);
		}		
		
		symbolTree.addClass(clase);	
		currentScope = null;
	}

	//MainClassEmpty
	//Identifier i1,i2;
	public void visit(MainClassEmpty n) {
		n.i1.accept(this);
		n.i2.accept(this);
		
		symbol = new Symbol(n.i1.s,currentScope,n.i1.ln());
		clase = new Class(symbol);		
		currentScope = clase;
		
		symbol = new Symbol("main",currentScope,n.i2.ln());
		metodo = new Method(symbol,"void",null);			
		currentScope = metodo;	
		
		symbol = new Symbol(n.i2.s,currentScope,n.i2.ln());
		parametro = new Parameter(symbol,"String[]");
		metodo.addParameter(parametro);
		
		clase.addMethod(metodo);
		symbolTree.addClass(clase);	
		currentScope = null;
	}
	
	//ClassDeclSimple
	//Identifier i;
	//VarDeclList vl;
	//MethodDeclList ml;
	public void visit(ClassDeclSimple n) {
		n.i.accept(this);
		
		symbol = new Symbol(n.i.s,currentScope,n.i.ln());
		clase = new Class(symbol);		
		currentScope = clase;
		
		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.get(i).accept(this);
			if ( i+1 < n.vl.size() ) { }
		}
		for ( int i = 0; i < n.ml.size(); i++ ) {
			n.ml.get(i).accept(this);
		}
		
		symbolTree.addClass(clase);
		currentScope = null;
	}

	//ClassDeclExtends
	//Identifier i;
	//Identifier j;
	//VarDeclList vl;
	//MethodDeclList ml;
	public void visit(ClassDeclExtends n) {
		n.i.accept(this);
		n.j.accept(this);
				
		Class scopeExtend = symbolTree.getClass(n.j.s);
		
		symbol = new Symbol(n.i.s,scopeExtend,n.i.ln());
		clase = new Class(symbol);	
		
		SemanticAnalyzer.classExtendExist(clase, n.j.s);
					
		currentScope = clase;
		
		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.get(i).accept(this);
			if ( i+1 < n.vl.size() ) {  }
		}
		for ( int i = 0; i < n.ml.size(); i++ ) {
			n.ml.get(i).accept(this);
		}
		
		symbolTree.addClass(clase);
		currentScope = null;
	}

	//VarDecl
	public void visit(VarDecl n) {		
	
	}

	//VarDeclSimple
	//Type t;
	//Identifier i;
	public void visit(VarDeclSimple n) {
		n.t.accept(this);
		n.i.accept(this);
		
		symbol = new Symbol(n.i.s,currentScope,n.i.ln());
		variable = new Variable(symbol ,n.t.show(), null);
				
		if(currentScope.getCategoryName().equalsIgnoreCase("Method")){
			metodo.addVariable(variable);
		}else if(currentScope.getCategoryName().equalsIgnoreCase("Class")){
			clase.addVariable(variable);
		}		
	}	

	//VarDeclAssign
	//Type t;
	//Identifier i;
	//Exp e;
	public void visit(VarDeclAssign n) {
		n.t.accept(this);
		n.i.accept(this);
		n.e.accept(this);
		
		symbol = new Symbol(n.i.s,currentScope,n.i.ln());
		variable = new Variable(symbol ,n.t.show(), n.e);
				
		if(currentScope.getCategoryName().equalsIgnoreCase("Method")){
			metodo.addVariable(variable);
		}else if(currentScope.getCategoryName().equalsIgnoreCase("Class")){
			clase.addVariable(variable);
		}
	}

	//MethodDecl
	public void visit(MethodDecl n) {

	}

	//Formal
	//Type t;
	//Identifier i;
	public void visit(Formal n) {
		n.t.accept(this);
		n.i.accept(this);
		/*
		symbol = new Symbol(n.i.s,currentScope,n.i.ln());
		parametro = new Parameter(symbol,n.t.show());
		metodo.addParameter(parametro);*/
	}

	//IntArrayType
	public void visit(IntArrayType n) {

	}
	
	//IntegerType
	public void visit(IntegerType n) {
	}

	//IdentifierType
	//String s;
	public void visit(IdentifierType n) {

	}

	//Block
	public void visit(Block n) {
		for ( int i = 0; i < n.sl.size(); i++ ) {
			n.sl.get(i).accept(this);
		}
	}

	//If
	//Exp e;
	//Statement s1,s2;
	public void visit(If n) {
		n.e.accept(this);
		n.s1.accept(this);
		n.s2.accept(this);
	}

	//While
	//Exp e;
	//Statement s;
	public void visit(While n) {
		n.e.accept(this);
		n.s.accept(this);
	}

	//Print
	//Exp e;
	public void visit(Print n) {
		n.e.accept(this);
	}

	//Assign
	//Identifier i;
	//Exp e;
	public void visit(Assign n) {
		n.i.accept(this);
		n.e.accept(this);
	}

	//ArrayAssign
	//Identifier i;
	//Exp e;
	public void visit(ArrayAssign n) {
		n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
	}

	//And
	//Exp e1,e2;
	public void visit(And n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	//Less
	//Exp e1,e2;
	public void visit(Less n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	//Major
	//Exp e1,e2;
	public void visit(Major n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	//Plus
	//Exp e1,e2;
	public void visit(Plus n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	//Minus
	//Exp e1,e2;
	public void visit(Minus n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	//Times
	//Exp e1,e2;
	public void visit(Times n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	//ArrayLookup
	//Exp e1,e2;
	public void visit(ArrayLookup n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	//ArrayLength
	//Exp e;
	public void visit(ArrayLength n) {
		n.e.accept(this);
	}

	//Call
	//Exp e1;
	//Identifier i;
	//Exp e2;
	//ExpList el;
	public void visit(Call n) {
		n.e1.accept(this);
		n.i.accept(this);
		n.e2.accept(this);

		for ( int i = 0; i < n.el.size(); i++ ) {			
			n.el.get(i).accept(this);
			if ( i+1 < n.el.size() ) {  }
		}
	}

	//CallSimple
	//Exp e1;
	//Identifier i;
	//Exp e2;
	public void visit(CallSimple n) {
		n.e1.accept(this);
		n.i.accept(this);
		n.e2.accept(this);
	}

	//CallMoreSimple
	//Exp e1;
	//Identifier i;
	public void visit(CallMoreSimple n) {
		n.e1.accept(this);
		n.i.accept(this);
	}

	//IntegerLiteral
	//int i;
	public void visit(IntegerLiteral n) {
		
	}

	//IdentifierExp
	//String s;
	public void visit(IdentifierExp n) {
		
	}

	public void visit(This n) {
		
	}

	//NewArray
	//Exp e;
	public void visit(NewArray n) {
		n.e.accept(this);
	}

	//NewObject
	//Identifier i;
	public void visit(NewObject n) {

	}

	//Identifier
	//String s;
	public void visit(Identifier n) {
		
	}

	//BlockEmpty
	public void visit(BlockEmpty n) {			

	}

	//Dbecomes
	//Exp e1,e2;
	public void visit(Dbecomes n) {		
		n.e1.accept(this);
		n.e2.accept(this);	
	}

	//Distinct	
	//Exp e1,e2;
	public void visit(Distinct n) {		
		n.e1.accept(this);
		n.e2.accept(this);		
	}

	//Or	
	//Exp e1,e2;
	public void visit(Or n) {
		n.e1.accept(this);
		n.e2.accept(this);		
	}

	//Divide	
	//Exp e1,e2;
	public void visit(Divide n) {
		n.e1.accept(this);
		n.e2.accept(this);		
	}

	//MethodDeclComplete	
	//Formal f1
	//Formal f2
	//FormalList fl;
	//VarDeclList vl;
	//StatementList sl;
	//Exp e;		
	public void visit(MethodDeclComplete n) {
		n.f1.accept(this);
		n.f2.accept(this);	
		
		symbol = new Symbol(n.f1.i.s,currentScope,n.f1.i.ln());
		metodo = new Method(symbol,n.f1.t.show(),n.e);		
		currentScope = metodo;
		
		symbol = new Symbol(n.f2.i.s,currentScope,n.f2.ln());
		parametro = new Parameter(symbol,n.f2.t.show());
		metodo.addParameter(parametro);
		
		
		
		for ( int i = 0; i < n.fl.size(); i++ ) {
			Formal f= n.fl.get(i);
			f.accept(this);
			
			symbol = new Symbol(f.i.s,currentScope,f.i.ln());
			parametro = new Parameter(symbol,f.t.show());
			metodo.addParameter(parametro);
			
			if (i+1 < n.fl.size()) {  }
		}

		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.get(i).accept(this);
		}
		for ( int i = 0; i < n.sl.size(); i++ ) {
			n.sl.get(i).accept(this);
			if ( i < n.sl.size() ) {  }
		}
		n.e.accept(this);	
		
		clase.addMethod(metodo);	
		currentScope = clase;
	}

	//MethodDeclSimple
	//Formal f1		
	//VarDeclList vl;
	//StatementList sl;
	//Exp e;
	public void visit(MethodDeclSimple n) {
		n.f1.accept(this);
		
		symbol = new Symbol(n.f1.i.s,currentScope,n.f1.i.ln());
		metodo = new Method(symbol,n.f1.t.show(),n.e);		
		
		currentScope = metodo;
		
		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.get(i).accept(this);
		}
		for ( int i = 0; i < n.sl.size(); i++ ) {
			n.sl.get(i).accept(this);
			if ( i < n.sl.size() ) {  }
		}
		n.e.accept(this);	
		
		clase.addMethod(metodo);	
		currentScope = clase;
	}
}
