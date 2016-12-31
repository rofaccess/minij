package AST;
import AST.Visitor.Visitor;

public class MainClassStatementList extends MainClass{
  public Identifier i1,i2;
  public StatementList sl;

  public MainClassStatementList(Identifier ai1, Identifier ai2, StatementList asl, int ln) {
    super(ln);
    i1=ai1; i2=ai2; sl=asl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}

