package SymbolTable;

public class Class extends Category{	
		
	public Class(){
		setCategoryName("Class");
	}		

	@Override
	public void printCategory() {
		// TODO Auto-generated method stub
		System.out.println();
	}
	
	@Override
	public int length() {
		// TODO Auto-generated method stub
		return getCategoryName().length();
	}	
}
