package AST;
import AST.Visitor.Visitor;

public class Dbecomes extends Exp {
  public Exp e1,e2;
  
  public Dbecomes(Exp ae1, Exp ae2, int ln) {
    super(ln);
    e1=ae1; e2=ae2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

@Override
public String show() {
	// TODO Auto-generated method stub
	return null;
}
  
}
