public class Factorial{
    public static void main(String[] a){
    	/* Falla en la siguiente linea porque no esta previsto */
    	//el VarDeclaration al inicio de un bloque main
    	//Fac f = new Fac();//No encaja con la gramatica
    	System.out.println(f.ComputeFac(10));
    }
}

public class Fac {

    public int ComputeFac(int num){
		int num_aux ;
		if (num < 1)
		    num_aux = 1 ;
		else 
		    num_aux = num * (this.ComputeFac(num-1)) ;
		return num_aux ;
    }

}
