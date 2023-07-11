package analisador.semantico;

import analisador.lexico.Analex;
import analisador.lexico.Analize;
import analisador.sintatico.Anasint;
import java.io.IOException;
import java.util.ArrayList;

public class ExecutaAnaseman {

    public static void main(String[] args) throws IOException {
        Analex analisador = new Analex();
        Anasint anex = new Anasint();
        ArrayList <Analize> lex = new ArrayList();
        AnaSemex a = new AnaSemex();
        
         //Atribuição dos tokens à lista de tokens do código fonte
        lex = analisador.lerFicheiro();
        
        //Chamada da função 
        //anex.teste();
        //analisador.imprimir(lex);
        
        //anex.anasint(lex);
        a.anasemex(lex);
        
        
    }
    
}
