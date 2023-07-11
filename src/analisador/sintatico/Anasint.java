package analisador.sintatico;

import analisador.lexico.*;
import static analisador.lexico.Token.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Anasint {

    public int delimiterController = 0;

    //Variáveis públicas e constantes
    public int controle = 0;
    public int contErros = 0;
    public ArrayList<String> tipos = new ArrayList();
    public ArrayList<String> modifiers = new ArrayList();
    public ArrayList<String> routes = new ArrayList();
    public static final String ORIGINAL = "\033[0m";
    public static final String VERMELHO = "\033[0;31m";
    public static final String VERDE = "\033[0;32m";
    public String typeVerify = "";
    public Token tokenVerify = null;

    //Construtor usado para carregar todos os tipos de dados e modificadores de acesso sem que instanciar a classe
    public Anasint() {
        tipos = new ArrayList();
        modifiers = new ArrayList();
        routes = new ArrayList();

        tipos.add("boolean");
        tipos.add("byte");
        tipos.add("short");
        tipos.add("int");
        tipos.add("long");
        tipos.add("float");
        tipos.add("double");
        tipos.add("char");
        tipos.add("Interface");
        tipos.add("ArrayList");
        tipos.add("Enum");
        tipos.add("String");
        tipos.add("Integer");
        tipos.add("Double");
        tipos.add("Float");
        tipos.add("Character");
        tipos.add("Boolean");
        modifiers.add("private");
        modifiers.add("static");
        modifiers.add("protected");
        modifiers.add("public");
        modifiers.add("static ");
        modifiers.add("abstract");
        modifiers.add("final");
        modifiers.add("native");
        modifiers.add("synchronized");
        modifiers.add("transient");
        modifiers.add("volatile");
        modifiers.add("strictfp");
    }

    //Método de impressão dos erros 
    public void anasint(ArrayList<Analize> code) throws FileNotFoundException {
        //routes.add("function");

        this.readPackage(code);
        this.readEstruturas(code);
        System.out.println("NÚMERO DE ERROS É IGUAL A: " + contErros);
    }

    //Método para leitura de declaração de uma package
    public void readPackage(ArrayList<Analize> code) {
        if (code.get(controle).lexema.equals("package")) {
            controle++;
            if (controle < code.size()) {
                if (code.get(controle).token == (Tok_IDENTIFICADOR)) {
                    controle++;
                    if (controle < code.size()) {
                        if (code.get(controle).lexema.equals(";")) {
                            controle++;
                            //PACKAGE BEM DECLARADA
                            //System.out.println(VERDE + " OK PACKAGE YA " + ORIGINAL);
                            if (controle < code.size()) {
                                if (code.get(controle).lexema.equals("import")) {
                                    while (code.get(controle).lexema.equals("import")) {
                                        this.readImport(code);
                                    }
                                }
                            }
                        } else if (code.get(controle).lexema.equals(".")) {
                            controle++;
                            this.ajustePackage(code);
                        } else {
                            System.out.println(VERMELHO + "EXPECTED . OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + " EXPRESSION" + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + "EXPECTED A . OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " APÓS A EXPRESSÃO " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " EXPECTED A PACKAGE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
            contErros++;
        }
    }

    //Método auxiliar do método readPackage
    public void ajustePackage(ArrayList<Analize> code) {
        if (controle < code.size()) {
            if (code.get(controle).token == Tok_IDENTIFICADOR) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).lexema.equals(";")) {
                        controle++;
                        //DECLARAÇÃO DE PACKAGE OK!
                        //System.out.println(VERDE + " OK PACKAGE YA " + ORIGINAL);
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals("import")) {
                                while (code.get(controle).lexema.equals("import")) {
                                    this.readImport(code);
                                }
                            }
                        }
                    } else if (code.get(controle).lexema.equals(".")) {
                        controle++;
                        this.ajustePackage(code);
                    } else {
                        System.out.println(VERMELHO + " EXPECTED . OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED . OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
            contErros++;
        }
    }

    //Método de leitura da importação de uma classe
    public void readImport(ArrayList<Analize> code) {
        if (controle < code.size()) {
            if (code.get(controle).lexema.equals("import")) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).token == Tok_IDENTIFICADOR) {
                        controle++;
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals(";")) {
                                controle++;
                                //System.out.println(VERDE + " IMPORT OKEY" + ORIGINAL);
                                if (controle < code.size()) {
                                    if (code.get(controle).lexema.equals("import")) {
                                        this.readImport(code);
                                    }
                                }
                            } else if (code.get(controle).lexema.equals(".")) {
                                controle++;
                                this.ajusteImport(code);
                            } else {
                                System.out.println(VERMELHO + " EXPECTED . OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        }
                    } else if (code.get(controle).lexema.equals("static")) {
                        controle++;
                        ajusteImport(code);
                    } else {
                        System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                }
            }
        }
    }

    //Método auxiliar do readImport()
    public void ajusteImport(ArrayList<Analize> code) {
        if (controle < code.size()) {
            if (code.get(controle).token == Tok_IDENTIFICADOR) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).lexema.equals(";")) {
                        controle++;
                        //DECLARAÇÃO DE IMPORT OK!
                        //System.out.println(VERDE + " OK IMPORT YA " + ORIGINAL);
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals("import")) {
                                readImport(code);
                            }
                        }
                    } else if (code.get(controle).lexema.equals(".")) {
                        controle++;
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals("*")) {
                                controle++;
                                if (controle < code.size()) {
                                    if ((code.get(controle).lexema.equals(";"))) {
                                        controle++;
                                        //System.out.println(VERDE + " IMPORT OKEY ");
                                        if (controle < code.size()) {
                                            if (code.get(controle).lexema.equals("import")) {
                                                readImport(code);
                                            }
                                        }
                                    } else {
                                        System.out.println(VERMELHO + " EXPECTED . OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                        contErros++;
                                    }
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED . OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            } else if (code.get(controle).token == Tok_IDENTIFICADOR) {
                                this.ajusteImport(code);
                            } else {
                                System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER OR * IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER OR * IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED . OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED . OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER  " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
            contErros++;
        }
    }

    //Método para leitura da declaração de uma estruturas
    public void readEstruturas(ArrayList<Analize> code) {
        if (controle < code.size()) {
            if (code.get(controle).lexema.equals("public")) {
                controle++;
                if (controle < code.size()) {
                    this.readEstruturas(code);
                } else {
                    System.out.println(VERMELHO + " EXPECTED A CLASS, INTERFACE OR ENUM IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else if ((code.get(controle).lexema.equals("class")) || (code.get(controle).lexema.equals("interface")) || (code.get(controle).lexema.equals("enum"))) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).token == Tok_IDENTIFICADOR) {
                        controle++;
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals("{")) {
                                //ENTRAMOS NO ESCOPO DA ESTRUTURA
                                controle++;
                                this.classBody(code);
                            } else {
                                System.out.println(VERMELHO + " EXPECTED { IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED { IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER  IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + "EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (code.get(controle).lexema.equals("void")) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).token == Tok_IDENTIFICADOR) {
                        controle++;
                    } else {
                        System.out.println(VERMELHO + "EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + "EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED A CLASS, INTERFACE OR ENUM IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        }
    }

    //Método que verifica tudo que deve ser declarado dentro de uma classe
    public void classBody(ArrayList<Analize> code) {
        while (controle < code.size()) {
            if (tipos.contains(code.get(controle).lexema)) {
                //Chamada da função de declaração de variáveis e funções
                this.readDeclaracao(code);
            } else if (code.get(controle).lexema.equals("}")) {
                controle = code.size();
            } else if (code.get(controle).lexema.equals("public")) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).lexema.equals("static")) {
                        controle++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED A ACCESS MODIFIER OR DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (code.get(controle).lexema.equals("void")) {
                this.readDeclaracao(code);
            } else {
                controle++;
                System.out.println(VERMELHO + " EXPECTED A VARIABLE OR FUNCTION DECLARATION OR } IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        }
    }

    //Método de leitura de declaração  (VERIFICAR O ÚLTIMO ELSE DESTA FUNÇÃO)
    public void readDeclaracao(ArrayList<Analize> code) {
        if (controle < code.size()) {
            if (modifiers.contains(code.get(controle).lexema)) {
                controle++;
                if (controle < code.size()) {
                    this.readDeclaracao(code);
                } else {
                    System.out.println(VERMELHO + " EXPECTED DATA TYPE OR MODIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (tipos.contains(code.get(controle).lexema)) {
                typeVerify = code.get(controle).lexema;
                this.typeTokenVerify(typeVerify);
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).token == Tok_IDENTIFICADOR) {
                        controle++;
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals(";")) {
                                //DECLARAÇÃO DE UMA VARIÁVEL
                                controle++;
                                System.out.println(VERDE + " DECLARACAO DE VARIAVEL OKEYYYY " + ORIGINAL);
                                //CHAMADA DO CORPO DA CLASSE PARA SE VERIFICAR SE HÁ MAIS ALGUMA COISA DENTRO DA CLASSE
                                this.classBody(code);
                            } else if (code.get(controle).lexema.equals("(")) {
                                //DECLARACAO DE FUNCAO
                                controle++;
                                this.readFunctions(code);
                            } else if (code.get(controle).lexema.equals(",")) {
                                controle++;
                                //DECLARACAO DE MAIS DE UMA VARIÁVEL
                                if (controle < code.size()) {
                                    this.readDeclaracao(code);
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            } else if (code.get(controle).lexema.equals("=")) {
                                controle++;
                                this.ajusteAtribuicao(code);
                            } else {
                                System.out.println(VERMELHO + " EXPECTED ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (code.get(controle).lexema.equals("void")) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).token == Tok_IDENTIFICADOR) {
                        controle++;
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals("(")) {
                                controle++;
                                this.readFunctions(code);
                            } else {
                                System.out.println(VERMELHO + " EXPECTED A ( IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED A ( IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (code.get(controle).lexema.equals("[")) {
                if (controle < code.size()) {
                    this.readArray(code);
                } else {
                    System.out.println(VERMELHO + " EXPECTED [ IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " EXPECTED DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
            contErros++;
        }
    }

    //Método auxiliar da declaração de um método 
    public void auxDeclaracao(ArrayList<Analize> code) {
        if (controle < code.size()) {
            if (modifiers.contains(code.get(controle).lexema)) {
                controle++;
                if (controle < code.size()) {
                    this.auxDeclaracao(code);
                } else {
                    System.out.println(VERMELHO + " EXPECTED DATA TYPE OR MODIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (tipos.contains(code.get(controle).lexema)) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).token == Tok_IDENTIFICADOR) {
                        controle++;
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals(";")) {
                                controle++;
                                System.out.println(VERDE + " DECLARACAO DE VARIAVEL OKEYYYY " + ORIGINAL);
                            } else if (code.get(controle).lexema.equals(",")) {
                                controle++;
                                //DECLARACAO DE MAIS DE UMA VARIÁVEL
                                if (controle < code.size()) {
                                    this.auxDeclaracao(code);
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            } else if (code.get(controle).lexema.equals("=")) {
                                controle++;
                                this.ajusteAtribuicao(code);
                            } else {
                                System.out.println(VERMELHO + " EXPECTED ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                                controle--;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " EXPECTED DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
            contErros++;
        }
    } //STAND BY 

    //Método que dá auxílio ao método de declaração de uma variável com atribuição de algum valor (VERIFICAR O LOOP)
    public void ajusteAtribuicao(ArrayList<Analize> code) {
        if (controle < code.size()) {
            if (code.get(controle).token == Token.Tok_IDENTIFICADOR) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).lexema.equals(";")) {
                        controle++;
                        System.out.println(VERDE + " ATRIBUICAO FEITA COM SUCESSO " + ORIGINAL);
                    } else if (code.get(controle).lexema.equals(",")) {
                        controle++;
                        //VERIFICAR AQUI
                        this.ajusteAtribuicao(code);
                    } else if (code.get(controle).lexema.equals("=")) {
                        controle++;
                        this.ajusteAtribuicao(code);
                    } else {
                        System.out.println(VERMELHO + " EXPECTED A ; = OR , IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED A ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (code.get(controle).token == Tok_NUMERO_INTEIRO || code.get(controle).token == Tok_NUMERO_REAL) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle - 1).token == this.typeTokenVerify(typeVerify)) {
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals(";")) {
                                controle++;
                                System.out.println(VERDE + " ATRIBUICAO NUMERO OKEYYY " + ORIGINAL);
                            } else if (code.get(controle).lexema.equals(",")) {
                                controle++;
                                this.ajusteAtribuicao(code);
                            } else {
                                System.out.println(VERMELHO + " EXPECTED A ; OR , IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED A ; OR , IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " UNEXPECTED A VALUE FOR DATA TYPE OF VARIABLE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                }
            } else if (code.get(controle).lexema.equals("(")) {
                controle++;
                this.chamadaFuncao(code);
            } else {
                System.out.println(VERMELHO + " EXPECTED AN ATRIBUITION OF A VALUE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " EXPECTED AN ATRIBUITION OF A VALUE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
            contErros++;
        }
    }

    //Método para declaração de funções 
    public void readFunctions(ArrayList<Analize> code) {
        if (controle < code.size()) {
            if (tipos.contains(code.get(controle).lexema)) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).token == Tok_IDENTIFICADOR) {
                        controle++;
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals(")")) {
                                controle++;
                                System.out.println(VERDE + " FUNÇÃO COM UM PARAMETRO ok" + ORIGINAL);
                                if (controle < code.size()) {
                                    if (code.get(controle).lexema.equals("{")) {
                                        controle++;
                                        delimiterController++;
                                        routes.add("function");
                                        //chamada do corpo da corpo da funcao
                                        this.functionBody(code);
                                    } else {
                                        System.out.println(VERMELHO + " EXPECTED { IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                        contErros++;
                                    }
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED { IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            } else if (code.get(controle).lexema.equals(",")) {
                                controle++;
                                if (controle < code.size()) {
                                    this.auxFunctions(code);
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (code.get(controle).lexema.equals(")")) {
                controle++;
                //FUNCAO SEM PARÂMETRO DECLARADA
                if (controle < code.size()) {
                    if (code.get(controle).lexema.equals("{")) {
                        controle++;
                        delimiterController++;
                        routes.add("function");
                        //chamada do corpo da corpo da funcao
                        this.functionBody(code);
                    } else {
                        System.out.println(VERMELHO + " EXPECTED { IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED { IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (code.get(controle).token == Tok_IDENTIFICADOR) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).lexema.equals("(")) {
                        controle++;
                        if (controle < code.size()) {
                            this.readFunctions(code);
                        } else {
                            System.out.println(VERMELHO + " EXPECTED A DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED A ( IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED A ( IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (code.get(controle).lexema.equals("[")) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).lexema.equals("]")) {
                        controle++;
                        if (controle < code.size()) {
                            if (code.get(controle).token == Tok_IDENTIFICADOR) {
                                controle++;
                                if (controle < code.size()) {
                                    if (code.get(controle).lexema.equals(",")) {
                                        controle++;
                                        this.readFunctions(code);
                                    } else {
                                        System.out.println(VERMELHO + " EXPECTED A , OR ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                        contErros++;
                                    }
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED A , OR ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            } else {
                                System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED ] IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED ] IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " EXPECTED DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
            contErros++;
        }
    }

    //Função que dá auxílio à declaração de mais de um parâmetro dentro da assinatura da função
    public void auxFunctions(ArrayList<Analize> code) {
        if (tipos.contains(code.get(controle).lexema) || code.get(controle).lexema.equals("void")) {
            controle++;
            if (controle < code.size()) {
                if (code.get(controle).token == Tok_IDENTIFICADOR) {
                    controle++;
                    if (controle < code.size()) {
                        if (code.get(controle).lexema.equals(")")) {
                            controle++;
                            //System.out.println(VERDE + " AQUI TÁ FIXE " + ORIGINAL);
                            if (controle < code.size()) {
                                if (code.get(controle).lexema.equals("{")) {
                                    controle++;
                                    delimiterController++;
                                    routes.add("function");
                                    //chamada do corpo da corpo da funcao
                                    this.functionBody(code);
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED { IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            } else {
                                System.out.println(VERMELHO + " EXPECTED { IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else if (code.get(controle).lexema.equals(",")) {
                            controle++;
                            if (controle < code.size()) {
                                this.auxFunctions(code);
                            } else {
                                System.out.println(VERMELHO + " EXPECTED DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " EXPECTED DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
            contErros++;
        }
    }

    //Método de leitura da chamada de função que já foi criada antes
    public void chamadaFuncao(ArrayList<Analize> code) {
        if (controle < code.size()) {
            if (code.get(controle).token == Tok_IDENTIFICADOR) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).lexema.equals(")")) {
                        controle++;
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals(";")) {
                                controle++;
                                System.out.println(VERDE + " CHAMADA DE FUNÇÃO OKEYY " + ORIGINAL);
                            } else {
                                System.out.println(VERMELHO + " EXPECTED A ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED A ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else if (code.get(controle).lexema.equals(",")) {
                        controle++;
                        this.chamadaFuncao(code);
                    } else {
                        System.out.println(VERMELHO + " EXPECTED A , OR ) " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED A , OR ) " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle).lexema + ORIGINAL);
                    contErros++;
                }
            } else if (code.get(controle).token == Tok_NUMERO_INTEIRO || code.get(controle).token == Tok_NUMERO_REAL) {
                controle++;
                if (controle < code.size()) {
                    if (code.get(controle).lexema.equals(")")) {
                        controle++;
                        System.out.println(VERDE + " CHAMADA DA FUNCAO OKEYYYYYYYY " + ORIGINAL);
                    } else if (code.get(controle).lexema.equals(",")) {
                        controle++;
                        this.chamadaFuncao(code);
                    } else {
                        System.out.println(VERMELHO + " EXPECTED A , OR ) " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED A , OR ) " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER OR NUMBER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER OR NUMBER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle).lexema + ORIGINAL);
            contErros++;
        }
    }

    //Método que recebe tudo que pode entrar no corpo de uma função 
    public void functionBody(ArrayList<Analize> code) {
        while (controle < code.size()) {
            if (tipos.contains(code.get(controle).lexema)) {
                //Chamada da função de declaração de variáveis e funções
                this.readDeclaracao(code);
            } else if (code.get(controle).lexema.equals("if")) {
                //chamada da funcao de if 
                this.readIf(code);
            } else if (code.get(controle).lexema.equals("do")) {
                //chamada da funcao do while
                this.readDoWhile(code);
            } else if (code.get(controle).lexema.equals("while")) {
                //chamada da função while 
                this.readWhile(code);
            } else if (code.get(controle).lexema.equals("System")) {
                //controle++;
                // chamada da funcao de sout
            } else if (code.get(controle).lexema.equals("for")) {

            } else if (code.get(controle).token == Tok_IDENTIFICADOR) {
                //chamada da funcao de atribuicao
                controle++;
                this.ajusteAtribuicao(code);
            } else if (code.get(controle).lexema.equals("}")) {
                //controle++;
                delimiterController--;
                if (controle < code.size()) {
                    this.classBody(code);
                } else {
                    System.out.println(VERMELHO + " EXPECTED A VARIABLE } OF THE CLASS IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                controle++;
                System.out.println(VERMELHO + " EXPECTED A VARIABLE } OF THE CLASS IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        }
    }

    //Redirecionar
    public void redirect(ArrayList<Analize> code) {
        if (routes.get(delimiterController).equals("function")) {
            this.functionBody(code);
        } else if (routes.get(delimiterController).equals("if")) {
            this.ifBody(code);
        } else if (routes.get(delimiterController).equals("while")) {
            this.whileBody(code);
        } else if (routes.get(delimiterController).equals("Do While")) {
            //this.doWhileBody(code);
        }
    }

    //Método de declaração de vectores e matrizes
    public void readArray(ArrayList<Analize> code) {
        if (code.get(controle).lexema.equals("[")) {
            controle++;
            if (controle < code.size()) {
                if (code.get(controle).lexema.equals("]")) {
                    controle++;
                    if (controle < code.size()) {
                        if (code.get(controle).lexema.equals("=")) {
                            controle++;
                            if (controle < code.size()) {
                                if (code.get(controle).lexema.equals("new")) {
                                    controle++;
                                    if (controle < code.size()) {
                                        if (tipos.contains(code.get(controle).lexema)) {
                                            controle++;
                                            if (controle < code.size()) {
                                                if (code.get(controle).lexema.equals("[")) {
                                                    controle++;
                                                    if (controle < code.size()) {
                                                        if (code.get(controle).token == Tok_NUMERO_INTEIRO) {
                                                            controle++;
                                                            if (controle < code.size()) {
                                                                if (code.get(controle).lexema.equals("]")) {
                                                                    controle++;
                                                                    if (controle < code.size()) {
                                                                        if (code.get(controle).lexema.equals(";")) {
                                                                            controle++;
                                                                            System.out.println(VERMELHO + " DECLARACAO DE ARRAY OKEYYY " + ORIGINAL);
                                                                        } else {
                                                                            System.out.println(VERMELHO + " EXPECTED A ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                                                            contErros++;
                                                                        }
                                                                    } else {
                                                                        System.out.println(VERMELHO + " EXPECTED A ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                                                        contErros++;
                                                                    }
                                                                } else {
                                                                    System.out.println(VERMELHO + " EXPECTED A ] IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                                                    contErros++;
                                                                }
                                                            } else {
                                                                System.out.println(VERMELHO + " EXPECTED A ] IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                                                contErros++;
                                                            }
                                                        } else {
                                                            System.out.println(VERMELHO + " EXPECTED A NUMBER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                                            contErros++;
                                                        }
                                                    } else {
                                                        System.out.println(VERMELHO + " EXPECTED A NUMBER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                                        contErros++;
                                                    }
                                                } else {
                                                    System.out.println(VERMELHO + " EXPECTED A [ IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                                    contErros++;
                                                }
                                            } else {
                                                System.out.println(VERMELHO + " EXPECTED A [ IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                                contErros++;
                                            }
                                        } else {
                                            System.out.println(VERMELHO + " EXPECTED A DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                            contErros++;
                                        }
                                    } else {
                                        System.out.println(VERMELHO + " EXPECTED A DATA TYPE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                        contErros++;
                                    }
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED A new IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            } else {
                                System.out.println(VERMELHO + " EXPECTED A new IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED A = IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED A = IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED A ] IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED A ] IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        }
    }

    //Método da condicao para if, while, do while e for 
    public void readCondicao(ArrayList<Analize> code) {
        if (code.get(controle).token == Tok_IDENTIFICADOR) {
            controle++;
            if (controle < code.size()) {
                if (code.get(controle).token == Tok_OPERADOR_IGUAL || code.get(controle).token == Tok_MAIOR || code.get(controle).lexema.equals(">=") || code.get(controle).token == Tok_MENOR
                        || code.get(controle).lexema.equals("<=") || code.get(controle).lexema.equals("&&") || code.get(controle).lexema.equals("||")) {
                    controle++;
                    if (controle < code.size()) {
                        this.readCondicao(code);
                    } else {
                        System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER OR NUMBER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else if (code.get(controle).lexema.equals(")")) {
                    //controle++;
                } else {
                    System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER OR NUMBER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else if (code.get(controle).token == Tok_NUMERO_INTEIRO || code.get(controle).token == Tok_NUMERO_REAL) {
            controle++;
            if (controle < code.size()) {
                if (code.get(controle).token == Tok_OPERADOR_IGUAL || code.get(controle).token == Tok_MAIOR || code.get(controle).lexema.equals(">=") || code.get(controle).token == Tok_MENOR
                        || code.get(controle).lexema.equals("<=") || code.get(controle).lexema.equals("&&") || code.get(controle).lexema.equals("||")) {
                    controle++;
                    if (controle < code.size()) {
                        this.readCondicao(code);
                    } else {
                        System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER OR NUMBER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else if (code.get(controle).lexema.equals(")")) {
                    //controle++;
                } else {
                    System.out.println(VERMELHO + " EXPECTED AN IDENTIFIER OR NUMBER IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " ILEGAL START EXPRESSION OR EXPECTED A LOGIC EXPRESSION OR ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
            contErros++;
        }
    }

    //Método para declaração de if
    public void readIf(ArrayList<Analize> code) {
        if (code.get(controle).lexema.equals("if")) {
            controle++;
            if (controle < code.size()) {
                if (code.get(controle).lexema.equals("(")) {
                    controle++;
                    if (controle < code.size()) {
                        this.readCondicao(code);
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals(")")) {
                                controle++;
                                if (controle < code.size()) {
                                    if (code.get(controle).lexema.equals("{")) {
                                        controle++;
                                        delimiterController++;
                                        routes.add("if");
                                        //ENTRAMOS NO ESCOPO DO IF 
                                        this.ifBody(code);
                                    } else if (code.get(controle).lexema.equals(";")) {
                                        controle++;
                                        System.out.println(VERDE + " DECLARAÇÃO DE IF SEM ESCOPO OKEYYY " + ORIGINAL);
                                    } else {
                                        System.out.println(VERMELHO + " EXPECTED A { OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                        contErros++;
                                    }
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED A { OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            } else {
                                System.out.println(VERMELHO + " EXPECTED A ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED A ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " ILEGAL START OF EXPRESSION IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED ( IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED ( IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        }
    }

    //Método que recebe tudo que pode entrar no corpo do if
    public void ifBody(ArrayList<Analize> code) {
        while (controle < code.size()) {
            if (tipos.contains(code.get(controle).lexema)) {
                //Chamada da função de declaração de variáveis e funções
                this.readDeclaracao(code);
            } else if (code.get(controle).lexema.equals("if")) {
                //chamada da funcao de if 
                this.readIf(code);
            } else if (code.get(controle).lexema.equals("do")) {
                //chamada da funcao do while
                this.readDoWhile(code);
            } else if (code.get(controle).lexema.equals("while")) {
                //chamada da função while 
                this.readWhile(code);
            } else if (code.get(controle).lexema.equals("System")) {
                // chamada da funcao de sout
            } else if (code.get(controle).token == Tok_IDENTIFICADOR) {
                //chamada da funcao de atribuicao
                controle++;
                this.ajusteAtribuicao(code);
            } else if (code.get(controle).lexema.equals("}")) {
                controle++;
                delimiterController--;
                if (controle < code.size()) {
                    //DEFERIMENTO É DE CADA UM
                    this.redirect(code);
                } else {
                    System.out.println(VERMELHO + " EXPECTED A  } IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                controle++;
                System.out.println(VERMELHO + "ILEGAL START OF EXPRESSION OR EXPECTED A  } IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        }
    }

    //Método da leitura de declaração de while
    public void readWhile(ArrayList<Analize> code) {
        if (code.get(controle).lexema.equals("while")) {
            controle++;
            if (controle < code.size()) {
                if (code.get(controle).lexema.equals("(")) {
                    controle++;
                    if (controle < code.size()) {
                        this.readCondicao(code);
                        if (controle < code.size()) {
                            if (code.get(controle).lexema.equals(")")) {
                                controle++;
                                if (controle < code.size()) {
                                    if (code.get(controle).lexema.equals("{")) {
                                        controle++;
                                        delimiterController++;
                                        routes.add("if");
                                        //ENTRAMOS NO ESCOPO DO IF 
                                        this.ifBody(code);
                                    } else if (code.get(controle).lexema.equals(";")) {
                                        controle++;
                                        System.out.println(VERDE + " DECLARAÇÃO DE WHILE SEM ESCOPO OKEYYY " + ORIGINAL);
                                    } else {
                                        System.out.println(VERMELHO + " EXPECTED A { OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                        contErros++;
                                    }
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED A { OR ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            } else {
                                System.out.println(VERMELHO + " EXPECTED A ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED A ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " ILEGAL START OF EXPRESSION IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED ( IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED ( IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        }
    }

    //Método que recebe tudo que pode entrar no corpo do while
    public void whileBody(ArrayList<Analize> code) {
        while (controle < code.size()) {
            if (tipos.contains(code.get(controle).lexema)) {
                //Chamada da função de declaração de variáveis e funções
                this.readDeclaracao(code);
            } else if (code.get(controle).lexema.equals("if")) {
                //chamada da funcao de if 
                this.readIf(code);
            } else if (code.get(controle).lexema.equals("do")) {
                //chamada da funcao do while
                this.readDoWhile(code);
            } else if (code.get(controle).lexema.equals("while")) {
                //chamada da função while 
                this.readWhile(code);
            } else if (code.get(controle).lexema.equals("System")) {
                controle++;
                // chamada da funcao de sout
            } else if (code.get(controle).token == Tok_IDENTIFICADOR) {
                //chamada da funcao de atribuicao
                controle++;
                this.ajusteAtribuicao(code);
            } else if (code.get(controle).lexema.equals("}")) {
                controle++;
                delimiterController--;
                if (controle < code.size()) {
                    //DEFERIMENTO É DE CADA UM
                    this.redirect(code);
                } else {
                    System.out.println(VERMELHO + " EXPECTED A  } IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                controle++;
                System.out.println(VERMELHO + " EXPECTED A  } IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        }
    }

    //Método da leitura de declaração de do while
    public void readDoWhile(ArrayList<Analize> code) {
        if (code.get(controle).lexema.equals("do")) {
            controle++;
            if (controle < code.size()) {
                if (code.get(controle).lexema.equals("{")) {
                    controle++;
                    delimiterController++;
                    routes.add("Do While");
                    //ENTRAMOS NO ESCOPO DO "DO WHILE"
                    this.doWhileBody(code);
                } else {
                    System.out.println(VERMELHO + " EXPECT A { IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECT A { IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        }
    }

    //Método auxiliar do "do while"
    public void auxDoWhile(ArrayList<Analize> code) {
        if (controle < code.size()) {
            if (code.get(controle).lexema.equals("(")) {
                controle++;
                if (controle < code.size()) {
                    this.readCondicao(code);
                    if (controle < code.size()) {
                        if (code.get(controle).lexema.equals(")")) {
                            controle++;
                            if (controle < code.size()) {
                                if (code.get(controle).lexema.equals(";")) {
                                    controle++;
                                    delimiterController--;
                                    if (controle < code.size()) {
                                        this.redirect(code);
                                    }
                                } else {
                                    System.out.println(VERMELHO + " EXPECTED A ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                    contErros++;
                                }
                            } else {
                                System.out.println(VERMELHO + " EXPECTED A ; IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                                contErros++;
                            }
                        } else {
                            System.out.println(VERMELHO + " EXPECTED A ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                            contErros++;
                        }
                    } else {
                        System.out.println(VERMELHO + " EXPECTED A ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " ILEGAL START EXPRESSION OR EXPECTED A LOGIC EXPRESSION OR ) IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                System.out.println(VERMELHO + " EXPECTED ( IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        } else {
            System.out.println(VERMELHO + " EXPECTED ( IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
            contErros++;
        }
    }

    //Método que recebe tudo que pode entrar no corpo do "do while"
    public void doWhileBody(ArrayList<Analize> code) {
        while (controle < code.size()) {
            if (tipos.contains(code.get(controle).lexema)) {
                //Chamada da função de declaração de variáveis e funções
                this.readDeclaracao(code);
            } else if (code.get(controle).lexema.equals("if")) {
                //chamada da funcao de if 
                this.readIf(code);
            } else if (code.get(controle).lexema.equals("do")) {
                controle++;
                //chamada da funcao do while
                this.readDoWhile(code);
            } else if (code.get(controle).lexema.equals("while")) {
                controle++;
                //chamada da função while 
                this.readWhile(code);
            } else if (code.get(controle).lexema.equals("System")) {
                controle++;
                // chamada da funcao de sout
            } else if (code.get(controle).token == Tok_IDENTIFICADOR) {
                //chamada da funcao de atribuicao
                controle++;
                this.ajusteAtribuicao(code);
            } else if (code.get(controle).lexema.equals("}")) {
                controle++;
                if (controle < code.size()) {
                    //DEFERIMENTO É DE CADA UM 
                    //TÉRMINO DA DECLARAÇÃO DO "DO WHILE"
                    if (code.get(controle).lexema.equals("while")) {
                        controle++;
                        this.auxDoWhile(code);
                    } else {
                        System.out.println(VERMELHO + " EXPECTED A WHILE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                        contErros++;
                    }
                } else {
                    System.out.println(VERMELHO + " EXPECTED A WHILE IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                    contErros++;
                }
            } else {
                controle++;
                System.out.println(VERMELHO + " EXPECTED A VARIABLE  } IN LINE " + code.get(controle - 1).linhaCodigo + " AFTER " + code.get(controle - 1).lexema + ORIGINAL);
                contErros++;
            }
        }
    }

    //Método a auxiliar para verificar valores que foram atribuidos as variáveis
    public Token typeTokenVerify(String type) {
        if (type.equals("int")) {
            tokenVerify = Tok_NUMERO_INTEIRO;
        } else if (type.equals("float") || type.equals("double")) {
            tokenVerify = Tok_NUMERO_REAL;
        } else if (type.equals("String")) {
            tokenVerify = Tok_STRING;
        } else if (type.equals("char")) {
            tokenVerify = Tok_CARACTERE;
        }
        return tokenVerify;
    }
    
    //Método de leitura de sout
    public void readSout(ArrayList <Analize> code){
        
    }

}
