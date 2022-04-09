package br.cefsa.compiladores.gramaticaX3D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Utilitarios 
{
	public static String lerArquivoTxt(String endereco)
	{
		Scanner ler = new Scanner(System.in);
		String conteudo = "";
		
		try {
		      FileReader arq = new FileReader(endereco);
		      BufferedReader lerArq = new BufferedReader(arq);

		      String linha = lerArq.readLine();
		      conteudo += linha + "\n";

		      while (linha != null) {

		        linha = lerArq.readLine();
		        
		        if(linha != null)
		        	conteudo += linha + "\n";
		      }
		      arq.close();
		    } 
		catch (IOException e) 
		{
		        System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		}
		return conteudo;
	}
	
	public static void gravarArquivoTxt(String endereco, String conteudo) throws IOException
	{
		 FileWriter arq = new FileWriter(endereco);
		 PrintWriter gravarArq = new PrintWriter(arq);
		 
		 gravarArq.printf(conteudo);
		 
		 arq.close();
		 
	}
	
	public static Gramatica obtemGramatica(String gramaticaTxt)
	{
		Gramatica gramatica = new Gramatica();
		ArrayList<String> alfabeto = new ArrayList();
		
		String[] gramaticaVetor = gramaticaTxt.split("\n");
		int numeroDeRegras = gramaticaVetor.length - 4;
		String[] regrasDeProducao = new String[1];
		alfabeto.add(gramaticaVetor[0]);
		gramatica.setAlfabeto(alfabeto);
		gramatica.setPassos(Integer.parseInt(gramaticaVetor[1]));
		gramatica.setAxioma(gramaticaVetor[2]);
		gramatica.setAngulo(Double.parseDouble(gramaticaVetor[3]));
		regrasDeProducao = gramaticaVetor[4].split(">");
		gramatica.setRegrasProducao(regrasDeProducao);

		return gramatica;
	}
	/*
	 *	Σ    Alfabeto da linguagem
		n    Passos, etapas
		ω    Axioma, condição inicial
		δ    Angulo
		pX   Regras de produção
		
		Σ : F, +, -         (Alfabeto)
		n : 3				(Passos)
		ω : F-F-F-F			(Axioma)
		δ : 90º				(Angulo)
		p1 : F > +FF		(Regras)

		0: F-F-F-F 			(Axioma - Condição inicial)
		1: +FF-+FF-+FF-+FF
		2: ++FF+FF-++FF+FF-++FF+FF-++FF+FF
		3: +++FF+FF++FF+FF-+++FF+FF++FF+FF-+++FF+FF++FF+FF-+++FF+FF++FF+FF
	 */
	

	public static String geraGrafico (Gramatica gramatica)
	{
		String graficoGerado = "";
		
		//Passos é o primeiro for
		//Regras é o segundo for
		//Axioma é a condição inicial
		
		String axiomaAtual = gramatica.getAxioma();
		ArrayList<String> axiomas = new ArrayList();
		axiomas.add(axiomaAtual);
		String proximoAxioma = "";
		String charQueSeraSubstituido = gramatica.getRegrasProducao()[0];
		String charParaSubstituir = gramatica.getRegrasProducao()[1];
		
		for(int i = 0; i < gramatica.getPassos(); i++)
		{
			proximoAxioma = axiomaAtual.replace(charQueSeraSubstituido, charParaSubstituir);
			axiomas.add(proximoAxioma);
			axiomaAtual = proximoAxioma;
		}

		String corpoHtml = "<html> \r\n"
				+ "        <head> \r\n"
				+ "            <title>Grafico gerado</title> 			\r\n"
				+ "            <script type='text/javascript' src='http://www.x3dom.org/download/x3dom.js'> </script> \r\n"
				+ "            <link rel='stylesheet' type='text/css' href='http://www.x3dom.org/download/x3dom.css'></link> \r\n"
				+ "        </head> \r\n"
				+ "        <body> \r\n"
				+ "            <h1>A gramatica inserida gerou a geometria abaixo</h1> \r\n"
				+ "			<h3>Gramatica: " + gramatica + " </h3> \r\n"
				+ graficoGerado + "\r\n"
				+ "		</body> \r\n"
				+ "	</html>  ";
		
		
	
		
		return graficoGerado;
	}
	


}
