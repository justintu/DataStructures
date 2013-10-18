import java.util.Iterator;

public class testTree {
	public static void main(String args[]) {

		
		BinarySearchTree b = new BinarySearchTree();
		
		b.insert(12);
		b.insert(7);
		b.insert(5);
		b.insert(9);
		b.insert(99);
		b.insert(77);
		b.insert(101);
		System.out.println("BST: " + b);
		
		Iterator i = b.preOrderIterator();
		
		while(i.hasNext()){
			System.out.println(i.next());
			System.out.println(i.hasNext());
		}
		
/*		
		System.out.println("Height: " + b.height());
		System.out.println("BST: " + b);
		System.out.println("Size: " + b.size());
		System.out.println("Empty? " + b.isEmpty());
		System.out.println("ArrayList of BST: " + b.toArrayList());
		System.out.println("Array of BST: " + b.toArray());
	*/
		
		
		
		
	}


}
