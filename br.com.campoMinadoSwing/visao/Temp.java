package visao;

import modelo.Tabuleiro;

public class Temp {
	
	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(3, 3, 9);
		
		tabuleiro.registrarObservador(e -> {
			if(e.isGanhou()) {
				System.out.println("Ganhou ... :)");
			}else {
				System.out.println("Perdeu ... :(");
				}
		});
		
		tabuleiro.AlternarMarcacao(0, 0);
		tabuleiro.AlternarMarcacao(0, 1);
		tabuleiro.AlternarMarcacao(0, 2);
		tabuleiro.AlternarMarcacao(1, 0);
		tabuleiro.AlternarMarcacao(1, 1);
		tabuleiro.AlternarMarcacao(1, 2);
		tabuleiro.AlternarMarcacao(2, 0);
		tabuleiro.AlternarMarcacao(2, 1);
		
		tabuleiro.abrirCampo(2, 2);
		//tabuleiro.AlternarMarcacao(2, 2);
	}
}


