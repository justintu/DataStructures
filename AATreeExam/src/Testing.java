import java.util.ArrayList;
import java.util.Iterator;
import junit.framework.TestCase;


public class Testing extends TestCase {
	
	private static int points = 0;

	public void testReverseIterator(){
		AATree<Integer> t = new AATree<Integer>();
		Iterator<Integer> i = t.reverseIterator();
		assertFalse(i.hasNext());
		
		AATree<Integer>.BinaryNode n0 = t. new BinaryNode(5);
		t.setRoot(n0);
		AATree<Integer>.BinaryNode n1 = t.new BinaryNode(3);
		AATree<Integer>.BinaryNode n2 = t.new BinaryNode(7);
		n0.setLeftChild(n1);
		n0.setLevel(2);
		n0.setRightChild(n2);
		n2.setLevel(2);
		AATree<Integer>.BinaryNode n3 = t.new BinaryNode(9);		
		n2.setRightChild(n3);
		AATree<Integer>.BinaryNode n4 = t.new BinaryNode(6);		
		n2.setLeftChild(n4);
		AATree<Integer>.BinaryNode n5 = t.new BinaryNode(1);		
		n5.setRightChild(n1);
		n0.setLeftChild(n5);
		AATree<Integer>.BinaryNode n8 = t.new BinaryNode(10);		
		n3.setRightChild(n8);
		n3.setLeftChild(null);

		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(10);
		l.add(9);
		l.add(7);
		l.add(6);
		l.add(5);
		l.add(3);
		l.add(1);
		i = t.reverseIterator();
		for (int k = 0; k < l.size(); k++){
			assertEquals(l.get(k), i.next());
		}
		points += 15;
	}
	
	
	public void testIsAATree(){
		AATree<Integer> t = new AATree<Integer>();
		AATree<Integer>.BinaryNode n0 = t. new BinaryNode(5);
		t.setRoot(n0);
		assertTrue(t.isAATree());

		AATree<Integer>.BinaryNode n1 = t.new BinaryNode(3);
		AATree<Integer>.BinaryNode n2 = t.new BinaryNode(7);
		n0.setLeftChild(n1);
		assertFalse(t.isAATree());
		n0.setLevel(2);
		assertFalse(t.isAATree());
		
		n0.setRightChild(n2);
		assertTrue(t.isAATree());
		n2.setLevel(2);
		assertFalse(t.isAATree());

		AATree<Integer>.BinaryNode n3 = t.new BinaryNode(9);		
		n2.setRightChild(n3);
		assertFalse(t.isAATree());
		
		AATree<Integer>.BinaryNode n4 = t.new BinaryNode(6);		
		n2.setLeftChild(n4);
		assertTrue(t.isAATree());
	
		AATree<Integer>.BinaryNode n5 = t.new BinaryNode(1);		
		n1.setLeftChild(n5);
		assertFalse(t.isAATree());
		n1.setLeftChild(null);
		n5.setRightChild(n1);
		n0.setLeftChild(n5);
		assertTrue(t.isAATree());	
		
		AATree<Integer>.BinaryNode n6 = t.new BinaryNode(4);		
		n1.setRightChild(n6);
		assertFalse(t.isAATree());
		n1.setRightChild(null);
		
		AATree<Integer>.BinaryNode n7 = t.new BinaryNode(8);		
		n3.setLeftChild(n7);
		assertFalse(t.isAATree());	
		
		AATree<Integer>.BinaryNode n8 = t.new BinaryNode(10);		
		n3.setRightChild(n8);
		assertFalse(t.isAATree());	
		
		n3.setLeftChild(null);
		assertTrue(t.isAATree());	
		points += 25;
	}
		
	
	public void testDataStructure(){
	/* Create a class called "DataStructure". In it you need to
	 * store Student objects so that you can perform the following methods
	 * in the time specified.
	 * 
	 * 1) A constructor that creates an empty DataStructure object 
	 *    in constant time.
	 * 2) A method "public void add(Student s)" which adds a student
	 *    to the data structure object in at most logarithmic time.
	 * 3) A method "public Student retrieve(String key)" which returns
	 *    the student object that has "key" as the key. This method runs
	 *    in constant time.
	 * 4) A method "public Student findKthSmallest(int k)" which returns
	 *    the kth smallest student object. This method runs in k time.
	 * 	
	 * 	When you have created this code, uncomment the test code below.
	 *  Notice that I will grade this code by hand to verify that the
	 *  complexity requirements are satisfied.
	 */
		
		DataStructure s = new DataStructure();
		Student s1 = new Student("2","Moe", "Stooge");
		Student s2 = new Student("1","Larry", "Stooge");
		Student s3 = new Student("4","Shemp", "Stooge");
		Student s4 = new Student("3","Curly", "Stooge");
		
		s.add(s1);
		s.add(s2);
		s.add(s3);
		s.add(s4);
		
		assertEquals("2", s.findKthSmallest(2).getID());
		assertEquals("3", s.retrieve("3").getID());

		points += 20;
	}
	
	public void testNothing(){
		System.out.println(points);
	}

	public static void main(String args[]) {
		junit.swingui.TestRunner.run(Testing.class);
	}	
	
}
