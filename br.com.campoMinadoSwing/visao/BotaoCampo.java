package visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import modelo.Campo;
import modelo.CampoEvento;
import modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener {
	
	private final Color BG_PADRAO = new Color(184, 184, 184);
	@SuppressWarnings("unused")
	private final Color BG_MARCAR = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(189, 66, 68);
	private final Color TEXTO_VERDE = new Color(0, 100, 0);
	
	private Campo campo;
	
	public BotaoCampo(Campo campo) {
		
		this.campo = campo;
		setBackground(BG_PADRAO);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
		campo.registrarObservador(this);	
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch (evento) {
		case ABRIR: 
			aplicarEstiloAbrir();
			break;
			
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstilopadrao();
	}
		
	SwingUtilities.invokeLater(()->{ //valida��o
		repaint();
		validate();
	});
				
}

	private void aplicarEstiloAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));// arte de botao afundado
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			return;
		}
		
		setBackground(BG_PADRAO);
		
		
		switch (campo.minasNaVizinhanca()) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		
		String valor = !campo.vizinhancaSegura() ? 
				campo.minasNaVizinhanca() +"": "";
		setText(valor);
	}
		
	private void aplicarEstiloMarcar() {
		setForeground(Color.BLACK);
		setBackground(BG_EXPLODIR);	
	
		setText("M");
	}
		
	private void aplicarEstiloExplodir() {
		setForeground(Color.WHITE);
		setBackground(BG_EXPLODIR);
		setText("X");
	}
		
	private void aplicarEstilopadrao() {
	
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}
	

	// Interface dos eventos do mause

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getButton() == 1) {
			campo.abrir();
			//System.out.println("Bot�o esquerdo");
		}else {
			campo.alternarMarcacao();
			//System.out.println("Outro bot�o");
		}
	}
	
	public void mouseClicked(MouseEvent e){}

	public void mouseReleased(MouseEvent e){}

	public void mouseEntered(MouseEvent e){}
	
	public void mouseExited(MouseEvent e){}


							
}
