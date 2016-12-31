package AST;
import AST.Visitor.Visitor;

public class BlockEmpty extends Statement {    
	
	public BlockEmpty(int ln) {
        super(ln);
    }
    public void accept(Visitor v){
    	v.visit(this);
    };
}
