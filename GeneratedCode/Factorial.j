.source Factorial.j
.class public Factorial
.super java/lang/Object

.method public static main([Ljava/lang/String;)V
  .limit stack 5
  .limit locals 100
  new Test
  dup
  invokenonvirtual Test/<init>()V
  invokevirtual Test/getValue()I
  istore 0
  getstatic java/lang/System/out Ljava/io/PrintStream;
  iload 0
  invokevirtual java/io/PrintStream/println(I)V
  return
.end method