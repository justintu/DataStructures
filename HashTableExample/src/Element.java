
/**
 * TODO Put here a description of what this class does.
 *
 * @author wollowsk.
 *         Created Jan 14, 2008.
 */
public class Element {
	private String key;
	private Object o;
	
	public Element(String key, Object o){
		this.key = key;
		this.o = o;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public Object getObject() {
		return this.o;
	}
	
	public String toString() {
		return key + ":" + o;
	}

}
