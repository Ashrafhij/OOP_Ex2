package gameClient;

import api.*;
import Server.Game_Server_Ex2;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Ex2_Client implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	public static game_service game;
	static int scenario_num=0;
	static int username=314988189;
	public static void main(String[] a) {
		if(a.length != 0) {
			Ex2_Client.username=Integer.parseInt(a[0]);
			Ex2_Client.scenario_num=Integer.parseInt(a[1]);
		}
		Thread client = new Thread(new Ex2_Client());
		client.start();
	}

	public void myLevel(int x){
		this.scenario_num=x;
	   } 
	public void myID(int x){  
		this.username=x;
	   } 
	/***************************/
	@Override
	public void run() {
		game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
		int id = username;
		game.login(id);
		String g = game.getGraph();
		String pks = game.getPokemons();
		
		directed_weighted_graph gg = getGra(g); // function return graph
	
		init(game);

		game.startGame();
		_win.setTitle("Ex2 - OOP: (trivial Solution) ");
		int ind=0;
		long dt=100;
		
		while(game.isRunning()) {
			moveAgants(game, gg);
			try {
				if(ind%1==0) {_win.repaint();}
				Thread.sleep(dt);
				ind++;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();

		System.out.println(res);
		_win.dispose();
		Ex2.gameStart("Enter ID + Level : e.g. ' 123456789 11 '");
//		System.exit(0);
	}
	/** 
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param
	 */
	private void moveAgants(game_service game, directed_weighted_graph gg) {
		String lg = game.move();
		List<CL_Agent> log = Arena.getAgents(lg, gg);
		_ar.setAgents(log);
		//ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
		String fs =  game.getPokemons();
		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
		_ar.setPokemons(ffs);
		
		for (int i = 0; i < ffs.size(); i++) {
            Arena.updateEdge(ffs.get(i), gg);
        }
		
		for(int i=0;i<log.size();i++) {
			CL_Agent agent=log.get(i);
			int id = agent.getID();
			int dest = agent.getNextNode();
			int src = agent.getSrcNode();
			double v = agent.getValue();
			if(dest==-1) {
				dest = nextNode(gg, src ,ffs);
				game.chooseNextEdge(agent.getID(), dest);
				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
			}
		}
	}
	
	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */
	private int nextNode(directed_weighted_graph g, int src , List<CL_Pokemon>  ar) {
		DWGraph_Algo ag = new DWGraph_Algo();
		ag.init(g);
		
		//to get the nearest existed Pokemon to the agent
		CL_Pokemon dest = nearestPokemon(ag , src , ar);
		List<node_data> srcPokemon = ag.shortestPath(src, dest.get_edge().getSrc());
		if(srcPokemon.size() == 1)
			return dest.get_edge().getDest();
		else
			return srcPokemon.get(1).getKey();
	}
	/**
	 * return the pokemon that the agent should go forward
	 */
	public CL_Pokemon nearestPokemon(DWGraph_Algo ag , int src ,Collection<CL_Pokemon> pCollection) {
		CL_Pokemon nearPokemon = null;
		double m = Integer.MAX_VALUE;
		Iterator<CL_Pokemon> itr = pCollection.iterator();
		while(itr.hasNext()) {
			CL_Pokemon pok=itr.next();
			if(pok.get_edge() != null) {
				//in this case Pokemon exist in the same edge as Agent
				if(pok.get_edge().getSrc() == src) {
					int dest = pok.get_edge().getDest();
					double s = ag.shortestPathDist(src, dest);
					nearPokemon=pok;
					edge_data e = new EdgeData(dest,src,nearPokemon.get_edge().getWeight());
					nearPokemon.set_edge(e);
					return nearPokemon; // i can return the pokemon cuz certainly there is no pokemon closer than him
				}
				int dest = pok.get_edge().getSrc();
				double s = ag.shortestPathDist(src, dest);
				if(s < m && s !=0) {
					m=s;
					nearPokemon=pok;
				}
			}
		}
		return nearPokemon;
	}
	/**
	 * return the graph after use the graphJsonDeserializerData Class on the Json String
	 */
	public static directed_weighted_graph getGra(String jsonString){
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(directed_weighted_graph.class, new graphJsonDeserializerData());
		Gson gson =  builder.setPrettyPrinting().create();
		directed_weighted_graph helper = gson.fromJson(jsonString, directed_weighted_graph.class);
		return helper;
	}
	
	/**
	 * return the Game So I Can Stop it or get all the info about the game 
	 */
	public static game_service getGame(){
		return game;
	}

	
	
	private void init(game_service game) {
		String g = game.getGraph();
		String fs = game.getPokemons();
//		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		directed_weighted_graph gg = getGra(g); // function return graph
		//gg.init(g);
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_win = new MyFrame("test Ex2");
		_win.setSize(1000, 700);
		_win.update(_ar);

	
		_win.show();
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
			for(int a = 0;a<rs;a++) {
				int ind = a%cl_fs.size();
				CL_Pokemon c = cl_fs.get(ind);
				int nn = c.get_edge().getDest();
				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}
				
				game.addAgent(nn);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
}