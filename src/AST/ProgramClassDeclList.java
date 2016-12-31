package AST;
import AST.Visitor.Visitor;

public class ProgramClassDeclList extends Program {  
  public ClassDeclList cl;

  public ProgramClassDeclList(ClassDeclList acl, int ln) {
    super(ln);
    cl=acl; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
