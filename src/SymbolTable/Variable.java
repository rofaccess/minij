package SymbolTable;

import AST.Exp;

public class Variable extends Category{	
	private String type;
	private Exp exp;
	
	public Variable(String t, Exp e){			
		type = t;
		exp = e;
		setCategoryName("Variable");
	}	
	
	@Override
	public void printCategory() {
		// TODO Auto-generated method stub
		
		int la = getCategoryName().length();
		while(la < 16){
			System.out.print(" ");
			la++;
		}
		
		System.out.print(type);
		
		la = type.length();
		while(la < 16){
			System.out.print(" ");
			la++;
		}
		
		if(exp!=null) System.out.println(exp.show());
		else System.out.println("null");
	}	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Exp getExp() {
		return exp;
	}

	public void setExp(Exp exp) {
		this.exp = exp;
	}
}
