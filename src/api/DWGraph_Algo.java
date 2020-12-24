package api;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class DWGraph_Algo implements dw_graph_algorithms{
	
	private directed_weighted_graph gr;
	
	public DWGraph_Algo() {
		 gr= new DWGraph_DS();
	}
    /**
     * Init the graph on which this set of algorithms operates on.
     */
	@Override
	public void init(directed_weighted_graph g) {
		//Set Graph
		this.gr=g;
	}
    /**
     * Return the underlying graph of which this class works.
     */
	@Override
	public directed_weighted_graph getGraph() {
		// return the graph that object graph_algo got
		return this.gr;
	}
    /**
     * Compute a deep copy of this weighted graph.
     */
	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph grC= new DWGraph_DS();

		//Define Copy of All node from This graph to Graph grC
		for (node_data key : this.gr.getV()) {
			NodeData k = new NodeData(key.getKey());
			grC.addNode(k);
		}
		//Connect All The Connected Node like Original Graph
		for (node_data key : grC.getV()) {
			for (edge_data k : this.gr.getE(key.getKey())) {
				grC.connect(key.getKey(), k.getDest(),k.getWeight());
			}
		}
		return grC;
	}
    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node.
     */
	@Override
	public boolean isConnected() {
		String v="Visited" , nv="Not_Visited";
		//Mark all the Nodes as not visited 
		for(node_data n : this.gr.getV()) {
			n.setInfo(nv);
		}
		//To get Randomally First Node of The Graph
		Iterator<node_data> it = this.gr.getV().iterator();
		node_data firstNode = it.next();
        //DFSSC = DFS Strong Connection 
		//Algorithm traversal starting from first Node. 
        DFSSC(firstNode.getKey(),this.gr); 
  
        // If DFSSC traversal doesn't visit all Nodes, then 
        // return false. 
        for(node_data n : this.gr.getV()) {
        	if(n.getInfo().equals(nv))
        		return false;
		}
  
        //Create a reversed graph 
        directed_weighted_graph gT = getTranspose(); 
  
        //Mark all the Nodes as not visited
        for(node_data n: gT.getV()) {
        	n.setInfo(nv);
        }
  
        //Do DFSSC for reversed graph starting from " first Node "
        DFSSC(firstNode.getKey(), gT);
  
        // If all Nodes are not visited in second DFSSC, then 
        // return false 
        for(node_data n : gT.getV()) {
        	if(n.getInfo().equals(nv))
        		return false;
		}
        return true;
	}
    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     */
	@Override
	public double shortestPathDist(int src, int dest) {
		// predecessor[i] array stores predecessor of
		// i and distance array stores distance of i
		int max = maxvalue(gr);
		//check if there is a path between these two nodes
		if(this.gr.getNode(src) == null || this.gr.getNode(dest) == null)
			return -1;
		double prev[] = new double[max+1];
		double dist[] = new double[max+1];
		//bfs algorithm that check the shortest path between these two nodes
		if (BFS(this.gr, src,dest,max+1, prev, dist) == false) {
			return -1;
		}
		return dist[dest];
	}
    /**
     * returns the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * Note if no such path --> returns null;
     */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// predecessor[i] array stores predecessor of
		// i and distance array stores distance of i
		int max = maxvalue(gr);
		//check if there is a path between these two nodes
		if(this.gr.getNode(src) == null || this.gr.getNode(dest) == null)
			return null;
		double prev[] = new double[max+1];
		double dist[] = new double[max+1];
		//bfs algorithm that check the shortest path between these two nodes
		List<node_data> s =BFSPrev(this.gr, src,dest,max+1, prev, dist);

		return s;
	}
    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     */
	@Override
	public boolean save(String file) {
		//Make JSON
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this.gr);

		//Write JSON to file
		try 
		{
			PrintWriter pw = new PrintWriter(new File(file));
			pw.write(json);
			pw.close();
			return true;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     */
	@Override
	public boolean load(String file) {
		try 
		{
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(DWGraph_DS.class, new graphJsonDeserializer());
			Gson gson = builder.create();			
			//continue as usual.. 

			FileReader reader = new FileReader(file);
			DWGraph_DS graph = gson.fromJson(reader, DWGraph_DS.class);
			init(graph);
			return true;
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	//DFSSC = DFS Strong Connection 
	// A recursive function to print DFS starting from (Node->key = v)
	public void DFSSC(int v , directed_weighted_graph g) 
    {
    	String vi="Visited" , nvi="Not_Visited";
        // Mark the current node as visited and print it 
        g.getNode(v).setInfo(vi);
  
        edge_data n; 
  
        // Recur for all the Nodes adjacent to this Node 
        Iterator<edge_data> i = g.getE(v).iterator(); 
        while (i.hasNext()) 
        { 
            n = i.next(); 
            if (g.getNode(n.getDest()).getInfo().equals(nvi)) 
                DFSSC(n.getDest(),g); 
        } 
    }
	// Function that returns transpose of the graph 
    public directed_weighted_graph getTranspose() 
    { 
    	DWGraph_DS gTranspose = new DWGraph_DS();
    	
    	for(node_data n : this.gr.getV()) {
    		gTranspose.addNode(n);
    	}
    	
    	for(node_data n : this.gr.getV()) {
    		// Recur for all the Nodes adjacent to this Node 
            Iterator<edge_data> i = this.gr.getE(n.getKey()).iterator();
            while (i.hasNext()) {
            	edge_data e = new EdgeData();
            	e = i.next();
            	gTranspose.connect(e.getDest(), n.getKey(),e.getWeight());
            }
    	}
    	return gTranspose;
    }
    
    
    
    
    // a modified version of BFS that stores predecessor
    // of each vertex in array prev
    // and its distance from source in array dist
    private boolean BFS(directed_weighted_graph adj, int src, int dest, int v, double prev[], double dist[])
    {
    	int i=0;
    	double alt=0;
    	// a queue to maintain queue of vertices whose
    	//  graph is to be scanned as per normal
    	// BFS algorithm using LinkedList of node_data type
    	LinkedList<node_data> queue = new LinkedList<node_data>();
    	for(i=0;i<v;i++) {
    		dist[i] = Integer.MAX_VALUE;
    		prev[i] = -1;
    	}
    	// initially all vertices are unvisited
    	// so v[i] for all i is unvisited
    	// and as no path is yet constructed
    	// dist[i] for all i set to infinity
    	for(node_data n : adj.getV()) {
    		queue.add(n);
    	}

    	// now source is first to be visited and
    	// distance from source to itself should be 0
    	dist[src] = 0;

    	while (!queue.isEmpty()) {
    		int index = getIndex(dist,queue);
    		node_data u =gr.getNode(index);
    		queue.remove(u);
    		for (edge_data n : adj.getE(u.getKey())) {
    			if (queue.contains(adj.getNode(n.getDest()))) {
    				alt = dist[index] + n.getWeight();
    				if(alt < dist[n.getDest()]) {
    					dist[n.getDest()]= alt;
    					prev[n.getDest()] = u.getKey();
    				}
    			}
    		}
    	}
    	return true;
    }
    
    
    // a modified version of BFS that stores predecessor
    // of each vertex in array pred
    // and its distance from source in array dist
    private List<node_data> BFSPrev(directed_weighted_graph adj, int src, int dest, int v, double prev[], double dist[])
    {
    	double alt=0;
    	int i=0;
    	// a queue to maintain queue of vertices whose
    	//  graph is to be scanned as per normal
    	// BFS algorithm using LinkedList of node_data type
    	LinkedList<node_data> queue = new LinkedList<node_data>();
    	List<node_data> list = new LinkedList();
    	node_data[] listarray = new node_data[v];
    	for(i=0;i<v;i++) {
    		dist[i] = Integer.MAX_VALUE;
    		prev[i] = -1;
    	}
    	// initially all vertices are unvisited
    	// so v[i] for all i is unvisited
    	// and as no path is yet constructed
    	// dist[i] for all i set to infinity
    	for(node_data n : adj.getV()) {
    		queue.add(n);
    	}

    	// now source is first to be visited and
    	// distance from source to itself should be 0
    	dist[src] = 0;

    	while (!queue.isEmpty()) {
    		int index = getIndex(dist,queue);
    		node_data u =gr.getNode(index);
    		queue.remove(u);
    		for (edge_data n : adj.getE(u.getKey())) {
    			if (queue.contains(adj.getNode(n.getDest()))) {
    				alt = dist[index] + n.getWeight();
    				if(alt < dist[n.getDest()]) {
    					dist[n.getDest()]= alt;
    					prev[n.getDest()] = u.getKey();
    				}
    			}
    		}
    	}
    	i=0;
    	node_data u=gr.getNode(dest);
    	if(prev[u.getKey()] != -1 || u.getKey()==src)
    		while(u != null) {
    			listarray[i++]=u;
    			u = gr.getNode((int)prev[u.getKey()]);
    		}
    	if(i==1)
    		return null;
    	while(--i>=0) {
    		list.add(listarray[i]);
    	}
    	return list;
    }
    
    
	//function to get the max value of nodes in a graph
	private int getIndex(double dist[],LinkedList<node_data> list) {
		int index=0,i;
		double min = Integer.MAX_VALUE;
		//loop over all the dist array
		for(i=0;i<dist.length;i++) {
			if(dist[i]<min && list.contains(this.gr.getNode(i))) {
				//if there is < min , save the value , and the index
				min=dist[i];
				index =i;
			}
		}
		return index;
			
	}
	
	
	//function to get the max value of nodes in a graph
	private int maxvalue(directed_weighted_graph gg) {
		int max=0;
		for(node_data e : gg.getV()) {
			if(e.getKey() > max)
				max= e.getKey();
		}
		return max;	
	}
    
    
	//function to compare between two graphs
	public boolean equalsGraph(directed_weighted_graph obj)   
	{
		boolean flag; // to tell if i found a specific node into the other graph
		//check size if not equals there is no need to go to loop
		if(obj.nodeSize() != this.gr.nodeSize())
			return false;
		//loop to get over all the nodes
		for(node_data it1: obj.getV()) {
			flag=false;
			for(node_data it2: this.gr.getV()) {
				if(it1.getKey() == it2.getKey())
					flag=true;
			}// check if the graph contain it1
			if(flag==false)
				return false;
		}//else return true
		return true;
	}
    
    
    
    

}
