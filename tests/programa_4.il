//Pedro Virissimo Scarelli
.assembly extern mscorlib {}
.assembly _codigo_objeto{}
.module _codigo_objeto.exe

.class public _UNICA{
   .method static public void _principal(){
       .entrypoint
       .locals(int64 i_mes)
M2:
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_mes
ldloc i_mes 
conv.r8
ldc.i8 1
conv.r8
clt
brtrue M1
ldloc i_mes
conv.r8
ldc.i8 12
conv.r8
cgt
brtrue M1
ldloc i_mes
conv.r8
ldc.i8 4
conv.r8
clt
brtrue M3
ldloc i_mes
conv.r8
ldc.i8 7
conv.r8
clt
brtrue M4
ldloc i_mes
conv.r8
ldc.i8 10
conv.r8
clt
brtrue M5
ldstr "quarto trimestre"
call void [mscorlib]System.Console::Write(string)
br E1
M1:
ldstr "valor invalido, digitar um valor entre 1 e 12:\n"
call void [mscorlib]System.Console::Write(string)
br M2
M3:
ldstr "primeiro trimestre"
call void [mscorlib]System.Console::Write(string)
br E1
M4:
ldstr "segundo trimestre"
call void [mscorlib]System.Console::Write(string)
br E1
M5:
ldstr "terceiro trimestre"
call void [mscorlib]System.Console::Write(string)
E1:ret
  }
}