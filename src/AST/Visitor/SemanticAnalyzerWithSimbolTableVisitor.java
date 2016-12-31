package AST.Visitor;

import AST.*;
import SymbolTable.*;

public class SemanticAnalyzerWithSimbolTableVisitor implements Visitor {
	private SymbolTable symbolTable;
	private String currentScope;
	private SemanticAnalyzer sa;
	
	//Constructor
	public SemanticAnalyzerWithSimbolTableVisitor(SymbolTable st){
		symbolTable = st;
		sa = new SemanticAnalyzer();
	}
	
	//
	public SymbolTable getSymbolTable(){
		return symbolTable;
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
		for ( int i = 0; i < n.sl.size(); i++ ) {	
			n.sl.get(i).accept(this);
		}		
		
		currentScope = n.i1.s;		
	}

	//MainClassEmpty
	//Identifier i1,i2;
	public void visit(MainClassEmpty n) {
		n.i1.accept(this);
		n.i2.accept(this);
		
		currentScope = n.i1.s;	
	}
	
	//ClassDeclSimple
	//Identifier i;
	//VarDeclList vl;
	//MethodDeclList ml;
	public void visit(ClassDeclSimple n) {
		n.i.accept(this);
		
		currentScope = n.i.toString();	
		
		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.get(i).accept(this);
			if ( i+1 < n.vl.size() ) { }
		}
		for ( int i = 0; i < n.ml.size(); i++ ) {
			n.ml.get(i).accept(this);
		}
		currentScope = "root";
	}

	//ClassDeclExtends
	//Identifier i;
	//Identifier j;
	//VarDeclList vl;
	//MethodDeclList ml;
	public void visit(ClassDeclExtends n) {
		n.i.accept(this);
		n.j.accept(this);
		
		currentScope = n.i.s;	
		
		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.get(i).accept(this);
			if ( i+1 < n.vl.size() ) {  }
		}
		for ( int i = 0; i < n.ml.size(); i++ ) {
			n.ml.get(i).accept(this);
		}
		currentScope = "root";
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
		
		Category v = new Variable(n.t.show(),null);
		Symbol var = new Symbol(n.i.s,currentScope,v,n.i.ln());		
		
			
	}	

	//VarDeclAssign
	//Type t;
	//Identifier i;
	//Exp e;
	public void visit(VarDeclAssign n) {
		n.t.accept(this);
		n.i.accept(this);
		n.e.accept(this);	
		
		sa.typeCheckVar(n.i, n.e, currentScope, symbolTable);
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
			
		sa.typeCheckVar(n.i, n.e, currentScope, symbolTable);
		//sa.refreshValueVar(n.i, n.e, currentScope, symbolTable);
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
		
		Category m = new Method();
		Symbol method = new Symbol(n.f1.i.s,currentScope,m,n.f1.i.ln());			
				
		currentScope = method.getIdentifier();
		
		for ( int i = 0; i < n.fl.size(); i++ ) {
			n.fl.get(i).accept(this);
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
		
		currentScope = method.getScope();
	}

	//MethodDeclSimple
	//Formal f1		
	//VarDeclList vl;
	//StatementList sl;
	//Exp e;
	public void visit(MethodDeclSimple n) {
n.f1.accept(this);
		
		Category m = new Method();
		Symbol method = new Symbol(n.f1.i.s,currentScope,m,n.f1.i.ln());		
						
		currentScope = method.getIdentifier();
		
		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.get(i).accept(this);
		}
		for ( int i = 0; i < n.sl.size(); i++ ) {
			n.sl.get(i).accept(this);
			if ( i < n.sl.size() ) {  }
		}
		n.e.accept(this);	
		
		currentScope = method.getScope();
	}
}
