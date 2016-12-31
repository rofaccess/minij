package SymbolTree;

public class Parameter extends Category{	
	private String type;
	
	public Parameter(Symbol s, String t){			
		type = t;
		setSymbol(s,"Parameter");
	}	
	
	public String getType(){
		return type;
	}
	
	public void setType(String t){
		type = t;
	}
	
	@Override
	public void printCategory() {
		super.printCategory();
		
		int la = getCategoryName().length();
		while(la < 16){
			System.out.print(" ");
			la++;
		}
		
		System.out.println(type);
	}	
}
