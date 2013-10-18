import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.*;

public class Testing2 extends TestCase{

	public void testHeight(){
		BinarySearchTree b = new BinarySearchTree();
		assertEquals(-1, b.height());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		assertEquals(0, b.height());
		
		BinaryNode n2 = new BinaryNode(4);
		BinaryNode n3 = new BinaryNode(5);
		n1.setRightChild(n2);
		n2.setRightChild(n3);
		assertEquals(2, b.height());
	}
	
	public void testSize(){
		BinarySearchTree b = new BinarySearchTree();
		assertEquals(0, b.size());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		assertEquals(1, b.size());
		
		BinaryNode n2 = new BinaryNode(4);
		BinaryNode n3 = new BinaryNode(5);
		n1.setRightChild(n2);
		n2.setRightChild(n3);
		assertEquals(3, b.size());
	}
	
	public void testIsEmpty(){
		BinarySearchTree b = new BinarySearchTree();
		assertTrue(b.isEmpty());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		assertFalse(b.isEmpty());
	}
	
	public void testToArrayList(){
		BinarySearchTree b = new BinarySearchTree();
		assertEquals(new ArrayList(), b.toArrayList());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		BinaryNode n2 = new BinaryNode(4);
		BinaryNode n3 = new BinaryNode(5);
		n1.setRightChild(n2);
		n2.setRightChild(n3);

		ArrayList temp = new ArrayList();
		temp.add(3);temp.add(4);temp.add(5);
		assertEquals(temp, b.toArrayList());
	}
	
	public void testToArray(){
		BinarySearchTree b = new BinarySearchTree();
		assertEquals(0, b.toArray().length);
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		BinaryNode n2 = new BinaryNode(4);
		BinaryNode n3 = new BinaryNode(5);
		n1.setRightChild(n2);
		n2.setRightChild(n3);
		
		Object[] temp = {3,4,5};
		Object[] foo = b.toArray();
		for (int j = 0; j < temp.length; j++){
			assertEquals(temp[j], foo[j]);			
		}
	}
	
	public void testToString(){
		BinarySearchTree b = new BinarySearchTree();
		assertEquals("", b.toString());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		BinaryNode n2 = new BinaryNode(4);
		BinaryNode n3 = new BinaryNode(5);
		n1.setRightChild(n2);
		n2.setRightChild(n3);

		assertEquals( "[3], [4], [5]", b.toString());			
	}
	
	public void testPreOrderIterator(){
		BinarySearchTree b = new BinarySearchTree();
		Iterator i = b.preOrderIterator();
		assertFalse(i.hasNext());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		BinaryNode n2 = new BinaryNode(4);
		BinaryNode n3 = new BinaryNode(5);
		n1.setRightChild(n2);
		n2.setRightChild(n3);
		BinaryNode n4 = new BinaryNode(1);
		BinaryNode n5 = new BinaryNode(0);
		BinaryNode n6 = new BinaryNode(2);
		n1.setLeftChild(n4);
		n4.setLeftChild(n5);
		n4.setRightChild(n6);

		i = b.preOrderIterator();
		int k = 0;
		Object[] temp = {3, 1, 0, 2, 4, 5};
		boolean[] tempValues = {true, true, true, true, true, false};
		while (i.hasNext()){
			assertEquals(temp[k], i.next());
			assertEquals(tempValues[k++], i.hasNext());
		}
		try {
			i.next();
			fail("Did not throw NoSuchElementException");
		} catch (Exception e){
			if (!(e instanceof NoSuchElementException)) {
				fail("Did not throw NoSuchElementException");				
			}
		};
	}
	
	public void testIterator(){
		BinarySearchTree b = new BinarySearchTree();
		Iterator i = b.iterator();
		assertFalse(i.hasNext());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		BinaryNode n2 = new BinaryNode(4);
		BinaryNode n3 = new BinaryNode(5);
		n1.setRightChild(n2);
		n2.setRightChild(n3);
		BinaryNode n4 = new BinaryNode(1);
		BinaryNode n5 = new BinaryNode(0);
		BinaryNode n6 = new BinaryNode(2);
		n1.setLeftChild(n4);
		n4.setLeftChild(n5);
		n4.setRightChild(n6);

		i = b.iterator();
		int k = 0;
		Object[] temp = {0, 1, 2, 3, 4, 5};
		boolean[] tempValues = {true, true, true, true, true, false};
		while (i.hasNext()){
			assertEquals(temp[k], i.next());	
			assertEquals(tempValues[k++], i.hasNext());
		}
		try {
			i.next();
			fail("Did not throw NoSuchElementException");
		} catch (Exception e){
			if (!(e instanceof NoSuchElementException)) {
				fail("Did not throw NoSuchElementException");				
			}
		};
	}
	
	public static void main(String args[]) {
		junit.swingui.TestRunner.run(Testing.class);
	}	
	
}
