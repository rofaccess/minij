package AST;
import AST.Visitor.Visitor;

public class IdentifierExp extends Exp {
	public String s;
	public IdentifierExp(String as, int ln) { 
		super(ln);
		s=as;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String s() {
		return s;
	}

	@Override
	public String show() {
		// TODO Auto-generated method stub
		return s;
	}	
}
