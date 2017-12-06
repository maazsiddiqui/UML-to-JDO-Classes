import java.util.HashSet;

@javax.jdo.annotations.PersistenceCapable

public abstract class Node {
	
	String ID;
	String address ;
	
	Network network;
	
	HashSet<UndirectedLink> undirectedLink = new HashSet<UndirectedLink>();
	
	HashSet<DirectedLink> directedLinkSource = new HashSet<DirectedLink>();
	HashSet<DirectedLink> directedLinkDestination = new HashSet<DirectedLink>();
	
	HashSet<BitemporalCell> bitemporalCell = new HashSet<BitemporalCell>();

}