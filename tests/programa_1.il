//Pedro Virissimo Scarelli
.assembly extern mscorlib {}
.assembly _codigo_objeto{}
.module _codigo_objeto.exe

.class public _UNICA{
   .method static public void _principal(){
.entrypoint
.locals(int64 i_basemenor)
.locals(int64 i_basemaior)
.locals(int64 i_altura)
.locals(float64 f_area)
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_basemaior
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_basemenor
call string [mscorlib] System.Console::ReadLine()
call int64 [mscorlib] System.Int64::Parse(string)
stloc i_altura
ldloc i_basemenor
conv.r8
ldloc i_basemaior
conv.r8
add
ldloc i_altura
conv.r8
ldc.i8 2
conv.r8
div
mul
stloc f_area
ldloc f_area
conv.r8
call void [mscorlib]System.Console::Write(float64)
ret
  }
}
