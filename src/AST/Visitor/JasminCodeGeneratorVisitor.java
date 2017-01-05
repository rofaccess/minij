package AST.Visitor;

import java.util.ArrayList;

import AST.*;
import SymbolTree.*;
import SymbolTree.Class;

public class JasminCodeGeneratorVisitor implements Visitor {
	private SymbolTree symbolTree;
	private Category currentScope;
	private ArrayList<String> jasminCodes;
	private String jasminCode;
	private ArrayList<String> jasminCodeFileNames;
	private String currentClass;
	private String currentType;
	private int label;
	private String indent;

	//Constructor
	public JasminCodeGeneratorVisitor(SymbolTree st){
		symbolTree = st;
		currentScope = null;
		jasminCode = new String();
		jasminCodes = new ArrayList<String>();
		jasminCodeFileNames = new ArrayList<String>();
		currentClass = "NULL_CLASS";
		currentType = "NULL_TYPE";
		label = 0;
		indent = "\n  ";
	}

	//
	public SymbolTree getSymbolTable(){
		return symbolTree;
	}

	public ArrayList<String> getJasminCodes(){
		return jasminCodes;
	}
	
	public String getJasminCode(){
		return jasminCode;
	}

	public ArrayList<String> getJasminCodeFileNames(){
		return jasminCodeFileNames;
	}
	
	private void addCode(String code){
		//System.out.print(code);
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
		jasminCodeFileNames.add(n.i1.s);
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
		
		jasminCodes.add(jasminCode);
		jasminCode = new String();
	}

	//Identifier i1,i2;
	public void visit(MainClassEmpty n) {		
		n.i1.accept(this);
		jasminCodeFileNames.add(n.i1.s);
		
		addCode(".source " + n.i1 + ".j");
		addCode("\n.class public " + n.i1);
		addCode("\n.super java/lang/Object");			
		
		n.i2.accept(this);	
		addCode("\n\n.method public static main([Ljava/lang/String;)V");		
		addCode("\n  return");		
		addCode("\n.end method");
		
		jasminCodes.add(jasminCode);
		jasminCode = new String();
	}


	//Identifier i;
	//VarDeclList vl;
	//MethodDeclList ml;
	public void visit(ClassDeclSimple n) {		
		n.i.accept(this);
		jasminCodeFileNames.add(n.i.s);
		
		addCode(".source " + n.i + ".j");
		addCode("\n.class public " + n.i);
		addCode("\n.super java/lang/Object");
		
		//Declaración explicita del constructor de la clase		
		addCode(classConstructor());
		
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
		
		jasminCodes.add(jasminCode);
		jasminCode = new String();
	}

	//Identifier i;
	//Identifier j;
	//VarDeclList vl;
	//MethodDeclList ml;
	public void visit(ClassDeclExtends n) {		
		n.i.accept(this);
		addCode(".source " + n.i + ".j");
		addCode("\n.class public " + n.i);		
		n.j.accept(this);
		addCode("\n.super " + n.j);
		
		//Declaración explicita del constructor de la clase
		addCode(classConstructor());
		
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
		
		jasminCodes.add(jasminCode);
		jasminCode = new String();
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
		
		addCode(indent + "istore 0");
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
		int ifLabel = label;
		int elseLabel = ifLabel + 1;
		int endLabel = elseLabel +1;
		
		addCode("\n  goto Label"+elseLabel);
		addCode("\n  Label"+ifLabel+":");
		indent = "\n    ";
		n.s1.accept(this);
		addCode(indent + "goto Label"+endLabel);
		//System.out.println();
		//System.out.print("    else ");		
		addCode("\n  Label"+elseLabel+":");		
		n.s2.accept(this);
		indent = "\n  ";
		addCode("\n  Label"+(endLabel)+":");
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
		
		addCode(indent + "getstatic java/lang/System/out Ljava/io/PrintStream;");
		addCode(indent + "iload 0");
		addCode(indent + "invokevirtual java/io/PrintStream/println("+currentType(currentType)+")V");
	}

	//Identifier i;
	//Exp e;
	public void visit(Assign n) {
		n.i.accept(this);
		//System.out.print(" = ");
		n.e.accept(this);
		//System.out.print(";");	
		
		addCode(indent + "istore 0");
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
		
		addCode(indent + "iand");
	}

	//Exp e1,e2;
	public void visit(Less n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" < ");
		n.e2.accept(this);
		//System.out.print(")");
		
		addCode("\n  isub");
		label += 1;
		addCode("\n  iflt Label"+label);
	}

	//Exp e1,e2;
	public void visit(Major n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" > ");
		n.e2.accept(this);
		//System.out.print(")");
		
		addCode("\n  isub");
		label += 1;
		addCode("\n  ifgt Label"+label);
	}

	//Exp e1,e2;
	public void visit(Plus n) {
		//System.out.print("(");
		n.e1.accept(this);		
		//System.out.print(" + ");		
		n.e2.accept(this);
		//System.out.print(")");		
		
		addCode(indent + "iadd");
	}

	//Exp e1,e2;
	public void visit(Minus n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" - ");
		n.e2.accept(this);
		//System.out.print(")");
		
		addCode(indent + "isub");
	}

	//Exp e1,e2;
	public void visit(Times n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" * ");
		n.e2.accept(this);
		//System.out.print(")");
		
		addCode(indent + "imul");
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
		
		addCode(indent + "invokevirtual "+ n.e1.show() + "/" + n.i + "()I");
	}

	//Exp e1;
	//Identifier i;
	//Exp e2;
	public void visit(CallSimple n) {
		n.e1.accept(this);				
		n.i.accept(this);		
		n.e2.accept(this);			
		
		//new Fac().ComputeFac(10)   -> exp1.id(exp2)
		addCode(indent + "invokevirtual "+ n.e1.show() + "/" + n.i + "()I");
	}

	//Exp e1;
	//Identifier i;
	public void visit(CallMoreSimple n) {
		n.e1.accept(this);
		n.i.accept(this);
		
		if(n.e1 instanceof NewObject){
			NewObject newObject = (NewObject) n.e1;
			String returnType = symbolTree.getClass(newObject.i.s).getMethod(n.i.s).getType();
			currentType = returnType;
			//invokevirtual Clase/Metodo()TipoRetorno
			addCode(indent + "invokevirtual "+ newObject.i + "/" + n.i + "()"+currentType(returnType));
			addCode(indent + "istore 0");
		}			
	}

	//int i;
	public void visit(IntegerLiteral n) {
		addCode(indent + "ldc " + n.i);		
	}


	//String s;
	public void visit(IdentifierExp n) {
		//addCode("\n  ldc " + n.s);
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
		addCode(indent + "new "+n.i);
		addCode(indent + "dup");
		addCode(indent + "invokenonvirtual " + n.i + "/<init>()V");
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
		
		addCode(indent + "ior");
	}

	//Exp e1,e2;	
	public void visit(Divide n) {
		//System.out.print("(");
		n.e1.accept(this);
		//System.out.print(" / ");
		n.e2.accept(this);
		//System.out.print(")");	
		
		addCode(indent + "idiv");
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
		addCode("\n  .limit stack 5");
		addCode("\n  .limit locals 100");	
		
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
		addCode("\n  iload 0");  //Agrega el resultado de n.e al stack
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
		addCode("\n  .limit stack 5");
		addCode("\n  .limit locals 100");	
				
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
		addCode("\n  iload 0");  //Agrega el resultado de n.e al stack
		addCode("\n  ireturn"); //Retorna lo agregado al stack?	
		addCode("\n.end method");			

	}
	
	/* Retorna una cadena que contiene una declaración del constructor de una clase  */
	public String classConstructor(){
		String classConstructor = new String();
		
		classConstructor += "\n\n.method public <init>()V";
		classConstructor += "\n  aload_0";
		classConstructor += "\n  invokenonvirtual java/lang/Object/<init>()V";
		classConstructor += "\n  return";
		classConstructor += "\n.end method";
		
		return classConstructor;
	}
	
	public String currentType(String type){		
		String currentType = new String();
		if(type == "int"){
			currentType = "I";
		}
		return currentType;
	}
}
