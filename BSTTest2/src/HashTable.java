import java.util.ArrayList;

public class HashTable {
	
	/** 
	 * You need to modify the buckets ArrayList so that you can indeed
	 * store a hash table. For testing purposes, you need to keep the name
	 * and the top-level ArrayList.  
	 * The buckets have to be stored in a data structure from the Collections
	 * API.
	 * To simplify this assignments, you may assume that we will only 
	 * store Strings in the hash table.
	 */
	private ArrayList<ArrayList> buckets;
	private int size = 9;
	
	/** 
	 * Create and initialize the buckets ArrayList. Ensure that the 
	 * ArrayList is of length "size."  
	 */
	public HashTable(){
		this.buckets = new ArrayList<ArrayList>(this.size);
		//For some reason instantiating with an int arg did not work for me.
		for(int i=0;i<this.size;i++)
			this.buckets.add(null);
	}

	/** 
	 * Write an insert method. It will hash the argument passed to it
	 * and insert it in the hashtable. Collisions will be handled by
	 * storing colliding elements in the same bucket. 
	 */
	public void insert(String e){
		int loc = this.hash(e);
		System.out.println(e + " " + loc);
		if(this.buckets.get(loc) == null){
			this.buckets.set(loc, new ArrayList<String>());
			this.buckets.get(loc).add(e);
		}else{
			this.buckets.get(loc).add(e);
		}
	}

	/** 
	 * Write a retrieve method. It will hash the argument passed to it
	 * and retrieve it from the proper bucket. In case a bucket holds more
	 * than one element, linear search may be used to locate the 
	 * element in the bucket. 
	 */
	public String retrieve(String e){
		int loc = this.hash(e);
		ArrayList<String> temp = this.buckets.get(loc);
		if(temp == null)
			return null;
		else{
			for(String s : temp){
				if(s.equals(e))
					return s;
			}
		}
		return null;
	}
	
	/** 
	 * This method hashes elements. Do not modify it.
	 */
	private int hash(String e){
		int hashVal = 0;
		for (int i = 0; i < e.length(); i++){
			hashVal = hashVal * 37 + e.charAt(i);
		}
		hashVal %= size;
		if (hashVal < 0) hashVal += size;
		return hashVal;
	}
	
	/** 
	 * This method returns the hash table. Do not modify it.
	 */
	public ArrayList getTable(){
		return buckets;
	}
}