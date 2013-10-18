import java.util.ArrayList;

public class Entry implements Insertable, Comparable<Entry>{
	
	private String word;
	private ArrayList<String> def;
	
	public Entry(String word, String def){
		this.word = word;
		this.def = new ArrayList<String>();
		this.def.add(def);
	}
	
	public boolean insert(Object o) {
		if(o == null)
			return false;
		else
			return this.def.add(((Entry)o).getDef());
	}
	
	public ArrayList<String> getDefinitions(){
		return this.def;
	}

	public int compareTo(Entry o){
		int cmp = o.getWord().compareTo(this.word);
		if(cmp == 0)
			return 0;
		else if(cmp > 1)
			return 1;
		else
			return -1;
	}
	
	public String getWord(){
		return this.word;
	}
	
	public String getDef(){
		return this.def.get(0);
	}
}
