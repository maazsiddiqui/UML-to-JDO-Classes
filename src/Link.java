import java.util.HashSet;

@javax.jdo.annotations.PersistenceCapable

public abstract class Link {
	
	String ID;
	
	HashSet<BitemporalCell> BitemporalCell = new HashSet<BitemporalCell>();
	
	Network network;
	
	

}