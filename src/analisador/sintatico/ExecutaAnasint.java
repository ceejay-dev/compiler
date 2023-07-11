package analisador.sintatico;

import analisador.lexico.Analex;
import analisador.lexico.Analize;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//Classe para execução do analisador sintático
public class ExecutaAnasint {   
    public static void main(String [] args) throws FileNotFoundException, IOException {
        //Instância do enum e as classes necessárias
        Analex analisador = new Analex();
        Anasint anex = new Anasint();
        ArrayList <Analize> lex = new ArrayList();
     
        //Atribuição dos tokens à lista de tokens do código fonte
        lex = analisador.lerFicheiro();
        
        //Chamada da função 
        anex.anasint(lex);
        
    }
    
    
    
}


