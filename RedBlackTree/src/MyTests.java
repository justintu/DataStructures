
public class MyTests {
	
	public static void main(String[] args){
		
		testInsert2();
		
	}
	
	public static void testInsert1(){
		RedBlackTree<Integer> t = new RedBlackTree<Integer>();
		
		t.insert(1);
		t.insert(2);
		t.insert(3);
		t.insert(4);
		t.insert(5);
		t.insert(6);
		t.insert(7);
		
		System.out.println(t);
		
	}
	
	public static void testInsert2(){
		RedBlackTree<Integer> t = new RedBlackTree<Integer>();
		
		t.insert(2);
		t.insert(1);
		t.insert(5);
		t.insert(3);
		t.insert(4);
		
		System.out.println(t);
//		System.out.println(t.height());
		
	}
	
	public static void testInsert3(){
		RedBlackTree<Integer> t = new RedBlackTree<Integer>();
		
		t.insert(7);
		t.insert(6);
		t.insert(5);
		t.insert(4);
		t.insert(3);
		t.insert(2);
		t.insert(1);
//		t.insert(1);
		
		System.out.println(t);
//		System.out.println(t.height());
		
	}
	
}
