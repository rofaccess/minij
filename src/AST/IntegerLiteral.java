package AST;
import AST.Visitor.Visitor;

public class IntegerLiteral extends Exp {
  public String i;

  public IntegerLiteral(String ai, int ln) {
    super(ln);
    i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  
  public String show(){
	  return i;
  }
  
}
