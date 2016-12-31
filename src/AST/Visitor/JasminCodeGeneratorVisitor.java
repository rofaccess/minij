package AST.Visitor;

import AST.*;
import SymbolTree.*;
import SymbolTree.Class;

public class JasminCodeGeneratorVisitor implements Visitor {
	private SymbolTree symbolTree;
	private Category currentScope;
	private String jasminCode;
	private String jasminCodeFileName;
	private String currentClass;
	private String currentType;

	//Constructor
	public JasminCodeGeneratorVisitor(SymbolTree st){
		symbolTree = st;
		currentScope = null;
		jasminCode = new String();
		currentClass = "NULL_CLASS";
		currentType = "NULL_TYPE";
	}

	//
	public SymbolTree getSymbolTable(){
		return symbolTree;
	}

	public String getJasminCode(){
		return jasminCode;
	}

	public String getJasminCodeFileName(){
		return jasminCodeFileName;
	}
	
	private void addCode(String code){
		System.out.print(code);
		jasminCode = jasminCode + code;
	}

	//Display added for toy example language.  Not used in regular MiniJ
	public void visit(Display n) {
		n.e.accept(this);
	}

	//
	public void visit(Program n) {

	} 

	//MainClass m;
	//ClassDeclList cl;
	public void visit(ProgramComplete n) {
		n.m.accept(this);
		for ( int i = 0; i < n.cl.size(); i++ ) {
			//System.out.println();
			n.cl.get(i).accept(this);
		}
	}

	//MainClass m;
	public void visit(ProgramMainClass n) {
		n.m.accept(this);    
	}

	//ClassDeclList cl;
	public void visit(ProgramClassDeclList n) {  
		for ( int i = 0; i < n.cl.size(); i++ ) {
			//System.out.println();
			n.cl.get(i).accept(this);
		}
	}

	//
	public void visit(MainClass n) {

	}

	//Identifier i1,i2;
	//StatementList sl;
	public void visit(MainClassStatementList n) {	
		n.i1.accept(this);		
		jasminCodeFileName = n.i1.s;
		addCode(".source " + n.i1 + ".j");
		addCode("\n.class public " + n.i1);
		addCode("\n.super java/lang/Object");
		n.i2.accept(this);
		addCode("\n\n.method public static main([Ljava/lang/String;)V");
		addCode("\n  .limit stack 5");
		addCode("\n  .limit locals 100");	
		for ( int i = 0; i < n.sl.size(); i++ ) {	
			//System.out.print("    ");
			n.sl.get(i).accept(this);
			//System.out.println();
		}
		addCode("\n  return");
		addCode("\n.end method");
		addCode("\n;end class "+n.i1);		
	}

	//Identifier i1,i2;
	public void visit(MainClassEmpty n) {		
		n.i1.accept(this);
		jasminCodeFileName = n.i1.s;
		addCode("\n\n.source " + n.i1 + ".j");
		addCode("\n.class public " + n.i1);
		addCode("\n.super java/lang/Object");			
		
		n.i2.accept(this);	
		addCode("\n\n.method public static main([Ljava/lang/String;)V");
		
		addCode("\n  return");
		
		addCode("\n.end method");
		addCode("\n;end class "+n.i1);
	}


	//Identifier i;
	//VarDeclList vl;
	//MethodDeclList ml;
	public void visit(ClassDeclSimple n) {		
		n.i.accept(this);
		addCode("\n\n.source " + n.i + ".j");
		addCode("\n.class public " + n.i);
		addCode("\n.super java/lang/Object");
		
		for ( int i = 0; i < n.vl.size(); i++ ) {
			//System.out.print("  ");
			n.vl.get(i).accept(this);
			if ( i+1 < n.vl.size() ) { 
				//System.out.println(); 
			}
		}
		for ( int i = 0; i < n.ml.size(); i++ ) {
			//System.out.println();
			n.ml.get(i).accept(this);
		}
		addCode("\n;end class "+n.i);		
	}

	//Identifier i;
	//Identifier j;
	//VarDeclList vl;
	//MethodDeclList ml;
	public void visit(ClassDeclExtends n) {		
		n.i.accept(this);
		addCode("\n\n.source " + n.i + ".j");
		addCode("\n.class public " + n.i);		
		n.j.accept(this);
		addCode("\n.super " + n.j);
		
		for ( int i = 0; i < n.vl.size(); i++ ) {
			//System.out.print("  ");
			n.vl.get(i).accept(this);
			if ( i+1 < n.vl.size() ) { 
				//System.out.println(); 
			}
		}
		for ( int i = 0; i < n.ml.size(); i++ ) {
			//System.out.println();
			n.ml.get(i).accept(this);
		}
		addCode("\n;end class "+n.i);
	}

	//
	public void visit(VarDecl n) {		
	}

	//Type t;
	//Identifier i;
	public void visit(VarDeclSimple n) {
		n.t.accept(this);
		//System.out.print(" ");
		n.i.accept(this);
		//System.out.print(";");
	}

	//Type t;
	//Identifier i;
	//Exp e;
	public void visit(VarDeclAssign n) {
		n.t.accept(this);
		//System.out.print(" ");
		n.i.accept(this);
		//System.out.print(" = ");
		n.e.accept(this);
		//System.out.print(";");
	}

	//
	public void visit(MethodDecl n) {

	}

	//Type t;
	//Identifier i;
	public void visit(Formal n) {
		n.t.accept(this);
		//System.out.print(" ");
		n.i.accept(this);
	}

	public void visit(IntArrayType n) {
		currentType="[I";
	}


	public void visit(IntegerType n) {
		currentType="I";
	}

	//String s;
	public void visit(IdentifierType n) {
		currentType = "L" + n.s +";";
	}

	//StatementList sl;
	public void visit(Block n) {
		//System.out.println("{ ");
		for ( int i = 0; i < n.sl.size(); i++ ) {
			//System.out.print("      ");
			n.sl.get(i).accept(this);
			//System.out.println();
		}
		//System.out.print("    } ");
	}

	//Exp e;
	//Statement s1,s2;
	public void visit(If n) {
		//System.out.print("if (");
		n.e.accept(this);
		//System.out.println(") ");
		//System.out.print("    ");
		n.s1.accept(this);
		//System.out.println();
		//System.out.print("    else ");
		n.s2.accept(this);
	}

	//Exp e;
	//Statement s;
	public void visit(While n) {
		//System.out.print("while (");
		n.e.accept(this);
		//System.out.print(") ");
		n.s.accept(this);
	}

	//Exp e;
	public void visit(Print n) {		
		n.e.accept(this);
		//addCode("\n  iload 0");//Asume que la exp e se evaluó y se guardó con "istore 0" 
		addCode("\n  getstatic java/lang/System/out Ljava/io/PrintStream;");
		addCode("\n  swap");
		addCode("\n  invokevirtual java/io/PrintStream/println(I)V");
	}

	//Identifier i;
	//Exp e;
	public void visit(Assign n) {
		n.i.accept(this);
		//System.out.print(" = ");
		n.e.accept(this);
		//System.out.print(";");
	}

	//Identifier i;
	//Exp e1,e2;
	public void visit(ArrayAssign n) {
		n.i.accept(this);
		//System.out.print("[");
		n.e1.accept(this);
		//System.out.print("] = ");
		n.e2.accept(this);
		//System.out.print(";");
	}

	//Exp e1,e2;
	public void visit(And n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" && ");
		n.e2.accept(this);
		//System.out.print(")");
	}

	//Exp e1,e2;
	public void visit(Less n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" < ");
		n.e2.accept(this);
		//System.out.print(")");
	}

	//Exp e1,e2;
	public void visit(Major n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" > ");
		n.e2.accept(this);
		//System.out.print(")");
	}

	//Exp e1,e2;
	public void visit(Plus n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" + ");
		n.e2.accept(this);
		//System.out.print(")");
	}

	//Exp e1,e2;
	public void visit(Minus n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" - ");
		n.e2.accept(this);
		//System.out.print(")");
	}

	//Exp e1,e2;
	public void visit(Times n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" * ");
		n.e2.accept(this);
		//System.out.print(")");
	}

	//Exp e1,e2;
	public void visit(ArrayLookup n) {
		n.e1.accept(this);
		//System.out.print("[");
		n.e2.accept(this);
		//System.out.print("]");
	}

	//Exp e;
	public void visit(ArrayLength n) {
		n.e.accept(this);
		//System.out.print(".length");
	}

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
			if ( i+1 < n.el.size() ) { 
				//System.out.print(", "); 
			}
		}
		
		addCode("\n  invokevirtual "+ n.e1.show() + "/" + n.i + "()I");
	}

	//Exp e1;
	//Identifier i;
	//Exp e2;
	public void visit(CallSimple n) {
		n.e1.accept(this);				
		n.i.accept(this);		
		n.e2.accept(this);			
		
		//new Fac().ComputeFac(10)   -> exp1.id(exp2)
		addCode("\n  invokevirtual "+ n.e1.show() + "/" + n.i + "()I");
	}

	//Exp e1;
	//Identifier i;
	public void visit(CallMoreSimple n) {
		n.e1.accept(this);
		n.i.accept(this);
		
		if(n.e1 instanceof NewObject){
			NewObject newObject = (NewObject) n.e1;
			String tipoRetorno = symbolTree.getClass(newObject.i.s).getMethod(n.i.s).getType();
			//invokevirtual Clase/Metodo()TipoRetorno
			addCode("\n  invokevirtual "+ newObject.i + "/" + n.i + "()"+currentType);
		}		
		
	}

	//int i;
	public void visit(IntegerLiteral n) {
		addCode("\n  ldc " + n.i);
		//addCode("\n  istore 0");
	}


	//String s;
	public void visit(IdentifierExp n) {
		//System.out.print(n.s);
	}

	public void visit(This n) {
		//System.out.print("this");
	}

	//Exp e;
	public void visit(NewArray n) {
		//System.out.print("new int [");
		n.e.accept(this);
		//System.out.print("]");
	}

	//Identifier i;
	public void visit(NewObject n) {
		addCode("\n  new "+n.i);
		addCode("\n  dup");
		addCode("\n  invokenonvirtual " + n.i + "/<init>()V");
		currentClass = n.i.s;
	}

	//String s;
	public void visit(Identifier n) {
		//System.out.print(n.s);//Imprime los identificadores Ej. Main, Factorial, etc.
	}

	//
	public void visit(BlockEmpty n) {			
		//System.out.println("{\n    }");	
	}

	//Exp e1,e2;
	public void visit(Dbecomes n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" == ");
		n.e2.accept(this);
		//System.out.print(")");

	}

	//Exp e1,e2;	
	public void visit(Distinct n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" != ");
		n.e2.accept(this);
		//System.out.print(")");		
	}

	//Exp e1,e2;	
	public void visit(Or n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" || ");
		n.e2.accept(this);
		//System.out.print(")");		
	}

	//Exp e1,e2;	
	public void visit(Divide n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" / ");
		n.e2.accept(this);
		//System.out.print(")");		
	}

	//Formal f1
	//Formal f2
	//FormalList fl;
	//VarDeclList vl;
	//StatementList sl;
	//Exp e;				
	public void visit(MethodDeclComplete n) {		
		n.f1.accept(this);
		addCode("\n\n.method public " + n.f1.i + "()" + currentType);
		
		n.f2.accept(this);			
		for ( int i = 0; i < n.fl.size(); i++ ) {
			if (i==0){
				//System.out.print(", "); 
			}
			n.fl.get(i).accept(this);
			if (i+1 < n.fl.size()) { 
				//System.out.print(", "); 
			}
		}
		//System.out.println(") { ");
		for ( int i = 0; i < n.vl.size(); i++ ) {
			//System.out.print("    ");
			n.vl.get(i).accept(this);
			//System.out.println("");
		}
		for ( int i = 0; i < n.sl.size(); i++ ) {
			//System.out.print("    ");
			n.sl.get(i).accept(this);
			if ( i < n.sl.size() ) { 
				//System.out.println(""); 
			}
		}
		n.e.accept(this);
		addCode("\n  iload0");  //Agrega el resultado de n.e al stack
		addCode("\n  ireturn"); //Retorna lo agregado al stack?	
		addCode("\n.end method");					
	}

	//Formal f1		
	//VarDeclList vl;
	//StatementList sl;
	//Exp e;
	public void visit(MethodDeclSimple n) {		
		n.f1.accept(this);
		addCode("\n\n.method public " + n.f1.i + "()"+currentType);
				
		for ( int i = 0; i < n.vl.size(); i++ ) {
			//System.out.print("    ");
			n.vl.get(i).accept(this);
			//System.out.println("");
		}
		for ( int i = 0; i < n.sl.size(); i++ ) {
			//System.out.print("    ");
			n.sl.get(i).accept(this);
			if ( i < n.sl.size() ) { 
				//System.out.println("");
			}
		}
		
		n.e.accept(this);
		addCode("\n  iload0");  //Agrega el resultado de n.e al stack
		addCode("\n  ireturn"); //Retorna lo agregado al stack?	
		addCode("\n.end method");			

	}
}