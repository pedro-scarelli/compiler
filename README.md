# Compilador de Código-Fonte para MSIL

Este projeto implementa um compilador completo (léxico, sintático e semântico) que lê um arquivo de código-fonte (`.txt`), gera um código-objeto em MSIL (`.il`) e, em seguida, permite a conversão para um executável (`.exe`) no Windows.

---

## 🚀 Funcionalidades

- **Análise Léxica**: Identifica tokens definidos pelas regras de expressões regulares.
- **Análise Sintática**: Valida a estrutura do programa de acordo com a gramática LL.
- **Análise Semântica**: Verifica coerência de tipos e regras semânticas.
- **Geração de Código MSIL**: Emite instruções IL compatíveis com o .NET/CLI.

---

## 📚 Gramática e Regras

O compilador foi construído com o GALS e segue a seguinte configuração:

#Options    
GenerateScanner = true  
GenerateParser = true  
Language = Java  
ScannerName = Lexico  
ParserName = Sintatico  
SemanticName = Semantico  
ScannerCaseSensitive = true  
ScannerTable = Compact  
Input = String  
Parser = LL  


#RegularDefinitions  

#Tokens    
pr: [a-z] [a-zA-Z]* // palavra reservada  
id:! (i_|f_|b_|s_) ([a-z][A-Z]? | [A-Z]) (([a-z] | [0-9]) [A-Z]?) // identificador  
cte: [1-9] [0-9]* | 0 // constante inteira  
cte_2: ([1-9][0-9]* | 0) , 0-9* // constante float  
ct: " ([^\n"%](%x))* " // constante string  


:! >@ \n [^@]* \n @< // comentário  


:[\n\s\t] // quebra de linha, espaço e tab  


// Palavras reservadas e símbolos  
delimiter tokens (main, read, write, etc.) e operadores lógicos, relacionais e aritméticos.

### Não-Terminais
<lista_steps>  <lista_id>        ...
### Produções Principais

```bnf
<program> ::= #100 main <lista_steps> end #101 ;
<lista_steps> ::= <instrucao> ";" <lista_steps1> ;
<instrucao> ::= <lista_id> <lista_id1> | <input> | <output> | <if> | <repeat> ;
...
```

Para a gramática completa, consulte o arquivo de configuração do GALS (grammar-rules.gals).
Link para download do GALS:  
https://gals.sourceforge.net/

---

## 💡 Como Usar

1. Crie um arquivo de código-fonte seguindo a gramática definida (por exemplo, programa.txt).

2. Execute o compilador:
   ```bash
   java -jar meu-compilador.jar programa.txt
   ```
3. O compilador gerará programa.il na mesma pasta do .txt.

4. No Windows, abra o Prompt de Comando e navegue até o diretório com o .il:
```bash
ilasm programa.il
```
5. Isso produzirá programa.exe. Para executar:
```bash
programa.exe
```

---

## 📝 Exemplo de Uso

### Arquivo de entrada (area.txt):
```bash
main
  i_lado, i_area;

  repeat
     read (i_lado);
     if i_lado < 0 || i_lado == 0
        writeln ("valor invalido");
     end;
  until i_lado > 0;
  i_area = i_lado * i_lado;
  write (i_area);

end
```
### Geração de .il:
```bash
.assembly extern mscorlib {}
.assembly _codigo_objeto{}
.module _codigo_objeto.exe

.class public _UNICA{
  .method static public void _principal(){
     .entrypoint
     .locals (int64 i_lado)
	 .locals (int64 i_area)
	 
	 // repeat
     L1:
	 call string [mscorlib]System.Console::ReadLine()
     call int64 [mscorlib]System.Int64::Parse(string)
     stloc i_lado
     
	 // if i_lado < 0 || i_lado == 0
	 ldloc i_lado
     conv.r8
     ldc.i8 0
     conv.r8 
     clt
     ldloc i_lado
     conv.r8
     ldc.i8 0
     conv.r8 
     ceq
     or
     brfalse L3
     
	 ldstr "valor invalido"
     call void [mscorlib]System.Console::WriteLine(string)
     br L2  
     L3:
	 L2:
	 
	 // until i_lado > 0
     ldloc i_lado
     conv.r8
     ldc.i8 0
     conv.r8 
     cgt
     brfalse L1        

     ldloc i_lado
     conv.r8
     ldloc i_lado
     conv.r8
     mul
     conv.i8
     stloc i_area
     ldloc i_area
     conv.r8
     conv.i8
     call void [mscorlib]System.Console::Write(int64)

     ret
  }
}
```
