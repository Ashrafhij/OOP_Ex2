package api;

public class NodeData implements node_data{
	
	private int key;
	geo_location loc;
    private double weight;
    private int tag;
    private String info;
    
    /**
     * constructor With parameters
     */
    public NodeData(int key){
        this.key=key;
        this.weight=0;
        this.tag=0;
        this.info="";
    }
    
    /**
     * constructor With parameters
     */
    public NodeData(int key,double weight,int tag, String info){
        this.key=key;
        this.weight=weight;
        this.tag=tag;
        this.info=info;
    }

    /**
     *Copy constructor
     */
    public NodeData(NodeData n){
        this.key=n.key;
        this.weight=n.weight;
        this.tag=n.tag;
        this.info=n.info;
    }
	/**
	 * Returns the key (id) associated with this node.
	 */
	@Override
	public int getKey() {
		return this.key;
	}
	/** Returns the location of this node, if
	 * none return null.
	 */
	@Override
	public geo_location getLocation() {
		
		return this.loc;
	}
	/** Allows changing this node's location.
	 */
	@Override
	public void setLocation(geo_location p) {
		this.loc = p;
	}
	/**
	 * Returns the weight associated with this node.
	 * @return
	 */
	@Override
	public double getWeight() {
		return this.weight;
	}
	/**
	 * Allows changing this node's weight.
	 * @param w - the new weight
	 */
	@Override
	public void setWeight(double w) {
		this.weight=w;
	}
	/**
	 * Returns the remark (meta data) associated with this node.
	 */
	@Override
	public String getInfo() {
		return this.info;
	}
	/**
	 * Allows changing the remark (meta data) associated with this node.
	 */
	@Override
	public void setInfo(String s) {
		this.info=s;
	}
	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 */
	@Override
	public int getTag() {
		return this.tag;
	}
	/** 
	 * Allows setting the "tag" value for temporal marking an node - common
	 * practice for marking by algorithms.
	 */
	@Override
	public void setTag(int t) {
		this.tag=t;
	}

}
