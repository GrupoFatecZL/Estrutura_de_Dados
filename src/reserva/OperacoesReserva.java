package reserva;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import cliente.OperacoesClientes;
import enfeite.OperacoesEnfeite;

public class OperacoesReserva {

	private LocalDate DataFesta = LocalDate.now();
    private LocalDate DataPrevista = DataFesta.plusDays(3);
    private LocalDate DataRetorno = LocalDate.now();
    private String HoraInicio = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	private String HoraPrevisto = LocalTime.now().plusHours(12).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private String HoraRetorno = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private String FormaDePagamento;
    private double PrecoFinal;
	private String Cliente;
	private int QtdeAluguel;
    private String Enfeite;
	private NO_Reserva inicio;
	private BufferedReader buffer;

	OperacoesClientes Clientes = new OperacoesClientes();
	OperacoesEnfeite Enfeites = new OperacoesEnfeite();
	
	public OperacoesReserva() {
		inicio = null;
	}

    public void MenuReservar() {
		int opcao = 0;
		while (opcao != 9) {

			opcao = Integer.parseInt(JOptionPane.showInputDialog("Menu de Reserva: "+
			"\n1- Realizar uma reserva"+
			"\n2- Consultar todas as reservas"+
			"\n3- Buscar uma reserva"+
			"\n9- Voltar  "));
			
			switch (opcao) {
				case 1:
					RealizarReserva();
				break;

				case 2:	
					ListarReservas();
				break;

				case 3:	
					String reserva = JOptionPane.showInputDialog("Informe algo informado na reservas: ");
					try {
						String arquivo = "ArquivoReserva.txt";
						BuscarReserva(reserva, arquivo);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Reserva nao localizada");
					}
				break;

				case 9:
					JOptionPane.showMessageDialog(null, "Voltando ao menu anterior");
				break;

				default:
					JOptionPane.showMessageDialog(null, "Opcao invalida");
			} 
		} 
	} 
    
	public void RealizarReserva() {

		Reserva reservas = new Reserva(DataFesta, DataPrevista, DataRetorno, HoraInicio, HoraPrevisto, HoraRetorno, FormaDePagamento, PrecoFinal, QtdeAluguel, Cliente, Enfeite);

		Cliente = JOptionPane.showInputDialog("Informe o CPF/RNE ou nome do cliente: ");
		try {
			String arq = "ArquivoClientes.txt";
			if ( lerArquivos( arq, Cliente ) == true ) {	
				reservas.setCliente(Cliente);
			} else {
				JOptionPane.showMessageDialog(null, "Cliente nao localizado, faca o cadastro dele");
				Clientes.CadastrarClientes();
				reservas.setCliente(Cliente);
			}

			QtdeAluguel = BuscarQtdeReserva(Cliente);
			reservas.setQtdeAluguel(QtdeAluguel);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao buscar cliente!");
		}

		Enfeite = JOptionPane.showInputDialog("Informe o tema que deseja reservar: ");
		try {
			String arq = "ArquivoEnfeites.txt";
			if ( lerArquivos( arq, Enfeite ) == true ) {
				reservas.setEnfeite(Enfeite);
			} else {
				JOptionPane.showMessageDialog(null, "Tema nao localizado, faca o cadastro dele");
				Enfeites.CadastrarEnfeites();
				reservas.setEnfeite(Enfeite);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao buscar enfeite!");
		}

		try {
			PrecoFinal = CalcularDesconto(Cliente, Enfeite); 
			reservas.setPrecoFinal(PrecoFinal);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao calcular preco!");
		}

		FormaDePagamento = JOptionPane.showInputDialog("Informe a forma de pagamento: ");
		reservas.setFormaDePagamento(FormaDePagamento);

		reservas.setDataFesta(DataFesta);
		reservas.setHoraInicio(HoraInicio);
		reservas.setDataPrevista(DataPrevista);
		reservas.setHoraRetorno(HoraPrevisto);
		reservas.setPrecoFinal(PrecoFinal);
		
		if (inicio == null) {								
			NO_Reserva n = new NO_Reserva(reservas);	
			inicio = n;
			n.prox = null;
			n.anterior = null;									
		}  
		else {
			NO_Reserva aux = inicio;				
			while (aux.prox != null) {				
				aux = aux.prox;						
			} 
			NO_Reserva n = new NO_Reserva(reservas);		
			aux.prox = n;	
			n.anterior = aux;
			n.prox = null;
		} 
		GravarReserva();
		JOptionPane.showMessageDialog(null, "Reserva realizada e gravada com sucesso!");  
		System.out.println("Reserva realizada: \n" + 
							" Cliente: " + reservas.getCliente() + 
							" - Tema: " + reservas.getEnfeite() + 
							" - Forma de Pagamento: " + reservas.getFormaDePagamento() +
							" - Preco Final: " + reservas.getPrecoFinal() +
							" \n Data da Festa: " + reservas.getDataFesta() +
							" - Horario da Festa: " + reservas.getHoraInicio() +
							" \n Data de devolucao: " + reservas.getDataPrevista() +
							" - Horario de devolucao: " + reservas.getHoraPrevisto()+
							" - Quantidade de Aluguel: " + reservas.getQtdeAluguel());
	} 
	
	public void GravarReserva()  {
		NO_Reserva aux = inicio;
		
		try {
			String fileName = "ArquivoReserva.txt";	
		    BufferedWriter gravar = new BufferedWriter(new FileWriter( fileName ));	
		
			while (aux != null) {
				gravar.newLine(); 

				Cliente = aux.reservas.getCliente();
	            gravar.write(aux.reservas.getCliente()+", "); 

				Enfeite = aux.reservas.getEnfeite();
	            gravar.write(aux.reservas.getEnfeite()+", ");

				FormaDePagamento = aux.reservas.getFormaDePagamento();
	            gravar.write(aux.reservas.getFormaDePagamento()+", ");

				PrecoFinal = aux.reservas.getPrecoFinal();
	            gravar.write(String.valueOf(aux.reservas.getPrecoFinal())+", "); 
				
				DataFesta = aux.reservas.getDataFesta();
	            gravar.write(aux.reservas.getDataFesta().toString()+", "); 
				
				HoraInicio = aux.reservas.getHoraInicio();
	            gravar.write(String.valueOf(aux.reservas.getHoraInicio())+", ");

				DataPrevista = aux.reservas.getDataPrevista();
	            gravar.write(aux.reservas.getDataPrevista().toString()+", "); 

				HoraPrevisto = aux.reservas.getHoraPrevisto();
	            gravar.write(String.valueOf(aux.reservas.getHoraPrevisto())+", "); 

				QtdeAluguel = aux.reservas.getQtdeAluguel();
				gravar.write(String.valueOf(aux.reservas.getQtdeAluguel()));

				aux = aux.prox;
			}
		     gravar.close();  			
		} 
		catch (Exception e) {
			System.err.println("Ocorreu um erro na gravacao!");
		}  	// fim try-catch
	} // fim gravar  cliente

	public double CalcularDesconto(String Cliente, String Enfeite) throws IOException {
		double precoFinal = Double.parseDouble(BuscarPreco(Enfeite));
		int Qtde = BuscarQtdeReserva(Cliente);
		LocalDate Agora = LocalDate.now();
		LocalDate DataCadastro = LocalDate.parse(BuscarData(Cliente));
		Period periodo = Period.between(DataCadastro, Agora);
		int anos = periodo.getYears();

		if ( anos >= 2 || Qtde >= 3 ) {
			precoFinal = (precoFinal - (precoFinal * 0.10));
			return precoFinal;
		}
		return precoFinal;
	}
	
	public void ListarReservas() {
		String nome = "ArquivoReserva.txt"; 
		File arq = new File(nome);    

        if ( arq.exists() && arq.isFile() ) {
            try {
				FileInputStream fluxo = new FileInputStream(arq);
				InputStreamReader leitor = new InputStreamReader(fluxo);
				BufferedReader buffer = new BufferedReader(leitor);
				String linha = buffer.readLine();
				JOptionPane.showMessageDialog(null, "A lista sera apresentada no console");
				while (linha != null) {
					System.out.println(linha);
					linha = buffer.readLine();
				}
				
				buffer.close();
				leitor.close();
				fluxo.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Lista est� vazia!");
		}
	}
	
	public int BuscarQtdeReserva(String buscador) throws IOException {
		int cont = 0;
		String arquivo = "ArquivoReserva.txt"; 
		File arq = new File(arquivo); 

		if (arq.exists() && arq.isFile()) {
			FileInputStream fluxo = new FileInputStream(arq);
			InputStreamReader leitor = new InputStreamReader(fluxo);
			buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			cont = 1;
			
			while (linha != null) {
				String [] frase;
				frase = linha.split(", ");

				for (String palavra: frase) {
					if (palavra.equalsIgnoreCase(buscador)) {
						cont++;
					}
				}
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
		} else {
			cont++;
		}
		return cont;
	}	
	
	public void BuscarReserva( String buscador, String arquivo ) throws IOException {
		File arq = new File(arquivo); 

		if (arq.exists() && arq.isFile()) {
			FileInputStream fluxo = new FileInputStream(arq);
			InputStreamReader leitor = new InputStreamReader(fluxo);
			buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			
			while (linha != null) {
				String [] frase;
				frase = linha.split(", ");

				for (String palavra: frase) {
					if (palavra.equalsIgnoreCase(buscador)) {
						JOptionPane.showMessageDialog(null, "Reserva do Enfeite: "+frase[1] +
							", feita por: "+frase[0]+", no dia: "+frase[4]);
					}
				}
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
		} 
		else {
			throw new IOException("Arquivo Invalido");
		}
	}	

	public boolean lerArquivos(String arquivo, String buscador) throws IOException {
		File arq = new File(arquivo);
		boolean result = false;
		if (arq.exists() && arq.isFile()) {
			FileInputStream fluxo = new FileInputStream(arq);
			InputStreamReader leitor = new InputStreamReader(fluxo);
			buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			
			while (linha != null) {
				String [] frase;
				frase = linha.split(", ");

				for (String palavra: frase) {
					if (palavra.equalsIgnoreCase(buscador)) {
						JOptionPane.showMessageDialog(null, "Localizamos: "+frase[2]);
						return true;
					}
				}
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
		} else {
			throw new IOException("Arquivo Invalido");
		}
		return result;
	}

	public String BuscarData(String Cliente) throws IOException {
		String arquivo = "ArquivoClientes.txt"; 
		File arq = new File(arquivo); 
		String data = "";

		if (arq.exists() && arq.isFile()) {
			FileInputStream fluxo = new FileInputStream(arq);
			InputStreamReader leitor = new InputStreamReader(fluxo);
			buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();

			while (linha != null) {
				String [] frase;
				frase = linha.split(", ");

				for (String palavra: frase) {
					if (palavra.equalsIgnoreCase(Cliente)) {
						data = frase[5];
					}
				}
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
		} else {
			JOptionPane.showMessageDialog(null, "Erro ao buscar data de cadastro do cliente");
		}
		return data;
	}

	public String BuscarPreco(String Enfeite) throws IOException {
		String arquivo = "ArquivoEnfeites.txt"; 
		File arq = new File(arquivo); 
		String preco = "";

		if (arq.exists() && arq.isFile()) {
			FileInputStream fluxo = new FileInputStream(arq);
			InputStreamReader leitor = new InputStreamReader(fluxo);
			buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();

			while (linha != null) {
				String [] frase;
				frase = linha.split(", ");

				for (String palavra: frase) {
					if (palavra.equalsIgnoreCase(Enfeite)) {
						preco = frase[4];
					}
				}
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
		} else {
			JOptionPane.showMessageDialog(null, "Erro ao buscar preçodo enfeite");
		}
		return preco;
	}
}