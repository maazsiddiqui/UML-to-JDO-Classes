@javax.jdo.annotations.PersistenceCapable

public class HubNode extends Node {
	
	public HubNode(String ID, String address, Network network) {
		this.ID = ID;
		this.address = address;
		this.network = network;
	}

}
