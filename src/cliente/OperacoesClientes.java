package cliente;

import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class OperacoesClientes {
	private String CPF_RNE;
	private String Nome;
	private String Telefone;
	private String Endereco;
	private LocalDate DataCadastro = LocalDate.now();
	private NO_Cliente inicio;

	public OperacoesClientes() {
		inicio = null;
	}

	public void MenuClientes() {
		int opcao = 0;
		while (opcao != 9) {

			opcao = Integer.parseInt(JOptionPane.showInputDialog("Menu de Clientes: \n " + 
					"\n1- Cadastrar Cliente" + 
					"\n2- Remover cliente da lista" + 
					"\n3- Buscar cliente por CPF ou RNE" + 
					"\n4- Listar clientes" + 
					"\n5- Ordenar Clientes por nome (QuickSort)" + 
					"\n6- Ordenar Clientes por nome (MergeSort)"
					+ "\n9- Voltar  "));

			switch (opcao) {
			case 1:
				CadastrarClientes();
				break;

			case 2:
				int posicao = Integer.parseInt(JOptionPane.showInputDialog("Digite a posicao a ser removida: "));
				System.out.println(RemoverClientes(posicao));
				break;

			case 3:
				CPF_RNE = JOptionPane.showInputDialog("Digite o CPF do cliente que deseja buscar: ");
				BuscarClientes(CPF_RNE);
				break;

			case 4:
				ListarClientes();
				break;

			case 5:
				Clientes[] vet1 = null;
				Clientes[] vet = converteNoVetor(vet1);
				quickSort(vet, 0, vet.length - 1);
				JOptionPane.showMessageDialog(null, "A lista sera mostrada no console");
				for (int j = 0; j < vet.length; j++) {
					System.out.println("\n CPF_RNE: " + vet[j].getCPF_RNE() + " - Nome: " + vet[j].getNome()
							+ " - Endereco: " + vet[j].getEndereco() + " - Telefone: " + vet[j].getTelefone()
							+ " - Data Cadastro: " + vet[j].getDataCadastro());
				}
				break;
			case 6:
				ordenarMergeSort();
				PercorrerLista();
			break;

			case 9:
				JOptionPane.showMessageDialog(null, "Voltando ao menu anterior");
			break;

			default:
				JOptionPane.showMessageDialog(null, "Opcao invalida");
			} // fim switch
		} // fim while
	} // fim MenuClientes()

	public boolean ListaVazia() {
		if (inicio == null) {
			return true;
		}
		return false;
	}

	public void PercorrerLista() {
		if (ListaVazia() == true) {
			JOptionPane.showConfirmDialog(null, "A lista esta vazia!");
		} else {
			NO_Cliente aux = inicio;
			JOptionPane.showMessageDialog(null, "A lista sera mostrada no console");
			while (aux != null) {
			
				System.out.println("\n- CPF_RNE: " + aux.clientes.getCPF_RNE() + " - Nome: " + aux.clientes.getNome()
						+ " - Endere???o: " + aux.clientes.getEndereco() + " Telefone: " + aux.clientes.getTelefone());
				aux = aux.prox;
			}
		}
	} // fim percorrer a lista

	public int Contar() {
		int count = 1;
		NO_Cliente aux = inicio;
		while (aux.prox != null) {
			count++;
			aux = aux.prox;
		}
		System.out.println(count);
		return count;
	}

	public NO_Cliente mergeSort(NO_Cliente clientes) {
		if (clientes == null || clientes.getProx() == null) {
			return clientes;
		}
		NO_Cliente meio = getNoMeio(clientes);
		NO_Cliente segundaMetade = meio.getProx();
		meio.setProx(null);
		segundaMetade.setAnt(null);

		return merge(mergeSort(clientes), mergeSort(segundaMetade));

	}

	private NO_Cliente getNoMeio(NO_Cliente clientes) {
		NO_Cliente a = clientes;
		NO_Cliente b = clientes.getProx();

		while (b != null && b.getProx() != null) {
			a = a.getProx();
			b = b.getProx().getProx();
		}
		return a;
	}

	private NO_Cliente merge(NO_Cliente primeiro, NO_Cliente segundo) {
		NO_Cliente aux = new NO_Cliente();

		NO_Cliente lista = aux;

		while (primeiro != null && segundo != null) {
			if (primeiro.clientes.getNome().compareToIgnoreCase(segundo.clientes.getNome()) <= 0) {

				aux.setProx(primeiro);
				primeiro = primeiro.getProx();
			} else {
				aux.setProx(segundo);
				segundo = segundo.getProx();
			}
			aux = aux.getProx();
		}
		if (primeiro == null)
			aux.setProx(segundo);
		else
			aux.setProx(primeiro);

		return lista.getProx();
	}

	public void ordenarMergeSort() {
		NO_Cliente a = mergeSort(inicio);
		this.inicio = a;
	}

	// Metodo Obter tamanho da lista
	public int obterTamanho() {
		NO_Cliente aux = inicio;
		int contador = 0;
		while (aux != null) {
			contador = contador + 1;
			aux = aux.prox;
		}
		return contador;
	}

	// Metodo Converter lista em vetor
	public Clientes[] converteNoVetor(Clientes vet[]) {
		NO_Cliente aux = inicio;
		int cont = 0;
		int tamanho = obterTamanho();
		Clientes[] vet1 = new Clientes[tamanho];
		while (aux != null) {
			vet1[cont] = aux.clientes;
			aux = aux.prox;
			cont++;

		}
		return vet1;
	}

		// Metodo Ordenacao por quickSort
	public Clientes[] quickSort(Clientes vet[], int ini, int fim) {

		int divisao;
		if (ini < fim) {
			divisao = particao(vet, ini, fim);
			quickSort(vet, ini, divisao - 1);
			quickSort(vet, divisao + 1, fim);
		}
		return vet;
	}

	public int particao(Clientes[] vet, int ini, int fim) {
		Clientes pivo = vet[ini];
		int i = ini + 1, f = fim;
		Clientes aux;
		while (i <= f) {
			while (i <= fim && (vet[i].getNome().compareTo(pivo.getNome()) <= 0))
				++i;
			while ((pivo.getNome().compareTo(vet[f].getNome()) < 0))
				--f;
			if (i < f) {
				aux = vet[i];
				vet[i] = vet[f];
				vet[f] = aux;
				++i;
				--f;
			}
		}
		if (ini != f) {
			vet[ini] = vet[f];
			vet[f] = pivo;
		}
		return f;
	}// Fim quickSort


	
		// Metodo cadastrar Clientes
	public void CadastrarClientes() {
		Clientes cliente = new Clientes(CPF_RNE, Nome, Endereco, Telefone, DataCadastro);

		CPF_RNE = JOptionPane.showInputDialog("Digite CPF/RNE: ");
		cliente.setCPF_RNE(CPF_RNE);

		Nome = JOptionPane.showInputDialog("Informe o Nome do Cliente: ");
		cliente.setNome(Nome);

		Endereco = JOptionPane.showInputDialog("Informe o Endereco do Cliente: ");
		cliente.setEndereco(Endereco);

		Telefone = JOptionPane.showInputDialog("Informe o Telefone do Cliente: ");
		cliente.setTelefone(Telefone);

		cliente.setDataCadastro(DataCadastro);

		if (inicio == null) { // verifica se a lista esta vazia
			NO_Cliente n = new NO_Cliente(cliente);

			inicio = n;
			n.prox = null;
			n.anterior = null;
		} // fim if

		else {
			NO_Cliente aux = inicio;
			while (aux.prox != null) { // buscando o ultimo elemento da lista
				aux = aux.prox;
			} // fim while
			NO_Cliente n = new NO_Cliente(cliente); // cria um novo N???
			aux.prox = n;
			n.anterior = aux;
			n.prox = null;
		} // fim do else
		GravarCliente();
		JOptionPane.showMessageDialog(null, "Cliente cadastrado e gravado com sucesso!");
		System.out.println("Cliente Cadastrado: \n" + 
				" CPF_RNE: " + cliente.getCPF_RNE() + 
				" - Nome: "	+ cliente.getNome() + 
				" - Endereco: " + cliente.getEndereco() + 
				" - Telefone: " + cliente.getTelefone()+ 
				" - Data Cadastro: " + cliente.getDataCadastro());

	} // fim cadastro cliente

	public void GravarCliente() {
		NO_Cliente aux = inicio;

		try {
			String fileName = "ArquivoClientes.txt";
			BufferedWriter gravar = new BufferedWriter(new FileWriter(fileName));

			while (aux != null) {
				gravar.newLine();
				gravar.write("- Novo cliente, ");

				CPF_RNE = aux.clientes.getCPF_RNE();
				gravar.write(aux.clientes.getCPF_RNE() + ", ");

				Nome = aux.clientes.getNome();
				gravar.write(aux.clientes.getNome() + ", ");

				Endereco = aux.clientes.getEndereco();
				gravar.write(aux.clientes.getEndereco() + ", ");

				Telefone = aux.clientes.getTelefone();
				gravar.write(aux.clientes.getTelefone() + ", ");

				DataCadastro = aux.clientes.getDataCadastro();
				gravar.write(aux.clientes.getDataCadastro().toString() + ", ");

				aux = aux.prox;
			}
			gravar.close();
		} catch (Exception e) {
			System.err.println("Ocorreu um erro na gravacao!");
		} // fim try-catch
	} // fim gravar cliente

	public void ListarClientes() {
		if (inicio == null) {
			System.out.println("A lista esta vazia");
		} // if
		else {
			NO_Cliente aux = inicio; // cria??????o de duas variaveis
			JOptionPane.showMessageDialog(null, "A lista sera mostrada no console");
			while (aux != null) {
				System.out.println("\n CPF_RNE: " + aux.clientes.getCPF_RNE() + 
						" - Nome: " + aux.clientes.getNome() + 
						" - Endereco: " + aux.clientes.getEndereco() + 
						" - Telefone: " + aux.clientes.getTelefone() + 
						" - Data Cadastro: " + aux.clientes.getDataCadastro());
				aux = aux.prox;
			} // fim while
		} // fim else
	} // fim lista cliente

	public boolean BuscarClientes(String CPF_RNE) {
		NO_Cliente nodo = inicio;
		String aux = nodo.clientes.getCPF_RNE();
		try {
			while (nodo != null) {
				if (CPF_RNE.equalsIgnoreCase(aux)) {
					JOptionPane.showMessageDialog(null, "Cliente sera apresentado em console");
					System.out.println("CPF_RNE: " + nodo.clientes.getCPF_RNE() + 
						" - Nome: " + nodo.clientes.getNome() + 
						" - Endereco: " + nodo.clientes.getEndereco() + 
						" - Telefone: " + nodo.clientes.getTelefone() + 
						" - Data Cadastro: " + nodo.clientes.getDataCadastro());
					return true;
				}
				nodo = nodo.prox;
				aux = nodo.clientes.getCPF_RNE();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cliente nao localizado!");
			return false;
		}
		JOptionPane.showMessageDialog(null, "Cliente nao localizado!");
		return false;
	} // fim buscar cliente

	public String RemoverInicio() { // 6 remover no inico da lista
		String CPF_RNE = " "; // criar as variaveis
		String Nome = " ";
		String Endereco = " ";
		String Telefone = " ";
		LocalDate DataCadastro = LocalDate.of(1970, 03, 01);

		if (inicio == null) {
			JOptionPane.showConfirmDialog(null, "Lista esta vazia");
		} // fim inicio
		else {
			CPF_RNE = inicio.clientes.getCPF_RNE();
			Nome = inicio.clientes.getNome();
			Endereco = inicio.clientes.getEndereco();
			Telefone = inicio.clientes.getTelefone();
			DataCadastro = inicio.clientes.getDataCadastro();

			inicio = inicio.prox; // passar para inicio o ender???o do proximos
									// endere???o
			if (inicio != null) {
				inicio.anterior = null;
			}
		} // fim else
		return "CPF_RNE: " + CPF_RNE + 
				" - Nome: " + Nome + 
				" - Endereco: " + Endereco + 
				" - Telefone: " + Telefone + 
				" - Data Cadastro: " + DataCadastro;
	} // fim da classe Remove Inicio

	public String RemoveFinal() { // remover no final da lista
		String CPF_RNE = " "; // criar as variaveis
		String Nome = " ";
		String Endereco = " ";
		String Telefone = " ";
		LocalDate DataCadastro = LocalDate.of(1970, 03, 01);

		if (inicio == null) {
			JOptionPane.showConfirmDialog(null, "Lista esta vazia");
		} else {
			if (inicio.prox == null) { // inicio o primeiro elemento da
										// lista
				CPF_RNE = inicio.clientes.getCPF_RNE();
				Nome = inicio.clientes.getNome();
				Endereco = inicio.clientes.getEndereco();
				Telefone = inicio.clientes.getTelefone();
				DataCadastro = inicio.clientes.getDataCadastro();

				inicio = null; // informa que o ultimo elemento da lista
			} // fim IF
			else {
				NO_Cliente aux1 = inicio; // gerando duas varias, uma para
											// varrer a lista
				NO_Cliente aux2 = inicio;

				while (aux1.prox != null) {
					aux2 = aux1;
					aux1 = aux1.prox;
				}

				NO_Cliente aux = LocalizaDadoRemocaoFim(inicio, inicio);

				CPF_RNE = aux.clientes.getCPF_RNE();
				Nome = aux.clientes.getNome();
				Endereco = aux.clientes.getEndereco();
				Telefone = aux.clientes.getTelefone();
				DataCadastro = aux.clientes.getDataCadastro();

				aux1.anterior = null;
				aux2.prox = null; // coloca null para mostrar o fim da lista.

			} // fim else
		} // fim else
		return "CPF_RNE: " + CPF_RNE + 
				" - Nome: " + Nome + 
				" - Endereco: " + Endereco + 
				" - Telefone: " + Telefone + 
				" - Data Cadastro: " + DataCadastro;
	} // fim remover no final

	public NO_Cliente LocalizaDadoRemocaoFim(NO_Cliente aux1, NO_Cliente aux2) {
		if (aux1.prox != null) {
			return LocalizaDadoRemocaoFim(aux1.prox, aux1);
		}
		return aux2;
	}

	public String RemoverClientes(int posicao) {
		String CPF_RNE = " "; // criar as variaveis
		String Nome = " ";
		String Endereco = " ";
		String Telefone = " ";
		LocalDate DataCadastro = LocalDate.of(1970, 03, 01);

		int i = 1;

		NO_Cliente aux = inicio; // criar um endere???amento aux com valor
									// inicial

		if (inicio == null) {
			JOptionPane.showConfirmDialog(null, "Lista esta vazia !");
			CPF_RNE = inicio.clientes.getCPF_RNE();
			Nome = inicio.clientes.getNome();
			Endereco = inicio.clientes.getEndereco();
			Telefone = inicio.clientes.getTelefone();
			DataCadastro = inicio.clientes.getDataCadastro();

			return "CPF_RNE: " + CPF_RNE + 
					" - Nome: " + Nome + 
					" - Endereco: " + Endereco + 
					" - Telefone: " + Telefone + 
					" - Data Cadastro: " + DataCadastro;
		} // fim IF

		if (posicao == 1) { // remo??????o pos = 1, remo??????o ser??? no inicio
							// da lista
			CPF_RNE = aux.clientes.getCPF_RNE();
			Nome = aux.clientes.getNome();
			Endereco = aux.clientes.getEndereco();
			Telefone = aux.clientes.getTelefone();
			DataCadastro = aux.clientes.getDataCadastro();

			RemoverInicio();
			return "CPF_RNE: " + CPF_RNE + 
					" - Nome: " + Nome + 
					" - Endereco: " + Endereco + 
					" - Telefone: " + Telefone + 
					" - Data Cadastro: " + DataCadastro;
		} // Fim IF
		else {
			while (aux.prox != null) { // remover no final da lista
				aux = aux.prox; // guarda no aux o endere???o do proximo da
								// posi??????o
				i++; // vai guardando os posi???oes ate encontral null
			} // fim While
			if (posicao > i || posicao <= 0) { // posicoes invalidas
				JOptionPane.showConfirmDialog(null, "Posicao invalida");
				return "CPF_RNE: " + CPF_RNE + 
					" - Nome: " + Nome + 
					" - Endereco: " + Endereco + 
					" - Telefone: " + Telefone + 
					" - Data Cadastro: " + DataCadastro;
			} // fim IF
			else if (posicao == i) { // Remo??????o no final
				CPF_RNE = aux.clientes.getCPF_RNE();
				Nome = aux.clientes.getNome();
				Endereco = aux.clientes.getEndereco();
				Telefone = aux.clientes.getTelefone();
				DataCadastro = aux.clientes.getDataCadastro();

				RemoveFinal();
				return "CPF_RNE: " + CPF_RNE + 
					" - Nome: " + Nome + 
					" - Endereco: " + Endereco + 
					" - Telefone: " + Telefone + 
					" - Data Cadastro: " + DataCadastro;
			} // fim else
			else { // remover qualquer posi??????o
				aux = inicio; // carrega aux com inicio
				NO_Cliente aux2 = aux; // cria endere???amenteo aux 2 e copia
										// aux

				while (posicao > 1) {
					aux2 = aux;
					aux = aux.prox;
					posicao--;
				} // while

				CPF_RNE = aux.clientes.getCPF_RNE();
				Nome = aux.clientes.getNome();
				Endereco = aux.clientes.getEndereco();
				Telefone = aux.clientes.getTelefone();
				DataCadastro = aux.clientes.getDataCadastro();

				aux2.prox = aux.prox;
				aux.prox = aux2;
				aux.prox = null;
				aux.anterior = null;

				return "CPF_RNE: " + CPF_RNE + 
					" - Nome: " + Nome + 
					" - Endereco: " + Endereco + 
					" - Telefone: " + Telefone + 
					" - Data Cadastro: " + DataCadastro;
			} // fim else
		} // fim else
	} // fim metodo escolher remover
} // fim classe