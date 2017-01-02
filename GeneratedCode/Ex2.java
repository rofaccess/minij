public class Factorial{
    public static void main(String[] a){
      System.out.println(new Test().getValue());
    }
}

public class Test{
  public int getValue(){
    int res;
    if(1 < 0){
      res = 2;
    }else{
      res = 3;
    }
    return res;
  }
}