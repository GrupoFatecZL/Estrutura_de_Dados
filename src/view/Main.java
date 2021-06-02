package view;

/*
Elen Carvalho de Oliveira - RA: 1110482013042
Luiz Antonio de Arruda - RA: 1110482013040
Vagner da Silva Feitosa  RA:1110481923033
Wesley Silva de Assis - RA: 1110482013028
*/

import javax.swing.JOptionPane;
import cliente.OperacoesClientes;
import devolucao.OperacoesDevolucao;
import enfeite.OperacoesEnfeite;
import reserva.OperacoesReserva;

public class Main {
	public static void main(String[] args) {		

		OperacoesEnfeite enfeites = new OperacoesEnfeite();
		OperacoesClientes clientes = new OperacoesClientes();
		OperacoesReserva reserva = new OperacoesReserva();
		OperacoesDevolucao devolucao = new OperacoesDevolucao();

		int opcao = 0;
		
		while (opcao != 9) {
			opcao = Integer.parseInt(JOptionPane.showInputDialog("Buffet: \n\n"+ 
							"1 - Cadastrar Enfeites \n"+ 
							"2 - Cadastrar Clientes\n"+ 
							"3 - Realizar Reserva \n"+ 
							"4 - Realizar Devolucao \n"+
							"9 - Sair "));
			
			switch (opcao) {
				case 1:
					enfeites.MenuEnfeites();
				break;

				case 2:
					clientes.MenuClientes();
				break;

				case 3:
					reserva.MenuReservar();
				break;

				case 4:
					devolucao.MenuDevolucao();
				break;
				
				case 9:
					JOptionPane.showMessageDialog(null, "Finalizando o programa");
				break;
					
				default:
					JOptionPane.showMessageDialog(null, "Opcao invalida");
			} // fim switch
		} // fim while
    } //fim main
} // fim classe