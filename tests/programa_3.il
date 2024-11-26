//Pedro Virissimo Scarelli
.assembly extern mscorlib {}
.assembly _codigo_objeto{}
.module _codigo_objeto.exe

.class public _UNICA{
   .method static public void _principal(){
.entrypoint
.locals(int64 i_nascimento)
.locals(int64 i_anoatual)
.locals(int64 i_idade)
ldc.i8 2024
stloc i_anoatual
ldstr "digite seu ano de nascimento: "
call void [mscorlib]System.Console::Write(string)
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_nascimento
ldloc i_anoatual
conv.r8
ldloc i_nascimento
conv.r8
sub
conv.i8
stloc i_idade
ldloc i_idade
conv.r8
ldc.i8 13
conv.r8
clt
brtrue M1
ldloc i_idade
conv.r8
ldc.i8 20
conv.r8
clt
brtrue M2
ldstr "senior"
call void [mscorlib]System.Console::Write(string)
br E1
M1:
ldstr "pre-mirim a petiz"
call void [mscorlib]System.Console::Write(string)
br E1
M2:
ldstr "infantil a junior"
call void [mscorlib]System.Console::Write(string)
E1:ret
  }
}
