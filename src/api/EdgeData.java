package api;

public class EdgeData implements edge_data{
	
	private int src;
    private int dest;
    private double w;
    private String info;
    private int tag;
    
    /**
     * Constructor
     */
    public EdgeData(){
        this.src=0;
        this.dest=0;
        this.w=0.0;
        this.info="";
        this.tag=0;
    }

    /**
     * Constructor With parameters
     */
    public EdgeData(int src, int dest, double w){
        if(w>=0) {
        	this.src=src;
            this.dest=dest;
            this.w=w;
            this.info="";
            this.tag=0;
        }
    }

    /**
     *Copy constructor
     */
    public EdgeData(EdgeData edge){
        this.src=edge.src;
        this.dest=edge.dest;
        this.w=edge.w;
        this.info=edge.info;
        this.tag=edge.tag;
    }
	/**
	 * The id of the source node of this edge.
	 */
	@Override
	public int getSrc() {
		return this.src;
	}
	/**
	 * The id of the destination node of this edge
	 */
	@Override
	public int getDest() {
		return this.dest;
	}
	/**
	 * @return the weight of this edge (positive value).
	 */
	@Override
	public double getWeight() {
		return this.w;
	}
	/**
	 * Returns the remark (meta data) associated with this edge.
	 */
	@Override
	public String getInfo() {
		return this.info;
	}
	/**
	 * Allows changing the remark (meta data) associated with this edge.
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
	 * This method allows setting the "tag" value for temporal marking an edge - common
	 * practice for marking by algorithms.
	 */
	@Override
	public void setTag(int t) {
		this.tag=t;
	}

}
