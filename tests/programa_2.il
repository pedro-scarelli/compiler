//Pedro Virissimo Scarelli
.assembly extern mscorlib {}
.assembly _codigo_objeto{}
.module _codigo_objeto.exe

.class public _UNICA{
   .method static public void _principal(){
.entrypoint
.locals(int64 i_prestacoes)
.locals(int64 i_pagas)
.locals(float64 f_total)
.locals(float64 f_valor)
ldstr "qual o numero total de prestacoes: "
call void [mscorlib]System.Console::Write(string)
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_prestacoes
ldstr "quantas prestacoes voce pagou: "
call void [mscorlib]System.Console::Write(string)
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_pagas

ldstr "qual a o valor da prestacao:"
call void [mscorlib]System.Console::Write(string)
call string [mscorlib] System.Console::ReadLine()
call float64 [mscorlib] System.Double::Parse(string)
stloc f_valor
ldloc i_prestacoes
conv.r8
ldloc i_pagas
conv.r8
sub
ldloc f_valor
mul
stloc f_total
ldstr "saldo devedor "
call void [mscorlib]System.Console::Write(string)
ldloc f_total
call void [mscorlib]System.Console::Write(float64)
ret
  }
}
