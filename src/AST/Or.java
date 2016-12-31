package AST;
import AST.Visitor.Visitor;

public class Or extends Exp {
  public Exp e1,e2;
  
  public Or(Exp ae1, Exp ae2, int ln) {
    super(ln);
    e1=ae1; e2=ae2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

@Override
public String show() {
	// TODO Auto-generated method stub
	return e1.show() + "||" +e2.show();
}
}
