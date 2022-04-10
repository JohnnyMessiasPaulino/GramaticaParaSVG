package br.cefsa.compiladores.gramaticaX3D;

import java.io.IOException;



public class Principal_GramaticaSVG {
	
	public static void main(String[] args) throws IOException 
	{
		//String enderecoTxtLeitura = "D:\\Teste\\gramatica.txt";
		String enderecoTxtLeitura = "arquivoTXT\\gramatica.txt";
		String enderecoTxtGravacaoSVG = "D:\\Teste\\grafico_gerado_SVG.html";
		//String enderecoTxtGravacaoSVG = "arquivoTXT\\grafico_gerado_SVG.html";
		//String enderecoTxtGravacaoX3D = "D:\\Teste\\grafico_gerado_X3D.html";
		
		String gramaticaTxt = Utilitarios.lerArquivoTxt(enderecoTxtLeitura);
		
		Gramatica gramatica = new Gramatica();
		
		gramatica = Utilitarios.obtemGramatica(gramaticaTxt);
		
		String regraProcessada = Utilitarios.executaRegrasProducao(gramatica);
		
		String corpoTagProcessadoCaminhoTartarugaSVG = Utilitarios.tartarugaSVG(regraProcessada, gramatica);
		//String corpoTagProcessadoCaminhoTartarugaX3D = Utilitarios.tartarugaX3D(regraProcessada, gramatica);
		
		String arquivoHtmlSVG = Utilitarios.geraHtmlSVG(gramatica, corpoTagProcessadoCaminhoTartarugaSVG);
		//String arquivoHtmlX3D = Utilitarios.geraHtmlX3D(gramatica, corpoTagProcessadoCaminhoTartarugaX3D);
		
		Utilitarios.gravarArquivoTxt(enderecoTxtGravacaoSVG, arquivoHtmlSVG);
		//Utilitarios.gravarArquivoTxt(enderecoTxtGravacaoX3D, arquivoHtmlX3D);
		
		System.out.println("Fim das execuções");

	}

}
