package analisador.lexico;

import java.io.*;
import java.util.*;

//Classe para a execução de analex
public class ExecutaAnalex {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //Instância das classes e enum necessárias 
        Analex analisador = new Analex();
        ArrayList<Analize> codigos = analisador.lerFicheiro();
        //Token token = null;
        Analize a = new Analize();

        //Impressão de cada token analisado no analex
        for (Analize analize : codigos) {
            //Método para imprimir todos os tokens (é um método de verificação do funcionamento analex utilizado dentro Anasint)
            System.out.println("==========================================================================================================================================================================================================================================");
            System.out.println("LINHA : " + analize.linhaCodigo + " TOKEN : " + analize.token + " LEXEMA : " + analize.lexema);
            System.out.println("==========================================================================================================================================================================================================================================");
        }
    }
}
