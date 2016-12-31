package AST;
import AST.Visitor.Visitor;

public class ProgramComplete extends Program {
  public MainClass m;
  public ClassDeclList cl;

  public ProgramComplete(MainClass am, ClassDeclList acl, int ln) {
    super(ln);
    m=am; cl=acl; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
