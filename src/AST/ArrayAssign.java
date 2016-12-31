package AST;
import AST.Visitor.Visitor;

public class ArrayAssign extends Statement {
  public Identifier i;
  public Exp e1,e2;

  public ArrayAssign(Identifier ai, Exp ae1, Exp ae2, int ln) {
    super(ln);
    i=ai; e1=ae1; e2=ae2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  public Exp e1(){
	  return e1;
  }
  
  public Exp e2(){
	  return e2;
  }
  
  public Identifier i(){
	  return i;
  }
}

