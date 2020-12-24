package api;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class graphJsonDeserializerData implements JsonDeserializer<DWGraph_DS>{
	
	@Override
	public DWGraph_DS deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException 
	{
		JsonObject jsonObject = json.getAsJsonObject();
        DWGraph_DS newGraph = new DWGraph_DS();
        
        JsonArray nodes = jsonObject.get("Nodes").getAsJsonArray();
        JsonArray edges = jsonObject.get("Edges").getAsJsonArray();
        
        for (JsonElement set : nodes)
        {
        	JsonObject val = set.getAsJsonObject();
        	int id = val.get("id").getAsInt();
			String pos = val.get("pos").getAsString();
			node_data node = new NodeData(id);
			node.setLocation(new GeoLocation(pos));
			newGraph.addNode(node);
        }
        
        for (JsonElement set : edges)
        {
        	JsonObject val = set.getAsJsonObject();
    		int src =  val.get("src").getAsInt();
    		int dest = val.get("dest").getAsInt();
    		double w = val.get("w").getAsDouble();
    		 newGraph.connect(src, dest, w);
        }
        
        return newGraph;
	}

}

