public class BooleanWrapper {
	
	private boolean mod;
	
	public BooleanWrapper(){
		mod = false;
	}

	public void setTrue() {
		mod = true;
	}
	
	public void setFalse() {
		mod = false;
	}
	
	public boolean getValue() {
		return mod;
	}
}
