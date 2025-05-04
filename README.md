# Compilador de Código-Fonte para MSIL

Este projeto implementa um compilador completo (léxico, sintático e semântico) que lê um arquivo de código-fonte (`.txt`), gera um código-objeto em MSIL (`.il`) e, em seguida, permite a conversão para um executável (`.exe`) no Windows.

---

## 🚀 Funcionalidades

- **Análise Léxica**: Identifica tokens definidos pelas regras de expressões regulares.
- **Análise Sintática**: Valida a estrutura do programa de acordo com a gramática definida.
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
pr: [a-z] [a-zA-Z]*
id:! (i_|f_|b_|s_) ([a-z][A-Z]? | [A-Z]) (([a-z] | [0-9]) [A-Z]?)
cte: [1-9] [0-9]* | 0
cte_2: ([1-9][0-9]* | 0) , 0-9*
ct: " ([^\n"%](%x))* "

:! >@ \n [^@]* \n @<

:[\n\s\t]

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
Link para download do GALS: https://ava3.furb.br/mod/resource/view.php?id=1027899

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

## 📝 Exemplo de Uso

### Arquivo de entrada (area.txt):

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

Geração de .il:

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
     ...
     ret
  }
}
