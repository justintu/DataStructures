public class SSI implements Comparable<SSI>{
	
	private String fname;
	private String lname;
	private int id;
	
	public SSI(int id){
		this.id = id;
		this.fname = null;
		this.lname = null;
	}
	
	public SSI(int id, String fname, String lname){
		this.id = id;
		this.fname = fname;
		this.lname = lname;
	}
	
	
	public int compareTo(SSI o) {
		if(this.id > o.id)
			return 1;
		else if(this.id < o.id)
			return -1;
		else
			return 0;
	}
	
	public String toString(){
		if(this.fname != null && this.lname != null)
			return String.valueOf(this.id) + " " + this.fname + " " + this.lname;
		else
			return String.valueOf(this.id);
	}

}
