package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {

	private final int linhas;
	private final int colunas;
	private final int minas;
	
	private final List<Campo> campos = new ArrayList<Campo>();
	private List<Consumer<ResultadoEvento>>  observadores  = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	
	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(Boolean resultado) {
		observadores.stream().forEach(r -> r.accept(new ResultadoEvento (resultado)));
	}
	
	public void abrirCampo(int linha, int coluna) {
		campos.parallelStream()
			  .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			  .findFirst()
			  .ifPresent(c -> c.abrir());	
	}
	
	public boolean objetivoAlcancado() {// verificar se todos os campos tem objetivo alcançado
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		
		if(evento == CampoEvento.EXPLODIR) {
			
			System.out.println("Perdeu... :(");
			mostrarMinas();
			notificarObservadores(false);
			
		}else if(objetivoAlcancado()) {
			
			System.out.println("Ganhou... :)");
			notificarObservadores(true);
		}	
		
	}
	
	private void mostrarMinas() {
		campos.stream()
		.filter(c -> c.isMinado())
		.forEach(c -> c.setAberto(true));
	}
	
	public void AlternarMarcacao(int linha, int coluna) {
		campos.parallelStream()
			  .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			  .findFirst()
			  .ifPresent(c -> c.alternarMarcacao());
			  
		
	}

	private void gerarCampos() { // gerar os campos do tabuleiro
		for(int linha = 0; linha < linhas; linha++) {
			for(int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.registrarObservador(this);
				campos.add(campo);
			}
		}
		
	}
	
	private void associarVizinhos() { 
		for (Campo campo1 : campos) {
			for (Campo campo2 : campos) {
				campo1.adicionarVizinho(campo2);
			}
		}
		
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {
			
			int aleatorio = (int) (Math.random() * campos.size());
			minasArmadas = campos.stream().filter(minado).count();
			campos.get(aleatorio).minar();
		}while(minasArmadas < minas); 
	}
	
	
}
