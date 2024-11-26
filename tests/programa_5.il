//Pedro Virissimo Scarelli
.assembly extern mscorlib {}
.assembly _codigo_objeto{}
.module _codigo_objeto.exe

.class public _UNICA{
   .method static public void _principal(){
       .entrypoint
       .locals(int64 i_numeros, int64 i_index, int64 i_pivo, int64 i_somatorio)
ldc.i8 0
stloc i_index
ldstr "quantos numeros: "
call void [mscorlib]System.Console::Write(string)
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_numeros
M2:
ldstr "digite um numero: "
call void [mscorlib]System.Console::Write(string)
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_pivo
ldloc i_pivo
stloc i_somatorio
M3:
ldloc i_pivo
conv.r8
ldc.i8 1 
conv.r8
sub
conv.i8
stloc i_pivo
ldloc i_somatorio
conv.r8
ldloc i_pivo
conv.r8
add
conv.i8
stloc i_somatorio
ldloc i_pivo
conv.r8 
ldc.i8 0
conv.r8
ceq
brfalse M3
ldstr "somatorio: "
call void [mscorlib]System.Console::Write(string)
ldloc i_somatorio
call void [mscorlib]System.Console::Write(int64)
ldstr "\n"
call void [mscorlib]System.Console::Write(string)
ldloc i_index
conv.r8
ldc.i8 1
conv.r8
add
conv.i8
stloc i_index
ldloc i_index
conv.r8
ldloc i_numeros
conv.r8
clt
brtrue M2
E1:ret
  }
}