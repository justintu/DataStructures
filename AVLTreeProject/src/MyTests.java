public class MyTests {
	public static void main(String[] args){
		
//		singleLeftRoot1();
//		doubleLeft();
		
//		singleRight();
//		doubleRight1();
//		doubleRight2();
		testRemove();
		
		
	}
	
	public static void testRemove(){
		AVLTree<Integer> b = new AVLTree<Integer>();
		b.insert(4);
		b.insert(2);
		b.insert(6);
		b.insert(1);
		b.insert(3);
		b.insert(5);
		b.insert(7);
		
		System.out.println("Original: " + b);
		b.print();
		
		b.remove(4);
		System.out.println("Removed 4: " + b);
		b.print();
		
		b.remove(3);
		System.out.println("Removed 3: " + b);
		b.print();
		
		b.remove(2);
		System.out.println("Removed 2: " + b);
		b.print();
	}
	
	public static void singleLeftRoot1(){
		AVLTree<Integer> b = new AVLTree<Integer>();
		b.insert(1);
		b.insert(2);
		b.insert(3);
		b.insert(4);
		b.insert(5);
		b.insert(6);
		System.out.println(b);
		b.print();
		System.out.println("Rotation count: " + b.getRotationCount());
		System.out.println("\n");
	}
	
	public static void singleLeftRoot2(){
		AVLTree<Integer> b = new AVLTree<Integer>();
		b.insert(5);
		b.insert(7);
		b.insert(9);
		b.insert(11);
		b.insert(13);
		b.insert(15);
		System.out.println(b);
		b.print();
		System.out.println("Rotation count: " + b.getRotationCount());
		System.out.println("\n");
	}
}
