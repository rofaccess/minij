.source Factorial.j
.class public Factorial
.super java/lang/Object

.method public static main([Ljava/lang/String;)V
  .limit stack 10
  .limit locals 5
  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc "Hola Mundo"

  invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc 5

  invokevirtual java/io/PrintStream/println(I)V

  ldc 5
  ldc 5
  imul
  istore 6

  iload 6 ; para imprimir resultado, agregar res al stack y llamar a print
  getstatic java/lang/System/out Ljava/io/PrintStream;
  swap
  invokevirtual java/io/PrintStream/println(I)V

  return
.end method

.method public static print(I)V
  .limit locals 5
  .limit stack 5
  iload 0
  getstatic java/lang/System/out Ljava/io/PrintStream;
  swap
  invokevirtual java/io/PrintStream/print(I)V
  return
.end method