package analisador.lexico;

import java.util.ArrayList;

//Classe com os componentes do token lido no código fonte
public class Analize {
    //Atributos 
    public Token token;
    public String lexema;
    public int linhaCodigo;
    
    //Método para inicializar os atributos da classe
    public  Analize(Token token, String lexema, int linhaCodigo){
        this.token = token;
        this.lexema = lexema;
        this.linhaCodigo = linhaCodigo;
    }
    
    //Construtor alternativo 
    /*public Analize(Token token, String lexema, int linhaCodigo){
        this.token = token;
        this.lexema = lexema;
        this.linhaCodigo = linhaCodigo;
    }*/
    
    //Constructor padrão 
    public Analize(){}
    
    //Getters e setters 
    public Token getToken() {
        return token;
    }

    public String getLexema() {
        return lexema;
    }

    public int getLinha() {
        return linhaCodigo;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setLinha(int linha) {
        this.linhaCodigo = linha;
    }
}
