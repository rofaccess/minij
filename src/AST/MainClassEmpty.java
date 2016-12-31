package AST;
import AST.Visitor.Visitor;

public class MainClassEmpty extends MainClass{
  public Identifier i1,i2;

  public MainClassEmpty(Identifier ai1, Identifier ai2, int ln) {
    super(ln);
    i1=ai1; i2=ai2; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}

