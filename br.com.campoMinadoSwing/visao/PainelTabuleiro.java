package visao;

import java.awt.GridLayout;
import javax.swing.JPanel;
import modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel { //JPanel agrupador tipo containner que agrupa outro componente dentro de outros

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(
				tabuleiro.getLinhas(), tabuleiro.getColunas()));
		
		tabuleiro.paraCadaCampo(c -> add(new ButaoCampo(c)));		
		tabuleiro.registrarObservador(e -> {
			//TODO mostrar resultado  para usuario
		});
	}
}
