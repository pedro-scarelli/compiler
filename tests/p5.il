.assembly extern mscorlib {}
.assembly _codigo_objeto{}
.module _codigo_objeto.exe

.class public _UNICA{
.method static public void _principal(){
.entrypoint
.locals (int64 i_numeros)
.locals (int64 i_index)
.locals (int64 i_pivo)
.locals (int64 i_somatorio)
ldc.i8 0
conv.r8
conv.i8
stloc i_index
ldstr "quantos numeros: "
call void [mscorlib]System.Console::Write(string)
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_numeros
M0:
ldstr "digite um numero: "
call void [mscorlib]System.Console::Write(string)
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_pivo
ldloc i_pivo
conv.r8
conv.i8
stloc i_somatorio
M1:
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
brfalse M1
ldstr "somatorio: "
call void [mscorlib]System.Console::Write(string)
ldloc i_somatorio
conv.r8
conv.i8
call void [mscorlib]System.Console::Write(int64)
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
brtrue M0
ret
    }
}
