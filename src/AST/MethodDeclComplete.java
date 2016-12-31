package AST;
import AST.Visitor.Visitor;

public class MethodDeclComplete extends MethodDecl {
  public Formal f1;
  public Formal f2;
  public FormalList fl;
  public VarDeclList vl;
  public StatementList sl;
  public Exp e;

  public MethodDeclComplete(Formal af1, Formal af2, FormalList afl, VarDeclList avl, 
                    StatementList asl, Exp ae, int ln) {
    super(ln);
    f1=af1; f2=af2; fl=afl; vl=avl; sl=asl; e=ae;
  }
 
  public void accept(Visitor v) {
    v.visit(this);
  }
}
