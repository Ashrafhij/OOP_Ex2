package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.*
;class DWGraph_AlgoTest {

	@Test
	void isConnected() {
		//init a graph
    	directed_weighted_graph g = new DWGraph_DS();
        //add some nodes
    	NodeData n1 = new NodeData(10);
    	NodeData n2 = new NodeData(15);
    	NodeData n3 = new NodeData(20);
    	NodeData n4 = new NodeData(42);
    	//add some node with similar key
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        //add connection
        g.connect(10,15,3);
        g.connect(15,42,10);
        g.connect(42,15,20);
        g.connect(42,20,15);
		dw_graph_algorithms ag1 = new DWGraph_Algo();
		ag1.init(g);
		//should Not be connected graph
		assertFalse(ag1.isConnected());
		//Should return Strongly Connected Graph = True
		g.connect(10, 42, 1);
		g.connect(20, 10, 12);
		assertTrue(ag1.isConnected());
		//if remove this Edge So There is no other way from 15 -> 42 , Should return False Connection
		g.removeEdge(15, 42);
		assertFalse(ag1.isConnected());
	}
	
	@Test
	void shortestPathDist() {
		//init a graph
    	directed_weighted_graph g = new DWGraph_DS();
        //add some nodes
    	NodeData n1 = new NodeData(10);
    	NodeData n2 = new NodeData(15);
    	NodeData n3 = new NodeData(20);
    	NodeData n4 = new NodeData(42);
    	//add some node with similar key
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        //add connection
        g.connect(10,15,3);
        g.connect(15,42,10);
        g.connect(42,15,20);
        g.connect(42,20,15);
        g.connect(10, 42, 1);
		g.connect(20, 10, 12);
		
		dw_graph_algorithms ag1 = new DWGraph_Algo();
		ag1.init(g);
		//Should return Strongly Connected Graph = True
		assertTrue(ag1.isConnected());
		double d = ag1.shortestPathDist(10,42);
		assertEquals(d, 1);
		//changed for a higher weight so he should return another path
		g.connect(10, 42, 22);
		d = ag1.shortestPathDist(10,42);
		assertEquals(d, 13);
	}
	
	@Test
	void shortestPath() {
		//init a graph
		directed_weighted_graph g = new DWGraph_DS();
		//add some nodes
		NodeData n1 = new NodeData(10);
		NodeData n2 = new NodeData(15);
		NodeData n3 = new NodeData(20);
		NodeData n4 = new NodeData(42);
		//add some node with similar key
		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		g.addNode(n4);
		//add connection
		g.connect(10,15,3);
		g.connect(15,42,10);
		g.connect(42,15,20);
		g.connect(42,20,15);
		g.connect(10, 42, 15);
		g.connect(20, 10, 12);

		dw_graph_algorithms ag1 = new DWGraph_Algo();
		ag1.init(g);
		//check 
		List<node_data> l1 = ag1.shortestPath(10,42);
		int[] check = {10, 15,42};
		int i = 0;
		for(node_data n: l1) {
			//assertEquals(n.getTag(), check[i]);
			assertEquals(n.getKey(), check[i]);
			i++;
		}
		List<node_data> l1swap = ag1.shortestPath(42,10);
		int[] checkswap = {42, 20,10};
		i = 0;
		for(node_data n: l1swap) {
			//assertEquals(n.getTag(), checkswap[i]);
			assertEquals(n.getKey(), checkswap[i]);
			i++;
		}
		//change weight from 10->42 to weight=1
		g.connect(10, 42, 1);
		//so shortest path should be [10,42]
		l1 = ag1.shortestPath(10,42);
		int[] check2 = {10,42};
		i = 0;
		for(node_data n: l1) {
			//assertEquals(n.getTag(), check[i]);
			assertEquals(n.getKey(), check2[i]);
			i++;
		}
	}
	
	
	@Test
	void save_load() {
		boolean check;
		//init a graph
		directed_weighted_graph g = new DWGraph_DS();
		directed_weighted_graph g2 = new DWGraph_DS();
		//add some nodes
		NodeData n1 = new NodeData(10);
		NodeData n2 = new NodeData(15);
		NodeData n3 = new NodeData(20);
		NodeData n4 = new NodeData(42);
		//add some node with similar key
		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		g.addNode(n4);
		//add connection
		g.connect(10,15,3);
		g.connect(15,42,10);
		g.connect(42,15,20);
		g.connect(42,20,15);
		g.connect(10, 42, 15);
		g.connect(20, 10, 12);
		
		//add some nodes
		NodeData b1 = new NodeData(1);
		NodeData b2 = new NodeData(2);
		NodeData b3 = new NodeData(3);
		NodeData b4 = new NodeData(4);
		//add some node with similar key
		g2.addNode(b1);
		g2.addNode(b2);
		g2.addNode(b3);
		g2.addNode(b4);
		//add connection
		g2.connect(1,3,12);
		g2.connect(4,1,1);
		g2.connect(2,1,3);
		g2.connect(4,2,10);
		g2.connect(2, 4, 20);
		g2.connect(3, 4, 15);

		dw_graph_algorithms ag1 = new DWGraph_Algo();
		
		//save both Graphs
        ag1.init(g);
        ag1.save("g.json");
        ag1.init(g2);
        ag1.save("g2.json");
        
        //load graph "g" and check if it will return the same graph
        ag1.load("g.json");
        check = ((DWGraph_Algo) ag1).equalsGraph(g);
        assertEquals(check,true);
        
      //load graph "g2" and check if it will return the same graph
        ag1.load("g2.json");
        check = ((DWGraph_Algo) ag1).equalsGraph(g2);
        assertEquals(check,true);
    }
	
	
	
	
	
}
