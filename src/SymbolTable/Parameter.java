package SymbolTable;

public class Parameter extends Category{	
	private String type;
	
	public Parameter(String t){			
		type = t;
		setCategoryName("Parameter");
	}	
	
	public String getType(){
		return type;
	}
	
	public void setType(String t){
		type = t;
	}
	
	@Override
	public void printCategory() {
		// TODO Auto-generated method stub
		System.out.println();
	}	
}
