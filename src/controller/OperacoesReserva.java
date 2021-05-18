package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import cliente.Clientes;
import cliente.NO_Cliente;
import cliente.OperacoesClientes;
import enfeite.Enfeites;
import enfeite.NO_Enfeite;
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
    private String Enfeite;

	private int codTema;
	private String tema;
	private String descricao;
	private double preco;

	private String CPF_RNE;
	private String Nome;
	private String Telefone;
	private String Endereco;
	private LocalDate DataCadastro = LocalDate.now();
	private int QtdeAluguel;

	private NO_Reserva inicio;

	Clientes cliente = new Clientes(CPF_RNE, Nome, Endereco, Telefone, DataCadastro, QtdeAluguel);
	Enfeites enfeite = new Enfeites(codTema, tema, descricao, preco);
	
	NO_Cliente NoCliente = new NO_Cliente(cliente);
	NO_Enfeite NoEnfeite = new NO_Enfeite(enfeite);

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
			"\n2- Realizar uma devolução"+
			"\n3- Remover uma devolução"+
			"\n4- Consultar reservas"+
			"\n9- Voltar  "));
			
			switch (opcao) {
				case 1:
					RealizarReserva();
				break;

				case 2:	
                    JOptionPane.showMessageDialog(null, "Em desenvolvimento ... ");
					//RealizarDevolucao()
				break;

				case 3:	
                    JOptionPane.showMessageDialog(null, "Em desenvolvimento ... ");
					//RemoverReserva()
				break;

				case 4:	
                    JOptionPane.showMessageDialog(null, "Em desenvolvimento ... ");
					//ConsultarReserva()
				break;

				case 9:
					JOptionPane.showMessageDialog(null, "Voltando ao menu anterior");
				break;
				default:
				break;
			} // fim switch
		} // fim while
	} // fim Menu Reserva
    
	public void RealizarReserva() {
		Reserva reservas = new Reserva(DataFesta, DataPrevista, DataRetorno, HoraInicio, HoraPrevisto, HoraRetorno, FormaDePagamento, PrecoFinal, Cliente, Enfeite);

		Cliente = JOptionPane.showInputDialog("Informe o CPF ou RNE do cliente: ");
		try {
			if ( Clientes.BuscaParaReserva(Cliente) == true ) {
				//Precisamos pegar a quantidade de aluguel do cliente e somar com mais um	
				reservas.setCliente(Cliente);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cliente não localizado, faça o cadastro dele");
			Clientes.CadastrarClientes();
		}

		Enfeite = JOptionPane.showInputDialog("Informe o tema que deseja reservar: ");
		try {
			if ( Enfeites.BuscarEnfeites(Enfeite) == true ) {
				reservas.setEnfeite(Enfeite);
				//CalcularDesconto(PrecoFinal); //Precisamos pegar o preço do tema e CalcularDesconto(PrecoFinal);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Tema não localizado, faça o cadastro dele");
			Enfeites.CadastrarEnfeites();
		}

		FormaDePagamento = JOptionPane.showInputDialog("Informe a forma de pagamento: ");
		reservas.setFormaDePagamento(FormaDePagamento);

		reservas.setDataFesta(DataFesta);
		reservas.setHoraInicio(HoraInicio);
		reservas.setDataPrevista(DataPrevista);
		reservas.setHoraRetorno(HoraPrevisto);
		reservas.setPrecoFinal(PrecoFinal);
		
		if (inicio == null) {								// verifica se a lista esta vazia
			NO_Reserva n = new NO_Reserva(reservas);	
			inicio = n;
			n.prox = null;
			n.anterior = null;									
		}  // fim if
		else {
			NO_Reserva aux = inicio;				
			while (aux.prox != null) {					// buscando o ultimo elemento da lista	
				aux = aux.prox;						
			} // fim while
			NO_Reserva n = new NO_Reserva(reservas);		// cria um novo Nó
			aux.prox = n;	
			n.anterior = aux;
			n.prox = null;
		} // fim do else
		GravarReserva();
		JOptionPane.showMessageDialog(null, "Reserva realizada e gravada com sucesso!");  
		System.out.println("Reserva realizada: \n" + 
							" Cliente: " + reservas.getCliente() + 
							" - Tema: " + reservas.getEnfeite() + 
							" - Forma de Pagamento: " + reservas.getFormaDePagamento() +
							" - Preço Final: " + reservas.getPrecoFinal() +
							" \n Data da Festa: " + reservas.getDataFesta() +
							" - Horário da Festa: " + reservas.getHoraInicio() +
							" \n Data de devolução: " + reservas.getDataPrevista() +
							" - Horário de devolução: " + reservas.getHoraPrevisto());
	} // fim cadastro cliente
	
	public void GravarReserva()  {
		NO_Reserva aux = inicio;
		
		try {
			String fileName = "ArquivoReserva.txt";	
		    BufferedWriter gravar = new BufferedWriter(new FileWriter( fileName ));	
		
			while (aux != null) {
	            gravar.write("** Nova reserva: "); 
				gravar.newLine();

				Cliente = aux.reservas.getCliente();
	            gravar.write(aux.reservas.getCliente()); 
				gravar.newLine();

				Enfeite = aux.reservas.getEnfeite();
	            gravar.write(aux.reservas.getEnfeite()); 
				gravar.newLine();

				FormaDePagamento = aux.reservas.getFormaDePagamento();
	            gravar.write(aux.reservas.getFormaDePagamento()); 
				gravar.newLine();

				PrecoFinal = aux.reservas.getPrecoFinal();
	            gravar.write(String.valueOf(aux.reservas.getPrecoFinal())); 
				gravar.newLine();
				
				DataFesta = aux.reservas.getDataFesta();
	            gravar.write(aux.reservas.getDataFesta().toString()); 
				gravar.newLine();
				
				HoraInicio = aux.reservas.getHoraInicio();
	            gravar.write(String.valueOf(aux.reservas.getHoraInicio())); 
				gravar.newLine();

				DataPrevista = aux.reservas.getDataPrevista();
	            gravar.write(aux.reservas.getDataPrevista().toString()); 
				gravar.newLine();
				
				HoraPrevisto = aux.reservas.getHoraPrevisto();
	            gravar.write(String.valueOf(aux.reservas.getHoraPrevisto())); 
				gravar.newLine();

				aux = aux.prox;
			}
		     gravar.close();  			
		} 
		catch (Exception e) {
			System.err.println("Ocorreu um erro na gravação!");
		}  	// fim try-catch
	} // fim gravar  cliente

	public void RealizarDevolucao() {

	}

	public void RemoverReserva() {

	}

	public void ConsultarReserva() {

	}

	public double CalcularDesconto(double PrecoFinal) {

		return PrecoFinal;
	}
}