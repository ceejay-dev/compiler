package analisador.lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import analisador.lexico.Token;

public class Analex {

    int estado = 0;
    private String lexema = "";
    public ArrayList<Analize> codigos = new ArrayList();
    public Analize simbolo = new Analize();
    private ArrayList<String> lexemas = new ArrayList();

    public Analex() {
        Scanner ficheiro;
        String fileName = "lexemas.txt";
        try {
            ficheiro = new Scanner(new File(fileName));
            while (ficheiro.hasNextLine()) {
                StringTokenizer stoken = new StringTokenizer(ficheiro.next());
                while (stoken.hasMoreTokens()) {
                    lexemas.add(stoken.nextToken());
                }
            }
            ficheiro.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public ArrayList <Analize> lerFicheiro() throws IOException {
        int contadorDeLinha = 1;
        try {
            try (BufferedReader ficheiro = new BufferedReader(new FileReader("codigo-fonte.txt"))) {
                String token = ficheiro.readLine();
                while (token != null) {
                    this.lerCaractere(token, contadorDeLinha);
                    contadorDeLinha++;
                    token = ficheiro.readLine();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Analex.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return codigos;

    }

    public void lerCaractere(String linha, int contadorDeLinha) {
        Token token = null;
        String plica = "'";
        for (int i = 0; i < linha.length(); i++) {
            switch (estado) {
                case 0: {
                    if (linha.charAt(i) == ' ' || linha.charAt(i) == '\t') {
                        estado = 0;
                    } else if (linha.charAt(i) >= 'A' && linha.charAt(i) <= 'Z' || (linha.charAt(i) >= 'a' && linha.charAt(i) <= 'z') || linha.charAt(i) == '_') {
                        lexema += linha.charAt(i);
                        estado = 1;

                    } else if (linha.charAt(i) >= '0' && linha.charAt(i) <= '9') {
                        lexema += linha.charAt(i);
                        estado = 3;
                        linha += " ";
                    } else if (linha.charAt(i) == '"') {
                        lexema += linha.charAt(i);
                        estado = 37;
                        linha += " ";
                    } else if (linha.charAt(i) == '+') {
                        lexema += linha.charAt(i);
                        estado = 8;
                        linha += " ";
                    } else if (linha.charAt(i) == '-') {
                        lexema += linha.charAt(i);
                        estado = 12;
                        linha += " ";
                    } else if (linha.charAt(i) == '=') {
                        lexema += linha.charAt(i);
                        estado = 16;
                        linha += " ";
                    } else if (linha.charAt(i) == '!') {
                        lexema += linha.charAt(i);
                        estado = 19;
                        linha += " ";
                    } else if (linha.charAt(i) == '>') {
                        lexema += linha.charAt(i);
                        estado = 22;
                        linha += " ";
                    } else if (linha.charAt(i) == '<') {
                        lexema += linha.charAt(i);
                        estado = 25;
                        linha += " ";
                    } else if (linha.charAt(i) == '%') {
                        lexema += linha.charAt(i);
                        estado = 28;
                        linha += " ";
                    } else if (linha.charAt(i) == '&') {
                        lexema += linha.charAt(i);
                        estado = 31;
                        linha += " ";
                    } else if (linha.charAt(i) == '*') {
                        lexema += linha.charAt(i);
                        estado = 34;
                        linha += " ";
                    } else if (linha.charAt(i) == '/') {
                        lexema += linha.charAt(i);
                        estado = 39;
                        linha += " ";
                    } else if (linha.charAt(i) == plica.charAt(0)) {
                        lexema += linha.charAt(i);
                        estado = 47;
                        linha += " ";
                    } else if (linha.charAt(i) == '.') {
                        lexema += linha.charAt(i);
                        estado = 50;
                        i--;
                    } else if (linha.charAt(i) == ';') {
                        lexema += linha.charAt(i);
                        estado = 51;
                        i--;
                    } else if (linha.charAt(i) == ':') {
                        lexema += linha.charAt(i);
                        estado = 52;
                        i--;
                    } else if (linha.charAt(i) == '?') {
                        lexema += linha.charAt(i);
                        estado = 53;
                        linha += " ";
                        i--;
                    } else if (linha.charAt(i) == '(') {
                        lexema += linha.charAt(i);
                        estado = 54;
                        linha += " ";
                        i--;
                    } else if (linha.charAt(i) == ')') {
                        lexema += linha.charAt(i);
                        estado = 55;
                        linha += " ";
                        i--;
                    } else if (linha.charAt(i) == '[') {
                        lexema += linha.charAt(i);
                        estado = 56;
                        linha += " ";
                        i--;
                    } else if (linha.charAt(i) == ']') {
                        lexema += linha.charAt(i);
                        estado = 57;
                        linha += " ";
                        i--;
                    } else if (linha.charAt(i) == '{') {
                        lexema += linha.charAt(i);
                        estado = 58;
                        linha += " ";
                        i--;
                    } else if (linha.charAt(i) == '}') {
                        lexema += linha.charAt(i);
                        estado = 59;
                        linha += " ";
                        i--;
                    } else if (linha.charAt(i) == ',') {
                        lexema += linha.charAt(i);
                        estado = 60;
                        i--;
                    } else if (linha.charAt(i) == '#') {
                        lexema += linha.charAt(i);
                        estado = 61;
                        i--;
                    } else if (linha.charAt(i) == '|') {
                        lexema += linha.charAt(i);
                        estado = 62;
                        linha += " ";
                    }
                    break;
                }
                case 1: {
                    while (i < linha.length() && (linha.charAt(i) >= 'A' && linha.charAt(i) <= 'Z' || (linha.charAt(i) >= 'a' && linha.charAt(i) <= 'z') || linha.charAt(i) == '_' || linha.charAt(i) >= '0' && linha.charAt(i) <= '9') && linha.charAt(i) - 1 > i) {
                        lexema += linha.charAt(i);
                        i++;
                    }
                    estado = 2;
                    i -= 2;
                    break;
                }
                case 2: {
                    boolean condicao1 = verificaPalavra(lexema);
                    if (condicao1 == true) {
                        simbolo = new Analize(token.Tok_PALAVRA_RESERVADA, lexema, contadorDeLinha);
                        codigos.add(simbolo);

                    } else {
                        simbolo = new Analize(token.Tok_IDENTIFICADOR, lexema, contadorDeLinha);
                        codigos.add(simbolo);

                    }
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 3: {
                    while (linha.charAt(i) >= '0' && linha.charAt(i) <= '9' && linha.charAt(i) - 1 > i) {
                        lexema += linha.charAt(i);
                        i++;
                    }
                    if (linha.charAt(i) == '.') {
                        lexema += linha.charAt(i);
                        estado = 5;
                    } else {
                        i--;
                        estado = 4;
                    }
                    break;
                }
                case 4: {
                    if (linha.length() != i) {
                        i--;
                    }
                    simbolo = new Analize(token.Tok_NUMERO_INTEIRO, lexema, contadorDeLinha);
                    codigos.add(simbolo);

                    estado = 0;
                    lexema = "";
                    break;

                }
                case 5: {
                    if (linha.charAt(i) != ' ') {
                        if (linha.charAt(i) >= '0' && linha.charAt(i) <= '9') {
                            lexema += linha.charAt(i);
                            estado = 6;
                        } else {
                            estado = 65;
                        }
                    }

                    break;
                }

                case 6: {
                    while (i < linha.length() - 1 && linha.charAt(i) >= '0' && linha.charAt(i) <= '9') {
                        lexema += linha.charAt(i);
                        i++;
                    }
                    i--;
                    simbolo = new Analize(token.Tok_NUMERO_REAL, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    lexema = "";
                    estado = 0;
                    break;
                }
                case 8: {
                    if (linha.charAt(i) == '+' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 9;
                    } else if (linha.charAt(i) == '=' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 10;
                    } else {
                        estado = 11;
                    }
                    i--;
                    break;
                }
                case 9: {
                    simbolo = new Analize(token.Tok_OPERADOR_COM_INCREMENTO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    lexema = "";
                    estado = 0;
                    break;
                }
                case 10: {
                    simbolo = new Analize(token.Tok_OPERADOR_ATRIBUICAO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    lexema = "";
                    estado = 0;
                    i--;
                    break;
                }
                case 11: {
                    simbolo = new Analize(token.Tok_OPERADOR_SOMA, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    lexema = "";
                    estado = 0;
                    i--;
                    break;
                }
                case 12: {
                    if (linha.charAt(i) == '-' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 13;

                    } else if (linha.charAt(i) == '=' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 14;
                    } else {
                        estado = 15;

                    }
                    i--;
                    break;
                }
                case 13: {
                    simbolo = new Analize(token.Tok_OPERADOR_COM_DECREMENTO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    lexema = "";
                    estado = 0;
                    break;
                }
                case 14: {
                    simbolo = new Analize(token.Tok_OPERADOR_ATRIBUICAO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    lexema = "";
                    estado = 0;
                    i--;
                    break;
                }
                case 15: {
                    simbolo = new Analize(token.Tok_OPERADOR_SUBTRACAO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    lexema = "";
                    estado = 0;
                    i--;
                    break;
                }
                case 16: {
                    if (linha.charAt(i) == '=' && linha.length() != i) {
                        estado = 17;
                        lexema += linha.charAt(i);
                    } else {

                        estado = 18;
                        if (linha.length() - 1 != i) {
                            i--;
                        }
                    }
                    i--;
                    break;
                }
                case 17: {
                    simbolo = new Analize(token.Tok_OPERADOR_IGUAL, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 18: {
                    simbolo = new Analize(token.Tok_OPERADOR_ATRIBUICAO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }

                case 19: {
                    if (linha.charAt(i) == '=' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 20;
                    } else {
                        estado = 21;
                    }
                    i--;
                    break;
                }
                case 20: {
                    simbolo = new Analize(token.Tok_OPERADOR_DIFERENTE, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 21: {
                    simbolo = new Analize(token.Tok_OPERADOR_RELACIONAL, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }

                case 22: {
                    if (linha.charAt(i) == '=' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 23;
                    } else {
                        estado = 24;
                        i--;
                    }
                    i--;
                    break;
                }
                case 23: {
                    simbolo = new Analize(token.Tok_MAIOR_IGUAL, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 24: {
                    simbolo = new Analize(token.Tok_MAIOR_IGUAL, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 25: {
                    if (linha.charAt(i) == '=' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 26;
                    } else {
                        estado = 27;
                        i--;
                    }
                    i--;
                    break;
                }
                case 26: {
                    simbolo = new Analize(token.Tok_MENOR_IGUAL, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 27: {
                    simbolo = new Analize(token.Tok_MENOR_IGUAL, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 28: {
                    if (linha.charAt(i) == '=' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 29;
                    } else {
                        estado = 30;
                    }
                    i--;
                    break;
                }
                case 29: {
                    simbolo = new Analize(token.Tok_OPERADOR_ATRIBUICAO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 30: {
                    simbolo = new Analize(token.Tok_OPERADOR_RESTO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 31: {
                    if (linha.charAt(i) == '&' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 32;
                    } else {
                        estado = 33;
                        i--;
                    }
                    i--;
                    break;
                }
                case 32: {
                    simbolo = new Analize(token.Tok_OPERADOR_RELACIONAL, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 33: {
                    simbolo = new Analize(token.Tok_OPERADOR_RELACIONAL, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                //inicio
                case 34: {
                    if (linha.charAt(i) == '=' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 35;
                    } else {
                        estado = 36;
                    }
                    i--;
                    break;
                }
                case 35: {
                    simbolo = new Analize(token.Tok_OPERADOR_ATRIBUICAO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 36: {
                    simbolo = new Analize(token.Tok_OPERADOR_PRODUTO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                //fim
                case 37: {
                    while (linha.charAt(i) != '"' && linha.length() - 1 > i) {
                        lexema += linha.charAt(i);
                        i++;
                    }
                    if (linha.charAt(i) == '"') {
                        i--;
                        estado = 38;
                    }
                    break;
                }
                case 38: {
                    lexema += linha.charAt(i);
                    simbolo = new Analize(token.Tok_STRING, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 39: {
                    if (linha.charAt(i) == '/') {
                        lexema += linha.charAt(i);
                        estado = 41;
                    } else if (linha.charAt(i) == '=') {
                        lexema += linha.charAt(i);
                        estado = 43;
                    } else if (linha.charAt(i) == '*' && linha.length() - 1 > i) {
                        lexema += linha.charAt(i);
                        estado = 44;
                        i--;
                    } else {
                        estado = 40;
                        if (linha.length() != i) {
                            i--;
                        }
                    }
                    //i--;
                    break;
                }
                case 40: {
                    simbolo = new Analize(token.Tok_OPERADOR_DIVISAO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 41: {
                    while (linha.charAt(i) != '\n') {
                        lexema += linha.charAt(i);
                        if (i != linha.length() - 1) {
                            i++;
                        } else {
                            break;
                        }
                    }
                    i--;
                    estado = 42;
                    break;
                }
                case 42: {
                    simbolo = new Analize(token.Tok_COMENTARIO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 43: {
                    simbolo = new Analize(token.Tok_OPERADOR_ATRIBUICAO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 44: {

                    while (linha.charAt(i) != '*' && linha.length() - 1 > i) {
                        lexema += linha.charAt(i);
                        i++;
                    }

                    estado = 45;

                    break;
                }
                case 45: {

                    while (linha.charAt(i) == '*' && linha.length() - 1 > i) {
                        lexema += linha.charAt(i);
                        i++;
                    }

                    if (linha.charAt(i) != '*' && linha.charAt(i) != '/') {
                        estado = 44;
                    } else {
                        estado = 46;
                        i--;
                    }
                    break;
                }

                case 46: {
                    simbolo = new Analize(token.Tok_COMENTARIO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 47: {
                    if (linha.charAt(i) != plica.charAt(0)) {
                        lexema += linha.charAt(i);
                        estado = 48;
                    }
                    break;
                }
                case 48: {
                    if (linha.charAt(i) == plica.charAt(0)) {
                        estado = 49;
                        i--;
                    }
                    break;
                }
                case 49: {
                    simbolo = new Analize(token.Tok_CARACTERE, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 50: {
                    simbolo = new Analize(token.Tok_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 51: {
                    simbolo = new Analize(token.Tok_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 52: {
                    simbolo = new Analize(token.Tok_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 53: {
                    simbolo = new Analize(token.Tok_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 54: {
                    simbolo = new Analize(token.Tok_ABRE_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 55: {
                    simbolo = new Analize(token.Tok_FECHA_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 56: {
                    simbolo = new Analize(token.Tok_ABRE_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 57: {
                    simbolo = new Analize(token.Tok_FECHA_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 58: {
                    simbolo = new Analize(token.Tok_ABRE_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 59: {
                    simbolo = new Analize(token.Tok_FECHA_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 60: {
                    simbolo = new Analize(token.Tok_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 61: {
                    simbolo = new Analize(token.Tok_DELIMITADOR, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 62: {
                    if (linha.charAt(i) == '|' && linha.length() != i) {
                        lexema += linha.charAt(i);
                        estado = 63;
                    } else {
                        estado = 64;
                    }
                    i--;
                    break;
                }
                case 63: {
                    simbolo = new Analize(token.Tok_OPERADOR_RELACIONAL, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }
                case 64: {
                    // simbolo = new Simbolo(lexema, "OPERADOR DE DIVISÃO", contadorDeLinha);
                    simbolo = new Analize(token.Tok_OPERADOR_DIVISAO, lexema, contadorDeLinha);
                    codigos.add(simbolo);
                    estado = 0;
                    lexema = "";
                    break;
                }

            }
            if (estado == 45) {
                lexema += '\n';
            }
        }
    }

    public boolean verificaPalavra(String palavra) {
        boolean condicao = false;
        if (lexemas.contains(palavra)) {
            condicao = true;
        }
        return condicao;
    }

    //Método para imprimir todos os tokens (é um método de verificação do funcionamento analex utilizado dentro Anasint)
    public void imprimir(ArrayList<Analize> lex) {
        for (Analize analize : lex) {
            System.out.println("==========================================================================================================================================================================================================================================");
            System.out.println("LINHA : " + analize.linhaCodigo + " TOKEN : " + analize.token + " LEXEMA : " + analize.lexema);
            System.out.println("==========================================================================================================================================================================================================================================");
        }
    }
    
        //Função que recebe todo o token analisado com os seus respectivos atributos
    public ArrayList analex() throws IOException {
        ArrayList<Analize> code = new ArrayList();

        Token token = null;

        Analize lex = new Analize(token.Tok_IDENTIFICADOR, " ", 1);
        do {
            //lex = this.lerCaractere();
            code.add(lex);

        } while (lex.getToken() != token.Tok_FINITO);

        return code;
    }
}
