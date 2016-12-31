package AST;
import AST.Visitor.Visitor;

public class VarDeclAssign extends VarDecl {
  public Type t;
  public Identifier i;
  public Exp e;
  
  public VarDeclAssign(Type at, Identifier ai, Exp ae, int ln) {
    super(ln);
    t=at; i=ai; e=ae;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
