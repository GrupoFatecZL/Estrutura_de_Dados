package cliente;

public class NO_Cliente {
	public Clientes clientes;
	public NO_Cliente inicio;
	public NO_Cliente prox;
	public NO_Cliente anterior;

	public NO_Cliente(Clientes clientes) {
		this.clientes = clientes;
		prox = null;
		anterior = null;
	}
	// ========================================================================================

	public NO_Cliente() {
	}

	public Clientes getCliente() {
		return clientes;
	}

	public void setCliente(Clientes clientes) {
		this.clientes = clientes;
	}

	public NO_Cliente getProx() {
		return prox;
	}

	public void setProx(NO_Cliente prox) {
		this.prox = prox;
	}

	public NO_Cliente getAnt() {
		return anterior;
	}

	public void setAnt(NO_Cliente anterior) {
		this.anterior = anterior;
	}

	public Clientes getNome() {
		return clientes;
	}

}