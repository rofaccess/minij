public class Main{
	//Suma suma;//No se admite VarDeclaration
	public static void main(String[] args){
		//suma = new Suma();//variable no declarada, tampoco se admite VarDeclaration
		//suma.print();//Statement no admite directamente una Expression
		System.out.println(new Suma().print());
	}
}

public class IO{
	
}

public class Suma extends IO{
	int res;
	public int print(){
		res = 10 * 6 - 2;
		System.out.println(res);
		return res;
	}
}

