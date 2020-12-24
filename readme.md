
	-Pokemon Game : represents a game which is based on the weighted graph with different methods and algorithms,
 		In this game there is a group of agents at-least one agent whose goal is to collect as many pokemons as possible before time runs out.
 		The more Pokemon he catch, the more points he earn , there is  24 different levels with different time limits , and The Pokemon have different values.
 		so that One Pokemon can be more weighted than the other, Agents can increase speed if they catch enough Pokemon.

	-inside Game : after clicking on the start of the game it shows a window that there the Client should enter his ID + level which 
			he wawnts to play , then it will start the game and time start to run out , there is two options , one to Re-Play the game ,
			so if "Re-Game" button clicked it will stop the game and back to starter window , so can choose another level with another Client
			and also there is Exit Button To Exit The Game.

	-Implementation of the Graph and The Nodes Which Represent the Agents + The Pokemons
 	directional weighted graph , Includes Set Of Nodes :

	-Node : Each Node Include Unique Key , location type of geo_location , weight type of Double and
		 there is some methods like info , Tag
	-Graph : implemented Graph By set of nodes type of Hashmap , because we have a directional weighted graph
			i used hashmap inside hashmap that implement the neighbors in a set type of edge_Data 
		   		which can be connected together , also Graph includes
					edgeSize variable that contain total edges in the graph.

	-Graph_algo : initialization of a graph with methods which check if
			the Graph is Connected or to get the Shortest Path
				between two nodes , Save / Load - graph 

	i Used Data Structure of Hashmap so i implemented graph by using hashmap inside another hashmap

	* Hashmap 1 : HashMap stores the node's number type of Ineteger as key ,
			the value is a Node Type of node_data ,used node_data to save the Weight and other methods 
				of the node.
	* Hashmap 2 : in this hashmap i used hashmap inside other hashmap so its like inner hashmap inside outer ,
			the outer hashmap includes <Integer,HashMap<Integer,edge_data>> which is the key value is the Source
				and the value is another hashmap that contains key as destination and values as EdgeData

	Methods , Functions and complexity : 
		is used these structures of hashmap so i can get
		complexity O(1)for the majority of methods in Both Graph's methods
		and also Node's methods so that a graph of million vertices can
		be constructed.

	*isConnected , ShortestPath Methods :
		in these methods i used a BFS which it explores all of the
 		neighbor nodes at the present depth prior to moving on to 
		the nodes at the next depth level,which explores the node 
		branch as far as possible before being forced to backtrack
		and expand other nodes , The time complexity can be expressed 
		as O(|V|+|E|), since every vertex and every edge will be
		explored in the worst case , for ShortestPath i used DIJKSTRA 
		Algorithm.
			
		
