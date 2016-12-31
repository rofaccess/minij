package AST;
import AST.Visitor.Visitor;

public class Call extends Exp {
  public Exp e1;
  public Identifier i;
  public Exp e2;
  public ExpList el;
  
  public Call(Exp ae1, Identifier ai, Exp ae2, ExpList ael, int ln) {
    super(ln);
    e1=ae1; i=ai; e2=ae2; el=ael;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

@Override
public String show() {
	// TODO Auto-generated method stub
	return e1.show()+"."+i.s+"("+e2.show()+el.show()+")";
}
 
}
