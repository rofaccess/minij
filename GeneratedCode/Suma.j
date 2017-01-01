.source Suma.j
.class  public Suma
.super  java/lang/Object

; constructor necesita declararse siempre (explicitamente)
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

;suma Method
.method public suma()I
;stack size
.limit stack 6

;Number of local variable
.limit locals 5

;expresion algebraica 5*10-2
;       -
;     /   \
;    *     2
;   / \
;  5   10
; si recorre en post-orden, entonces 5,10,*,2,-
 ldc 5		;visito nodo 5, push 5 al stack
 ldc 10     ;visito nodo 10, push 10 al stack
 imul       ;visito nodo Times, imul
 ldc 2      ;visito nodo 2, push 2 al stack
 isub       ;visito nodo Minus, isub
 istore 0	;visito nodo Assign, local variable[0] = push stack

 getstatic java/lang/System/out Ljava/io/PrintStream;
 iload 0 ; para imprimir resultado, agregar res al stack y llamar a print
 invokevirtual java/io/PrintStream/println(I)V
 iload 0 ;agregar res al stack y retornar resultado
 ireturn
.end method