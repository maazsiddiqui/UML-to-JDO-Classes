import javax.jdo.*;
import com.objectdb.Utilities;

public class CreateData {
	
	static PersistenceManager pm = Utilities.getPersistenceManager("network.odb");
	int linkID = 0;
	
	boolean constructUndirectedLink(Node n1, Node n2, Network network) {
		
		if ( !(linkable(n1, n2, network)) ) {
			System.out.println("Error: " + n1.ID + " is not linkable with " + n2.ID);
			return false;
		}
		
		UndirectedLink undirectedLink = new UndirectedLink(n1, n2, network, ++linkID);
		
		pm.currentTransaction().begin();
		pm.makePersistent(undirectedLink);
		pm.currentTransaction().commit();
		
		network.links.add(undirectedLink);
		n1.undirectedLink.add(undirectedLink);
		n2.undirectedLink.add(undirectedLink);

		return true;
	
	}	
	
	boolean linkable(Node n1, Node n2, Network network) {
		
		//	same network
		if ( network.nodes.contains(n1) && network.nodes.contains(n2) ) {
			return true;
		}
		
		//	different network and n1 is HubNode		
		else if ( n1 instanceof HubNode ) {
			
			// n1 can be linked to Hub/NonHub nodes of parent network
			if ( n2.network.ID.equals(n1.network.parentNetwork.ID) &&
			     (network.ID.equals(n1.network.parentNetwork.ID) || 
				  network.ID.equals(n1.network.ID)) ) {	
	
				return true;
			}
			
			// n1 can be linked to HubNode of its child networks
			if ( n2 instanceof HubNode && 
				 n1.network.ID.equals(n2.network.parentNetwork.ID) &&					
				 (n1.network.subNetworks.contains(network) ||
				  network.ID.equals(n1.network.ID)) ) {

				return true;
			}			
			
			// n1 can be linked to HubNodes of its parent's subnetworks
			if ( n2 instanceof HubNode &&
				 n2.network.parentNetwork.ID.equals(n1.network.parentNetwork.ID) &&
				 (network.ID.equals(n1.network.ID) ||
				  network.ID.equals(n2.network.ID) ||
				  network.ID.equals(n1.network.parentNetwork.ID) ||
				  network.ID.equals(n2.network.parentNetwork.ID)) ) {

				return true;				
			}
			
		}
		
		//	different network and n2 is HubNode	and n1 is NonHub node	
		else if ( n2 instanceof HubNode ) {
			
			// n2 can be linked to Hub/NonHub nodes of parent network
			if ( n1.network.ID.equals(n2.network.parentNetwork.ID) && 
				 (network.ID.equals(n2.network.parentNetwork.ID) || 
				  network.ID.equals(n2.network.ID)) ) {
		
					return true;
			}
			
			// n2 can be linked to HubNode of its child networks
			if ( n1 instanceof HubNode && 
				 n2.network.ID.equals(n1.network.parentNetwork.ID) &&					
				 (n2.network.subNetworks.contains(network) ||
				  network.ID.equals(n2.network.ID)) ) {
			
					return true;
			}
			
			// n2 can be linked to HubNodes of its parent's subnetworks
			if ( n1 instanceof HubNode &&
				 n1.network.parentNetwork.ID.equals(n2.network.parentNetwork.ID) &&
				 (network.ID.equals(n1.network.ID) ||
				  network.ID.equals(n2.network.ID) ||
				  network.ID.equals(n1.network.parentNetwork.ID) ||
				  network.ID.equals(n2.network.parentNetwork.ID)) ) {
					
				return true;				
			}
			
		}
		else 
			return false;
		
		return false;
		
	}

	public static void main(String args[]) {
		
		CreateData data = new CreateData();

		// 	networks
		Network network1 = new Network("N1", "network1");
		Network network2 = new Network("N2", "network2");
		Network network3 = new Network("N3", "network3");
		Network network4 = new Network("N4", "network4");
		
		network1.subNetworks.add(network2);
		network2.parentNetwork = network1;
		
		network1.subNetworks.add(network3);
		network3.parentNetwork = network1;
		
		network2.subNetworks.add(network4);
		network4.parentNetwork = network2;		
		
		//	non Hub Node	
		Node n1 = new NonHubNode("n1", "N1.1", network1);
		Node n2 = new NonHubNode("n2", "N1.2", network1);
		Node n3 = new NonHubNode("n3", "N2.1", network2);
		Node n6 = new NonHubNode("n6", "N3.2", network3);
		Node n8 = new NonHubNode("n8", "N4.2", network4);
		
		//	Hub Nodes
		Node n4 = new HubNode("n4", "N2.2", network2);
		Node n5 = new HubNode("n5", "N3.1", network3);
		Node n7 = new HubNode("n7", "N4.1", network4);
		
		network1.nodes.add(n1);
		network1.nodes.add(n2);
		network2.nodes.add(n3);
		network2.nodes.add(n4);
		network3.nodes.add(n5);
		network3.nodes.add(n6);
		network4.nodes.add(n7);
		network4.nodes.add(n8);
		
		//	persistence-by-reachability
		pm.currentTransaction().begin();
		pm.makePersistent(network1);
		pm.makePersistent(network2);
		pm.makePersistent(network3);
		pm.makePersistent(network4);
		pm.currentTransaction().commit();
		
		data.constructUndirectedLink(n1, n2, network1);
		data.constructUndirectedLink(n2, n4, network1);
		data.constructUndirectedLink(n4, n5, network1);
		data.constructUndirectedLink(n3, n4, network2);
		data.constructUndirectedLink(n4, n7, network2);
		data.constructUndirectedLink(n5, n6, network3);
		data.constructUndirectedLink(n7, n8, network4);
				
		//	error links for testing
		data.constructUndirectedLink(n1, n3, network1);
		data.constructUndirectedLink(n2, n6, network1);
		data.constructUndirectedLink(n3, n8, network2);
		data.constructUndirectedLink(n3, n6, network2);
		data.constructUndirectedLink(n5, n7, network3);
		data.constructUndirectedLink(n6, n8, network4);		
	
	}	

}
