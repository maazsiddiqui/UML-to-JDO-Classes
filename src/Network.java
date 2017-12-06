import java.util.HashSet;

@javax.jdo.annotations.PersistenceCapable

public class Network {

	String ID;
	String name;
		
	HashSet<Organization> organization = new HashSet<Organization>();
	
	HashSet<Network> subNetworks = new HashSet<Network>();
	
	Network parentNetwork;
	
	HashSet<Link> links = new HashSet<Link>();
	HashSet<Node> nodes = new HashSet<Node>();
	
	
	public Network(String ID, String name) {
		this.ID = ID;
		this.name = name;
	}
	
}