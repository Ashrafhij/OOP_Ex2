package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;
import api.edge_data;
import api.*
;class DWGraph_DSTest {

    @Test
    void nodeSize() {
    	//init graph
        directed_weighted_graph g = new DWGraph_DS();
        NodeData n1 = new NodeData(10);
    	NodeData n2 = new NodeData(15);
    	NodeData n3 = new NodeData(20);
    	NodeData n4 = new NodeData(42);
        //add some node with similar key
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        //remove some nodes that doesn't exist
        g.removeNode(n1.getKey());
        g.removeNode(n3.getKey());
        g.removeNode(n3.getKey());
        int size = g.nodeSize();
        assertEquals(2,size);
        g.addNode(n3);
        g.addNode(n3);
        size = g.nodeSize();
        assertEquals(3,size);
    }

    @Test
    void edgeSize() {
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
        g.connect(1,2,10);
        g.connect(10,15,3);
        g.connect(15,42,10);
        g.connect(42,15,20);
        g.connect(42,20,15);
        edge_data edge = g.getEdge(1,3);
        //edge not exist check
        assertEquals(null, edge);
        int edgeSize =  g.edgeSize();
        assertEquals(4, edgeSize);
        edge = g.getEdge(10,15);
        assertEquals(3, edge.getWeight());
        edge = g.getEdge(15,20);
        assertEquals(null, edge);
        //Update Weight on edge
        g.connect(10,15,22);
        //check after update edge
        edge = g.getEdge(10,15);
        assertEquals(22, edge.getWeight());
        //one direction check
        edge = g.getEdge(15,42);
        assertEquals(10, edge.getWeight());
        //check other direction weight
        edge = g.getEdge(42,15);
        assertEquals(20, edge.getWeight());
    }

    @Test
    void getV() {
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
        g.connect(1,2,10);
        g.connect(10,15,3);
        g.connect(15,42,10);
        g.connect(42,15,20);
        g.connect(42,20,15);
    	Collection<node_data> k=  g.getV();
    	for (node_data key : k) {
    		assertNotNull(key);
    	}
    }

    @Test
    void getE() {
    	
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
        g.connect(1,2,10);
        g.connect(10,15,3);
        g.connect(15,42,10);
        g.connect(42,15,20);
        g.connect(42,20,15);

        Collection<edge_data> k=  g.getE(15);
    	for (edge_data key : k) {
    		assertNotNull(key);
    	}
    }

    @Test
    void connect() {
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
        g.connect(1,2,10);
        g.connect(10,15,3);
        g.connect(15,42,10);
        g.connect(42,15,20);
        g.connect(42,20,15);
    	//should return false vertices not even exists
        edge_data edge = g.getEdge(1,2);
        assertNull(edge);
        //should return the edgeData != null
        edge = g.getEdge(10,15);
        assertNotNull(edge);
        g.removeEdge(10,15);
      //should return the edgeData == null
        edge = g.getEdge(10,15);
        assertNull(edge);
    }


    @Test
    void removeNode() {
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
    	
        g.removeNode(15);
        
        //after remove the node (15) there is no connection between 10->15 || 15 <-> 42
        edge_data edge = g.getEdge(10,15);
        assertNull(edge);
        edge = g.getEdge(42,15);
        assertNull(edge);
        edge = g.getEdge(15,42);
        assertNull(edge);
        int edgesize = g.edgeSize();
        //still 3 nodes with 1 edge
        assertEquals(1,edgesize);
    }

    @Test
    void removeEdge() {
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
        edge_data edge = g.getEdge(10,15);
        //there is an edge weight is 3
    	assertEquals(edge.getWeight(),3);
    	assertNotNull(edge);
    	//should return null , there is no edge between these two nodes
        edge = g.removeEdge(1,3);
        assertNull(edge);
        //there is connection between these two nodes so it should not return Null
        edge = g.removeEdge(10,15);
        assertNotNull(edge);
      //After Remove this Connection it should return null
        edge = g.getEdge(10,15);
        assertNull(edge);
    }
	

}
