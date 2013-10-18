import java.util.ArrayList;

public class SkipList<T extends Comparable<? super T>>{
	
	private Node head;
	private int[] nums;
	private final int maxLevel = 10;
	private int level;
	private int lastRand = 0;
	private int visited;
	
	public SkipList(int[] nums){
		this.nums = nums;
		this.head = new Node(10, null);
		this.level = 0;
		this.visited = 0;
	}
	
	public boolean insert(T value)
    {
        Node x = head;	
        Node[] update = new SkipList.Node[maxLevel + 1];

        for (int i = level; i >= 0; i--) {
	    while (x.forward[i] != null && x.forward[i].value.compareTo(value) < 0) {
	        x = x.forward[i];
	    }
	    update[i] = x; 
	}
	x = x.forward[0];


        if (x == null || !x.value.equals(value)) {        
            int lvl = 0;
            while(this.shouldAddLevel())
            	lvl++;
      
            if (lvl > level) {
	        for (int i = level + 1; i <= lvl; i++) {
	            update[i] = head;
	        }
	        level = lvl;
	    }

            x = new Node(lvl, value);
	    for (int i = 0; i <= lvl; i++) {
	    	this.visited++;
	        x.forward[i] = update[i].forward[i];
	        update[i].forward[i] = x;
	    }

        }
        this.visited++;
        return x != null;
    }
	
	private boolean shouldAddLevel(){
		if(this.lastRand == this.maxLevel - 1)
			return false;
		this.lastRand++;
		return this.nums[this.lastRand + 1] == 1;
	}
	
	public int getNodesVisited(){
		return this.visited;
	}
	
	public Node getRoot(){
		return this.head;
	}
	
	public class Node{
		public final T value;
	    public final Node[] forward; // array of pointers

	    public Node(int level, T value) 
	    {
	        this.forward = new SkipList.Node[level + 1];
	        this.value = value;
	    }
	    
	    public T getElement(){
	    	return this.value;
	    }
	    
	    public ArrayList<Node> getLinks(){
	    	ArrayList<Node> temp = new ArrayList<Node>();
	    	for(int i=1;i<this.forward.length;i++)
	    		temp.add(this.forward[i]);
			return temp;
		}
	}
}