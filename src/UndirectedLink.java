
@javax.jdo.annotations.PersistenceCapable

public class UndirectedLink extends Link {

	Node endNode1;
	Node endNode2;
		
	public UndirectedLink(Node n1, Node n2, Network network, int id) {
		endNode1 = n1;
		endNode2 = n2;
		this.network = network;
		this.ID = "L" + Integer.toString(id);
	}

}