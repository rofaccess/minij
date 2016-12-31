package AST;
import AST.Visitor.Visitor;

public class VarDeclSimple extends VarDecl {
  public Type t;
  public Identifier i;
  
  public VarDeclSimple(Type at, Identifier ai, int ln) {
    super(ln);
    t=at; i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
