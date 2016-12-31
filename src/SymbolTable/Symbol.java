package SymbolTable;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Symbol {
	private String identifier;
	private String scope;
	private Category category;
	private Integer lineNumber;

	public Symbol(String id, String sc,Category ca, Integer ln){
		setSymbol(id,sc,ca,ln);
	}
	
	public void printSymbol(){			
		System.out.print(lineNumber);
		int la = lineNumber.toString().length();
		while(la < 8){
			System.out.print(" ");
			la++;
		}
		
		System.out.print(identifier);
		la = identifier.length();
		while(la < 16){
			System.out.print(" ");
			la++;
		}
		
		System.out.print(scope);		
		la = scope.length();
		while(la < 16){
			System.out.print(" ");
			la++;
		}
		
		System.out.print(category.getCategoryName());
		
		category.printCategory();
	}	

	/* Fuente: http://www.dosideas.com/noticias/java/758-java-hashing.html */	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Symbol) {
			Symbol sk = (Symbol)obj;
			return sk.identifier.equals(identifier) && 
				   sk.scope.equals(scope) && 
				   sk.category.getCategoryName().equals(category.getCategoryName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(83, 7)
		.append(identifier)
		.append(scope)
		.append(category.getCategoryName())
		.toHashCode();
	}
	
	public void setSymbol(String id, String sc,Category ca,Integer ln){
		setIdentifier(id);
		setScope(sc);
		setCategory(ca);
		setLn(ln);
	}

	public String getIdentifier(){
		return identifier;
	}

	public void setIdentifier(String id){
		identifier = id;
	}

	public String getScope(){
		return scope;
	}

	public void setScope(String sc){
		scope = sc;
	}
	
	public Category getCategory(){
		return category;
	}

	public void setCategory(Category ca){
		category = ca;
	}
	
	public int getLn() {
		return lineNumber;
	}

	public void setLn(int lineNumber) {
		this.lineNumber = lineNumber;
	}	
}
