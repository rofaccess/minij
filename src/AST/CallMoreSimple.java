package AST;
import AST.Visitor.Visitor;

public class CallMoreSimple extends Exp {
  public Exp e1;
  public Identifier i;
  
  public CallMoreSimple(Exp ae1, Identifier ai, int ln) {
    super(ln);
    e1=ae1; i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

@Override
public String show() {
	// TODO Auto-generated method stub
	return e1.show()+"."+i.s+"()";
}
  
}
