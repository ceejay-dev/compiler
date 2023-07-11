package analisador.lexico;

//Enum com significado de cada lexema
public enum Token {
    Tok_COMENTARIO,
    Tok_OPERADOR_ATRIBUICAO_MODULAR,
    Tok_OPERADOR_DIVISAO,
    Tok_OPERADOR_RELACIONAL,
    Tok_OPERADOR_DIFERENTE,
    Tok_OPERADOR_COM_INCREMENTO,
    Tok_OPERADOR_COM_DECREMENTO,
    Tok_OPERADOR_SOMA,
    Tok_OPERADOR_SUBTRACAO,
    Tok_OPERADOR_ATRIBUICAO,
    Tok_OPERADOR_IGUAL,
    Tok_OPERADOR_RESTO,
    Tok_DELIMITADOR,
    Tok_ABRE_DELIMITADOR,
    Tok_FECHA_DELIMITADOR,
    Tok_OPERADOR_PRODUTO,
    Tok_ERRO,
    Tok_QUEBRA_LINHA,   
    Tok_IDENTIFICADOR,
    Tok_NUMERO_INTEIRO,
    Tok_NUMERO_REAL,
    Tok_PALAVRA_RESERVADA,
    Tok_MAIOR,
    Tok_MENOR,
    Tok_MAIOR_IGUAL,
    Tok_MENOR_IGUAL,
    Tok_STRING,
    Tok_CARACTERE,
    Tok_FINITO
}