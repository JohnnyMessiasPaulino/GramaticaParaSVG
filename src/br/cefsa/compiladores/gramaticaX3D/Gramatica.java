package br.cefsa.compiladores.gramaticaX3D;

import java.util.List;

public class Gramatica {
	
		private List<String> alfabeto;
		private int passos;
		private String axioma;
		private double angulo;
		private String[] regrasProducao;
		
		public int getPassos() {
			return passos;
		}
		public void setPassos(int passos) {
			this.passos = passos;
		}
		public String getAxioma() {
			return axioma;
		}
		public void setAxioma(String axioma) {
			this.axioma = axioma;
		}
		public double getAngulo() {
			return angulo;
		}
		public void setAngulo(double angulo) {
			this.angulo = angulo;
		}

		public List<String> getAlfabeto() {
			return alfabeto;
		}
		public void setAlfabeto(List<String> alfabeto) {
			this.alfabeto = alfabeto;
		}
		public String[] getRegrasProducao() {
			return regrasProducao;
		}
		public void setRegrasProducao(String[] regrasProducao) {
			this.regrasProducao = regrasProducao;
		}


		
}
