package AST;
import AST.Visitor.Visitor;

public class ProgramMainClass extends Program {  
	public MainClass m;

  public ProgramMainClass(MainClass am, int ln) {
    super(ln);
    m=am; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
