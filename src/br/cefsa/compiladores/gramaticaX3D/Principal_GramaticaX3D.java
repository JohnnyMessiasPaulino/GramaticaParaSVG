package br.cefsa.compiladores.gramaticaX3D;

import java.io.IOException;



public class Principal_GramaticaX3D {
	
	public static void main(String[] args) throws IOException 
	{
		String enderecoTxtLeitura = "D:\\Teste\\gramatica.txt";
		String enderecoTxtGravacao = "D:\\Teste\\grafico_gerado.html";
		
		String gramaticaTxt = Utilitarios.lerArquivoTxt(enderecoTxtLeitura);
		
		Gramatica gramatica = new Gramatica();
		
		gramatica = Utilitarios.obtemGramatica(gramaticaTxt);
		
		String regraProcessada = Utilitarios.executaRegrasProducao(gramatica);
		
		String corpoTagProcessadoCaminhoTartaruga = Utilitarios.tartaruga(regraProcessada, gramatica);
		
		String arquivoHtml = Utilitarios.geraHtml(gramatica, corpoTagProcessadoCaminhoTartaruga);
		
		Utilitarios.gravarArquivoTxt(enderecoTxtGravacao, arquivoHtml);
		
		//System.out.println();

	}

}
