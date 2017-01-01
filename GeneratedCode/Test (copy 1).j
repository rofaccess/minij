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
  ldc 10
  istore 0
  iload 0
  ireturn
.end method