import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import junit.framework.TestCase;

public class Testing extends TestCase{
		
	private static int points = 0;
	
	public void testNthLargest(){
		BinarySearchTree<Integer> b = new BinarySearchTree<Integer>();
		try {
			b.nthLargest(1);
			fail("Did not throw IllegalArgumentException");
		} catch (Exception e){
			if (!(e instanceof IllegalArgumentException)) {
				fail("Did not throw IllegalArgumentException");				
			}
		}
		points += 2;
		
		b.insert(5);
		b.insert(3);
		b.insert(1);
		b.insert(6);
		b.insert(7);
		b.insert(8);
		try {
			b.nthLargest(0);
			fail("Did not throw IllegalArgumentException");
		} catch (Exception e){
			if (!(e instanceof IllegalArgumentException)) {
				fail("Did not throw IllegalArgumentException");				
			}
		}
		points += 1;
		
		assertEquals(new Integer(8), b.nthLargest(1));
		points += 5;
		
		assertEquals(new Integer(1), b.nthLargest(6));
		points += 2;
	}
	

	
	public void testLevelOrderTraversal(){
		BinarySearchTree<Integer> b = new BinarySearchTree<Integer>();
		b.insert(5);
		b.insert(3);
		b.insert(1);
		b.insert(4);	
		b.insert(6);
		b.insert(8);
	
		ArrayList<Integer> v = new ArrayList<Integer>();
		v.add(5);
		v.add(3);
		v.add(6);
		v.add(1);
		v.add(4);
		v.add(8);
		
		Iterator<Integer> i = b.iterator();
		for (int j = 0; j < v.size(); j++){
			assertTrue(i.hasNext());
			assertEquals(v.get(j), i.next());
		}
		points += 20;
	}
	
	public void testHashTable(){
		BinarySearchTree<String> b = new BinarySearchTree<String>();
		b.insert("one");
		b.insert("two");
		b.insert("three");
		b.insert("four");
		b.insert("five");
		b.insert("six");
		HashTable t = b.toHashTable();
		assertEquals("four", t.retrieve("four"));
		ArrayList buckets = t.getTable();
		assertEquals(9, buckets.size());
		assertEquals(null, buckets.get(0));
		assertEquals(null, buckets.get(1));
		assertEquals(null, buckets.get(2));
		assertEquals(null, buckets.get(6));
		assertEquals(null, buckets.get(8));

		Collection temp = (Collection) buckets.get(4);
		Iterator i = temp.iterator();
		assertTrue(i.hasNext());
		assertEquals("two", i.next());
		
		temp = (Collection) buckets.get(5);
		i = temp.iterator();
		assertTrue(i.hasNext());
		assertEquals("three", i.next());
		
		temp = (Collection) buckets.get(3);
		i = temp.iterator();
		assertTrue(i.hasNext());
		String s1 = (String) i.next();
		assertTrue(s1.equals("four") || s1.equals("five"));
		assertTrue(i.hasNext());
		String s2 = (String) i.next();
		assertTrue(s2.equals("four") || s2.equals("five"));
		assertFalse(s1.equals(s2));
		
		temp = (Collection) buckets.get(7);
		i = temp.iterator();
		assertTrue(i.hasNext());
		s1 = (String) i.next();
		assertTrue(s1.equals("one") || s1.equals("six"));
		assertTrue(i.hasNext());
		s2 = (String) i.next();
		assertTrue(s2.equals("one") || s2.equals("six"));
		assertFalse(s1.equals(s2));
	
		
		points += 30;
	}	

	public void testNothing(){
		System.out.println(points);
	}

	public static void main(String args[]) {
		junit.swingui.TestRunner.run(Testing.class);
	}	
	
}


