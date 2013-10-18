public class MyTests {
	public static void main(String[] args){
		SplayTree<Integer> t = new SplayTree<Integer>();
		
		t.insert(6);
		t.insert(4);
		t.insert(5);
		t.insert(3);
		t.insert(7);
		
		System.out.println(t);
	}
}
