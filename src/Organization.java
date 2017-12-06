import java.util.HashSet;

@javax.jdo.annotations.PersistenceCapable

public class Organization {
	
	String name;
	HashSet<Network> network = new HashSet<Network>();
	
	public Organization(String name) {
		this.name = name;
	}
	
	public Organization(String name, HashSet<Network> network) {
		this.name = name;
		this.network = network;
	}

	public void operates(Network network) {
		this.network.add(network);
	}
	
}