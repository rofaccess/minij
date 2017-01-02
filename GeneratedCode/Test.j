.source Test.j
.class public Test
.super java/lang/Object

.method public <init>()V
  aload_0
  invokenonvirtual java/lang/Object/<init>()V
  return
.end method

.method public getValue()I
  .limit stack 5
  .limit locals 100
  ldc 1
  ldc 0
  isub
  ifgt Label1
  goto Label2
  Label1:
    ldc 2
    istore 0
    goto Label3
  Label2:
    ldc 3
    istore 0
  Label3:
  iload 0
  ireturn
.end method