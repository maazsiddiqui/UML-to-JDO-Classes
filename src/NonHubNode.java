@javax.jdo.annotations.PersistenceCapable

public class NonHubNode extends Node {
	
	public NonHubNode(String ID, String address, Network network) {
		this.ID = ID;
		this.address = address;
		this.network = network;
	}

}