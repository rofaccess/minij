package AST;
import AST.Visitor.Visitor;

public abstract class VarDecl extends ASTNode {  
  public VarDecl(int ln) {
    super(ln);
  }

  public abstract void accept(Visitor v);
}
