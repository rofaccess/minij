package AST.Visitor;

import AST.*;

public interface Visitor {
  // Display added for toy example language.  Not used in MiniJ AST
  public void visit(Display n);
  public void visit(Program n);
  public void visit(MainClass n);
  public void visit(ClassDeclSimple n);
  public void visit(ClassDeclExtends n);
  public void visit(VarDecl n);
  public void visit(MethodDecl n);
  public void visit(Formal n);
  public void visit(IntArrayType n);
  public void visit(IntegerType n);
  public void visit(IdentifierType n);
  public void visit(Block n);
  public void visit(If n);
  public void visit(While n);
  public void visit(Print n);
  public void visit(Assign n);
  public void visit(ArrayAssign n);
  public void visit(And n);
  public void visit(Less n);
  public void visit(Major n);
  public void visit(Plus n);
  public void visit(Minus n);
  public void visit(Times n);
  public void visit(ArrayLookup n);
  public void visit(ArrayLength n);
  public void visit(Call n);
  public void visit(IntegerLiteral n);
  public void visit(IdentifierExp n);
  public void visit(This n);
  public void visit(NewArray n);
  public void visit(NewObject n);
  public void visit(Identifier n);
  public void visit(ProgramComplete n);
  public void visit(ProgramClassDeclList n);
  public void visit(ProgramMainClass n);
  public void visit(MainClassEmpty n);
  public void visit(MainClassStatementList n);
  public void visit(BlockEmpty n);
  public void visit(Dbecomes n);
  public void visit(Distinct n);
  public void visit(Or n);  
  public void visit(Divide n);
  public void visit(CallSimple n);
  public void visit(CallMoreSimple n);
  public void visit(VarDeclSimple n);
  public void visit(VarDeclAssign n);
  public void visit(MethodDeclComplete n);
  public void visit(MethodDeclSimple n);
}