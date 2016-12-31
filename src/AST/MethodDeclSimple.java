package AST;
import AST.Visitor.Visitor;

public class MethodDeclSimple extends MethodDecl {
	public Formal f1;	
	public VarDeclList vl;
	public StatementList sl;
	public Exp e;

	public MethodDeclSimple(Formal af1, VarDeclList avl, 
	                    StatementList asl, Exp ae, int ln) {
	    super(ln);
	    f1=af1; vl=avl; sl=asl; e=ae;
	  }
 
  public void accept(Visitor v) {
    v.visit(this);
  }
}
