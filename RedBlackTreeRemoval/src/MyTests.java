
public class MyTests {
	public static void main(String[] args){
		test1();
	//	test2();
	//	test3();
	}
	
	public static void test1(){
		RedBlackTree<Integer> b = new RedBlackTree<Integer>();
		
		b.insert(9);
		b.insert(7);
		b.insert(11);
		b.insert(5);
		b.insert(8);
		b.insert(10);
		b.insert(12);
		b.insert(7);
		b.insert(11);
		b.insert(9);
		
		b.remove(12);
		b.remove(11);
		b.remove(8);
		System.out.println(b);
	}
	
	public static void test2(){
		RedBlackTree<Integer> b = new RedBlackTree<Integer>();
		b.insert(20);
		b.insert(10);
		b.insert(30);
		b.insert(5);
		b.insert(15);
		b.insert(40);
		b.insert(25);
		b.insert(25);
		b.insert(5);
		b.insert(5);
		System.out.println(b);
		
		b.remove(15);
		
		System.out.println(b);
	}
	
	public static void test3(){
		RedBlackTree<Integer> b = new RedBlackTree<Integer>();
		int size = 128;
		int v = size / 2;
		int temp;
		while (v > 0) {
			temp = v;
			while (temp < size){
				b.insert(temp);
				temp += v;
				}
			v = v / 2;
		}
		
		System.out.println(b);
		
		for (int i = 127; i >= 1; i--){
			System.out.println(i);
			b.remove(i);
			
		}
		System.out.println("Rotations: " + b.getRotationCount());
	}
}
