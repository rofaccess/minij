/* Este archivo tiene el mismo contenido que el archivo typecheckingerrors.txt */
public class Factorial{
    public static void main(String[] a){
    	System.out.println(new Fac().ComputeFac(10));
    }
}

public class Fac {
    public int ComputeFac(int num){
		int num_aux ;
		Test test;
		//variable con nombre duplicado (OK)
		SubTest test;
		//asignacion a variable no declarada (OK)
		no_declarado = 123;
		//asignacion object to int (OK)
		num_aux = test;
		//operacion object a un int (OK)
		num_aux = 10 + 5 * test;
		//Asignacion de tipo a subtipo (no es un error)
		test = new SubTest();
		//Asignacion metodo object a int (OK)
		num_aux = test.getValue();
		// llamada a metodo escondido
		num_aux = test.getValue(123);
		// llamada a metodo inexistente (OK)
		num_aux = test.getABC();
		// llamada a metodo inexistente con mas args
		num_aux = test.getValue(123, num_aux);
		if (num < 1)
			num_aux = 1 ;
		else 
			num_aux = num * (this.ComputeFac(num-1)) ;
		return num_aux ;
    }
}
public class Test{
	public Test getValue(){
		//error return type (OK)
		int[] a;
		return a;
	}
}
public class SubTest extends Test{
	
	public Test getValue(int arg){
		//error variable no declarada (OK)
		a = 10;
		//error return type (OK)
		return 10;
	}
}
