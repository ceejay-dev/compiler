package analisador.sintatico;

import analisador.lexico.Token;
import java.util.ArrayList;

public class Sintatico {
    public static final String AZUL = "\033[34m";
    public static final String VERDE = "\033[0;32m";
    
    //attributos 
    public Token token;
    public String lexema;
    public String tipo;
    public String valorAtribuicao;
    public String tipoAtribuicao;
    public int escopo;

    public Sintatico(Token token, String lexema, String tipo, String valorAtribuicao, String tipoAtribuicao, int escopo) {
        this.token = token;
        this.lexema = lexema;
        this.tipo = tipo;
        this.valorAtribuicao = valorAtribuicao;
        this.tipoAtribuicao = tipoAtribuicao;
        this.escopo = escopo;
    }

    public Sintatico() {
    }
    
    public void printTable(ArrayList <Sintatico> lista ){
        System.out.println("\n____________________________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println("                                                                                                SEMANTIC TABLE                             ");
        System.out.format("|%-50s| %30s| %16s| %30s| %30s| %16s|\n", "TOKEN:", "LEXEM: ", "TYPE: ", "ATRIBUITION VALUE:", "ATRIBUITION DATA TYPE:", " SCOPE:");
        for(Sintatico code: lista){
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.format("|%-50s| %30s| %16s| %30s| %30s| %16s|\n",code.token,code.lexema,code.tipo,code.valorAtribuicao,code.tipoAtribuicao,code.escopo);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("____________________________________________________________________________________________________________________________________________________________________________________________________");
        }
    }
}
