.assembly extern mscorlib {}
.assembly _codigo_objeto{}
.module _codigo_objeto.exe

.class public _UNICA{
.method static public void _principal(){
.entrypoint
.locals (int64 i_mes)
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_mes
M0:
ldstr "valor invalido, digitar um valor entre 1 e 12: "
call void [mscorlib]System.Console::Write(string)
ldstr "\n"
call void [mscorlib]System.Console::Write(string)
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_mes
ldloc i_mes
conv.r8
ldc.i8 1
conv.r8
clt
ldloc i_mes
conv.r8
ldc.i8 12
conv.r8
cgt
or
brtrue M0
ldloc i_mes
conv.r8
ldc.i8 4
conv.r8
clt
brfalse M1
ldstr "primeiro trimestre"
call void [mscorlib]System.Console::Write(string)
br M0
M1:
ldloc i_mes
conv.r8
ldc.i8 7
conv.r8
clt
brfalse M1
ldstr "segundo trimestre"
call void [mscorlib]System.Console::Write(string)
br M0
M1:
ldloc i_mes
conv.r8
ldc.i8 10
conv.r8
clt
brfalse M1
ldstr "terceiro trimestre"
call void [mscorlib]System.Console::Write(string)
br M0
M1:
ldstr "quarto trimestre"
call void [mscorlib]System.Console::Write(string)
M0:
ret
    }
}
