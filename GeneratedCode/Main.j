.source Main.j
.class  public Main
.super  java/lang/Object

;Main Method
.method public static main([Ljava/lang/String;)V

	;stack size
	.limit stack 6

	;Number of local variable
	.limit locals 5
    ; create a new one of suma
    new Suma
    dup
    invokenonvirtual Suma/<init>()V
   
    ; now call my class method foo()
    invokevirtual Suma/suma()I 
	return 
.end method