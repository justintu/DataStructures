import java.util.ArrayList;

/**
 * TODO Put here a description of what this class does.
 *
 * @author wollowsk.
 *         Created Jan 14, 2008.
 */
public class MyHashTable {
	private int size = 11;
	private ArrayList<Element>[] table;
	
	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 */
	public MyHashTable() {
		table = new ArrayList[size];
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param key
	 * @param o
	 */
	public void insert(String key, Object o){
		int index = hash4(key);
		if (table[index] == null) {
			table[index] = new ArrayList();
			table[index].add(new Element(key, o));
		} else {
			table[index].add(new Element(key, o));
		}
	}
	
	private int hash1(String key){
		return key.charAt(0) % size;
	}
	
	private int hash2(String key){
		int hashVal = 0;
		for (int i = 0; i < key.length(); i++){
			hashVal += key.charAt(i);
		}
		return hashVal % size;
	}
	
	private int hash3(String key){
		int hashVal = 0;
		int limit = key.length();
		limit = (limit > 4)?4:limit;
		for (int i = 0; i < limit; i++){
			hashVal += key.charAt(i);
		}
		return hashVal % size;
	}
	
	private int hash4(String key){
		int hashVal = 0;
		//int limit = key.length();
		//limit = (limit > 4)?4:limit;
		for (int i = 0; i < key.length(); i++){
			hashVal = hashVal * 37 + key.charAt(i);
		}
		hashVal %= size;
		if (hashVal < 0) hashVal += size;
		return hashVal;
	}
	
	public String showAll(){
		String temp = "";
		for (int i = 0; i < size; i++) {
			temp += table[i] + "\n";
		}
		return temp;
	}
}
