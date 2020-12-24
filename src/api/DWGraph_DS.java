package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DWGraph_DS implements directed_weighted_graph{

	// hashmap to store all nodes of the graph
	private HashMap<Integer, node_data> g;
	// hashmap that the key is the src and the value is another hashmap that contains key as dest and values as EdgeData
	private HashMap<Integer,HashMap<Integer,edge_data>> Edges;
	int edgeSize;
	int MC;
	
    public DWGraph_DS() {
    	  g = new HashMap<Integer, node_data>();
    	  Edges = new HashMap<Integer,HashMap<Integer,edge_data>>();
    	  this.edgeSize =0;
    	  this.MC=0;
    }
    
    /**
	 * returns the node_data by the node_id
	 */
    @Override
    public node_data getNode(int key) {
    	return this.g.get(key);

    }
    /**
	 * returns the data of the edge (src,dest), null if none.
	 */
    @Override
    public edge_data getEdge(int src, int dest) {
    	//check if src and dest even exist in the graph
    	if(this.g.containsKey(src) || this.g.containsKey(dest)) {
    		if(this.Edges.get(src) !=null && src != dest)
    			return this.Edges.get(src).get(dest);
    	}
    	return null;
    }
	/**
	 * adds a new node to the graph with the given node_data.
	 */
	@Override
	public void addNode(node_data n) {
		//check if the node is already exist
		if(!(this.g.containsKey(n.getKey())))
		{
			this.g.put(n.getKey(), n);
			this.Edges.put(n.getKey(), new HashMap<Integer, edge_data>());
			MC+=1;
		}
	}
	/**
	 * Connects an edge with weight w between node src to node dest.
	 */
	@Override
	public void connect(int src, int dest, double w) {
		edge_data edge = new EdgeData(src,dest,w);
		//check if the nodes exists in hashmap
		if (!(!this.g.containsKey(src) || !this.g.containsKey(dest) || src == dest)) {
			//check if they are already neighbors
			if(this.Edges.get(src).containsKey(dest)) {
				//first remove the exist connecting between them so i can Update the Weight
				this.Edges.get(src).remove(dest);
				//Update the Weight
				this.Edges.get(src).put(dest, edge);
			}
			else {
				this.Edges.get(src).put(dest, edge);
				MC+=1;
				edgeSize +=1;
			}
		}
	}
	/**
	 * This method returns a pointer (shallow copy) for the
	 * collection representing all the nodes in the graph. 
	 */
	@Override
	public Collection<node_data> getV() {
		//return all nodes from the graph
		return this.g.values();
	}
	/**
	 * This method returns a pointer (shallow copy) for the
	 * collection representing all the edges getting out of 
	 * the given node (all the edges starting (source) at the given node). 
	 */
	@Override
	public Collection<edge_data> getE(int node_id) {
		node_data n = getNode(node_id);
		//check if node even exist in hashmap
		if(n == null || this.Edges.get(node_id) == null) 
			return null;
		return this.Edges.get(node_id).values();
	}
	/**
	 * Deletes the node (with the given ID) from the graph -
	 * and removes all edges which starts or ends at this node.
	 */
	@Override
	public node_data removeNode(int key) {
		int edgeCount=0;
		node_data node = getNode(key);
		//remove from edge hashmap and also update size of edges
		if(this.Edges.get(key) !=null) {
			edgeCount = this.Edges.get(key).values().size();
			edgeSize-=edgeCount; // Update Edge Size
			this.Edges.remove(key);
		}
		Iterator it = this.Edges.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			if(this.Edges.get(pair.getKey()).containsKey(key)) {
				removeEdge((int) pair.getKey(), key);
			}
		}
		//remove from nodes hashmap
		this.g.remove(key);
		return node;
	}
	/**
	 * Deletes the edge from the graph,
	 */
	@Override
	public edge_data removeEdge(int src, int dest) {
		edge_data edgeExist= getEdge(src, dest);
		//check if nodes even exist
		if (!(!this.g.containsKey(src) || !this.g.containsKey(dest))) {
			//Remove edge for both nodes
			if(edgeExist != null) {
				this.Edges.get(src).remove(dest);
				edgeSize-=1;
				MC+=1;
			}
		}
		return edgeExist;
	}
	/** Returns the number of vertices (nodes) in the graph.
	 */
	@Override
	public int nodeSize() {
		//return the size of the nodes in the graph
		return this.g.size();
	}
	/** 
	 * Returns the number of edges.
	 */
	@Override
	public int edgeSize() {
		// return the edges size in the graph
		return edgeSize;
	}
	/**
	 * Returns the Mode Count - for testing changes in the graph.
	 */
	@Override
	public int getMC() {
		//return mc
		return this.MC;
	}


}
