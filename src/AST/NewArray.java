package AST;
import AST.Visitor.Visitor;

public class NewArray extends Exp {
  public Exp e;
  
  public NewArray(Exp ae, int ln) {
    super(ln);
    e=ae; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

@Override
public String show() {
	// TODO Auto-generated method stub
	return "new int["+e.show()+"]";
}
}
