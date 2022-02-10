package visao;

import java.awt.GridLayout;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;

import modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel { //JPanel agrupador tipo containner que agrupa outro componente dentro de outros

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(
				tabuleiro.getLinhas(), tabuleiro.getColunas()));
		
		tabuleiro.paraCada(c -> add(new ButaoCampo(c)));
	}
}
