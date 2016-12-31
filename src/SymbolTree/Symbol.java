package SymbolTree;

public class Symbol {
	private String identifier;
	private Category scope;
	private String categoryName;
	private Integer lineNumber;

	public Symbol(String id, Category sc,Integer ln) {
		setSymbol(id, sc, ln);
	}

	public void printSymbol() {
		System.out.print(lineNumber);
		int la = lineNumber.toString().length();
		while (la < 8) {
			System.out.print(" ");
			la++;
		}

		System.out.print(identifier);
		la = identifier.length();
		while (la < 16) {
			System.out.print(" ");
			la++;
		}		

		if (scope != null){
			System.out.print(scope.getIdentifier());
			la = scope.getIdentifier().length();
		}else{
			System.out.print("null");
			la = 4;
		}

		while (la < 16) {
			System.out.print(" ");
			la++;
		}

		System.out.print(categoryName);
	}

	public void setSymbol(String id, Category sc, Integer ln) {
		setIdentifier(id);
		setScope(sc);
		setLn(ln);
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String id) {
		identifier = id;
	}

	public Category getScope() {
		return scope;
	}

	public void setScope(Category sc) {
		scope = sc;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String cn) {
		categoryName = cn;
	}

	public int getLn() {
		return lineNumber;
	}

	public void setLn(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
