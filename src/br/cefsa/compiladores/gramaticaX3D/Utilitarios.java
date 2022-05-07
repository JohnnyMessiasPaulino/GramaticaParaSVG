package br.cefsa.compiladores.gramaticaX3D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

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

	
	public static String executaRegrasProducao(Gramatica gramatica)
	{
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
		
		return axiomaAtual;
	}
	
	public static double[] atualizaPosicoes(double[] posicoes, double angulo)
	{
		int passo = 20;
		double radiano = angulo * (Math.PI/180);
		double[] novaPosicaoXY = new double[3];
		
		//BigDecimal bdX = new BigDecimal().setScale(2, RoundingMode.HALF_EVEN);
		//BigDecimal bdY = new BigDecimal().setScale(2, RoundingMode.HALF_EVEN);
		
		novaPosicaoXY[0] += Math.round(posicoes[0] + (Math.sin(radiano) * passo)); //co = sen * hip
		novaPosicaoXY[1] += Math.round(posicoes[1] - (Math.cos(radiano) * passo)); //ca = cos * hip
		
		System.out.println("X: " + novaPosicaoXY[0] + "\n"
				+ "Y: " + novaPosicaoXY[1] + "\n");

		return novaPosicaoXY;
	}
	
	public static String tartarugaSVG(String regraProcessada, Gramatica gramatica)
	{
		String corpoTagProcessado = "";
		double[] posicaoXY = new double[2];
		posicaoXY[0] = 800; //Referente a X
		posicaoXY[1] = 1500; //Referente a Y
		double[] proximaPosicaoXY = new double[2];
		proximaPosicaoXY[0] = 800; //Referente a X
		proximaPosicaoXY[1] = 1500; //Referente a Y
		Stack<double[]> posicaoGravadaXY = new Stack<double[]>();
		posicaoGravadaXY.add(proximaPosicaoXY);
		
		double anguloAtual = 0; //Para cima = 0 -- Sentido horario positivo
		double anguloGravado = 0;
		

		for(int i = 0; i < regraProcessada.length(); i++)
		{
			if(regraProcessada.charAt(i) == 'F')
			{
				proximaPosicaoXY = atualizaPosicoes(posicaoXY, anguloAtual);
				
				corpoTagProcessado += 
						"  					<line x1=\"" + posicaoXY[0] + "\" "
								+ 				"y1=\"" + posicaoXY[1] + "\" "
								+ 				"x2=\"" + proximaPosicaoXY[0] + "\" "
								+ 				"y2=\"" + proximaPosicaoXY[1] + "\" "
								+ 				"style=\"stroke:rgb(0,0,0);stroke-width:1\" />\r\n";
				
				posicaoXY = proximaPosicaoXY;

			}
			else if(regraProcessada.charAt(i) == '+')
			{
				anguloAtual += gramatica.getAngulo();
			}
			else if(regraProcessada.charAt(i) == '-')
			{
				anguloAtual -= gramatica.getAngulo();
			}
			else if(regraProcessada.charAt(i) == '[')
			{
				posicaoGravadaXY.add(posicaoXY);
				//anguloGravado = anguloAtual;
				//anguloAtual = 0;
			}
			else if(regraProcessada.charAt(i) == ']')
			{
				posicaoXY = posicaoGravadaXY.pop();
				//anguloAtual = anguloGravado;
			}

			
		}
		
		System.out.println("Caminhos executados");
		return corpoTagProcessado;
	}
	
	public static String tartarugaX3D(String regraProcessada, Gramatica gramatica)
	{
		String corpoTagProcessado = "";
		double[] posicaoImagemXYZ = new double[3];
		posicaoImagemXYZ[0] = 0; //Referente a X
		posicaoImagemXYZ[1] = 0; //Referente a Y
		posicaoImagemXYZ[2] = 0; //Referente a Z
		double[] posicaoGravadaImagemXYZ = new double[3];
		posicaoGravadaImagemXYZ[0] = 0; //Referente a X
		posicaoGravadaImagemXYZ[1] = 0; //Referente a Y
		posicaoGravadaImagemXYZ[2] = 0; //Referente a Z
		
		double anguloAtual = 0; //Para cima = 0 -- Sentido horario positivo
		
		/*
		 * 
		 * dAB = Math.root(Math.pow(xb - xa)
		 * dAB = Raiz((xb - xa)^2 + (yb - ya)^2 + (zb - za)^2)
		 */

		
		for(int i = 0; i < regraProcessada.length(); i++)
		{
			if(regraProcessada.charAt(i) == 'F')
			{
				corpoTagProcessado += "					<transform translation='"
																+ posicaoImagemXYZ[0] + " " 
																+ posicaoImagemXYZ[1] + " " 
																+ posicaoImagemXYZ[2] + "'>\r\n"
						+ "						<shape>\r\n"
						+ "							<appearance> \r\n"
						+ "								<material diffuseColor='1 1 1'></material> \r\n"
						+ "							</appearance> 					\r\n"
						+ "							<box size='0.1 1 0.1'>\r\n"
						+ "							</box> \r\n"
						+ "						</shape> \r\n"
						+ "					</transform>\r\n";

				
				posicaoImagemXYZ = atualizaPosicoes(posicaoImagemXYZ, anguloAtual);

			}
			else if(regraProcessada.charAt(i) == '+')
			{
				anguloAtual += gramatica.getAngulo();
			}
			else if(regraProcessada.charAt(i) == '-')
			{
				anguloAtual -= gramatica.getAngulo();
			}
			else if(regraProcessada.charAt(i) == '[')
			{
				posicaoGravadaImagemXYZ = posicaoImagemXYZ;
			}
			else if(regraProcessada.charAt(i) == ']')
			{
				posicaoImagemXYZ = posicaoGravadaImagemXYZ;
			}

			
		}
		
		return corpoTagProcessado;
	}
	
	public static String geraHtmlSVG (Gramatica gramatica, String corpoTagProcessadoSVG)
	{
		String corpoHtml = "<html> \r\n"
				+ "        <head> \r\n"
				+ "            <title>Grafico gerado</title> 			\r\n"
				+ "        </head> \r\n"
				+ "        <body> \r\n"
				+ "            <h1>A gramatica inserida gerou a geometria abaixo</h1> \r\n"
				+ "			<h3>Alfabeto: " + gramatica.getAlfabeto() + " </h3> \r\n"
				+ "			<h3>Passos: " + gramatica.getPassos() + " </h3> \r\n"
				+ "			<h3>Axioma: " + gramatica.getAxioma() + " </h3> \r\n"
				+ "			<h3>Angulo: " + gramatica.getAngulo() + " </h3> \r\n"
				+ "			<h3>Regras Produ��o: " + gramatica.getRegrasProducao()[0] + " > " + gramatica.getRegrasProducao()[1] + " </h3> \r\n"
				+ "				<svg height=\"3000\" width=\"3000\">\r\n"
				+ corpoTagProcessadoSVG + "\r\n"
				+ "				</svg>\r\n"
				+ "	</body> \r\n"
				+ "</html>  ";
		
		return corpoHtml;
	}
	

	public static String geraHtmlX3D (Gramatica gramatica, String corpoTagProcessado)
	{
		String corpoHtml = "<html> \r\n"
				+ "        <head> \r\n"
				+ "            <title>Grafico gerado</title> 			\r\n"
				+ "            <script type='text/javascript' src='http://www.x3dom.org/download/x3dom.js'> </script> \r\n"
				+ "            <link rel='stylesheet' type='text/css' href='http://www.x3dom.org/download/x3dom.css'></link> \r\n"
				+ "        </head> \r\n"
				+ "        <body> \r\n"
				+ "            <h1>A gramatica inserida gerou a geometria abaixo</h1> \r\n"
				+ "			<h3>Gramatica: " + gramatica.getAxioma() + " </h3> \r\n"
				+ "			<x3d width='1200px' height='800px'> \r\n"
				+ "				<scene>\r\n"
				+ corpoTagProcessado + "\r\n"
				+ "				</scene>\r\n"
				+ "			</x3d>\r\n"
				+ "		</body> \r\n"
				+ "	</html>  ";
		
		return corpoHtml;
	}
	


}
