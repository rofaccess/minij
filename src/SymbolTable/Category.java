package SymbolTable;

public abstract class Category {
	private String categoryName;	
		
	public abstract void printCategory();
		
	public String getCategoryName(){
		return categoryName;
	}
	
	public void setCategoryName(String c){
		categoryName = c;
	}	
	
	public int length() {
		return getCategoryName().length();
	}
}
