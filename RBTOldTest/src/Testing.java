import java.util.ArrayList;

import junit.framework.*;

public class Testing extends TestCase{
	
	
	public void testHeight(){
		RedBlackTree<Integer> b = new RedBlackTree<Integer>();
		assertEquals(-1, b.height());
		
		RedBlackTree.BinaryNode n0 = b.new BinaryNode(5);
		b.setRoot(n0);
		n0.setBlack();
		assertEquals(0, b.height());

		RedBlackTree.BinaryNode n1 = b.new BinaryNode(3);
		RedBlackTree.BinaryNode n2 = b.new BinaryNode(7);
		n0.setLeftChild(n1);
		assertEquals(1, b.height());
		
		n0.setRightChild(n2);
		assertEquals(1, b.height());
		
		RedBlackTree.BinaryNode n3 = b.new BinaryNode(9);		
		n2.setRightChild(n3);
		n3.setBlack();
		assertEquals(2, b.height());
		
		RedBlackTree.BinaryNode n4 = b.new BinaryNode(1);		
		n1.setLeftChild(n4);
		n4.setBlack();
		assertEquals(2, b.height());
	}
	
	public void testReverse(){
		RedBlackTree<Integer> b = new RedBlackTree<Integer>();
		b.reverse();
		
		RedBlackTree.BinaryNode n0 = b. new BinaryNode(5);
		b.setRoot(n0);
		n0.setBlack();
		b.reverse();
		ArrayList<Object> a = b.toArrayList();
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(5);
		ArrayList<Object> colors = new ArrayList<Object>();
		colors.add(RedBlackTree.Color.BLACK);
		for (int i = 0; i < values.size(); i++){
			assertEquals(values.get(i), ((RedBlackTree.BinaryNode) a.get(i)).getElement());
			assertEquals(colors.get(i), ((RedBlackTree.BinaryNode) a.get(i)).getColor());
		}

		b = new RedBlackTree<Integer>();
		n0 = b. new BinaryNode(5);
		b.setRoot(n0);
		n0.setBlack();
		
		RedBlackTree.BinaryNode n1 = b.new BinaryNode(3);
		RedBlackTree.BinaryNode n2 = b.new BinaryNode(7);
		n0.setLeftChild(n1);
		n0.setRightChild(n2);
		b.reverse();
		a = b.toArrayList();
		values = new ArrayList<Object>();
		values.add(5);
		values.add(7);
		values.add(3);
		colors = new ArrayList<Object>();
		colors.add(RedBlackTree.Color.BLACK);
		colors.add(RedBlackTree.Color.RED);
		colors.add(RedBlackTree.Color.RED);
		for (int i = 0; i < values.size(); i++){
			assertEquals(values.get(i), ((RedBlackTree.BinaryNode) a.get(i)).getElement());
			assertEquals(colors.get(i), ((RedBlackTree.BinaryNode) a.get(i)).getColor());
		}
		
		b = new RedBlackTree<Integer>();
		n0 = b. new BinaryNode(5);
		b.setRoot(n0);
		n0.setBlack();
		
		n1 = b.new BinaryNode(3);
		n2 = b.new BinaryNode(7);
		n0.setLeftChild(n1);
		n0.setRightChild(n2);
		RedBlackTree.BinaryNode n3 = b.new BinaryNode(9);		
		n2.setRightChild(n3);
		n3.setBlack();
		RedBlackTree.BinaryNode n4 = b.new BinaryNode(1);		
		n1.setLeftChild(n4);
		n4.setBlack();
		b.reverse();
	
		a = b.toArrayList();
		values = new ArrayList<Object>();
		values.add(5);
		values.add(7);
		values.add(9);
		values.add(3);
		values.add(1);
		colors = new ArrayList<Object>();
		colors.add(RedBlackTree.Color.BLACK);
		colors.add(RedBlackTree.Color.RED);
		colors.add(RedBlackTree.Color.BLACK);
		colors.add(RedBlackTree.Color.RED);
		colors.add(RedBlackTree.Color.BLACK);
		for (int i = 0; i < values.size(); i++){
			assertEquals(values.get(i), ((RedBlackTree.BinaryNode) a.get(i)).getElement());
			assertEquals(colors.get(i), ((RedBlackTree.BinaryNode) a.get(i)).getColor());
		}		
	}
	
	public void testIsRedBlackTree(){
		RedBlackTree<Integer> b = new RedBlackTree<Integer>();
		RedBlackTree.BinaryNode n0 = b. new BinaryNode(5);
		b.setRoot(n0);
		assertFalse(b.isRedBlackTree());
	
		n0.setBlack();
		RedBlackTree.BinaryNode n1 = b.new BinaryNode(3);
		RedBlackTree.BinaryNode n2 = b.new BinaryNode(7);
		n0.setLeftChild(n1);

		n0.setRightChild(n2);
		assertTrue(b.isRedBlackTree());
		
		RedBlackTree.BinaryNode n3 = b.new BinaryNode(9);		
		n2.setRightChild(n3);
		assertFalse(b.isRedBlackTree());
		n3.setBlack();
		assertFalse(b.isRedBlackTree());
		
		RedBlackTree.BinaryNode n4 = b.new BinaryNode(1);		
		n1.setLeftChild(n4);
		assertFalse(b.isRedBlackTree());
		n4.setBlack();
		System.out.println(b);
		assertFalse(b.isRedBlackTree());	
	
		n4.setRed();
		n3.setRed();
		n1.setBlack();
		n2.setBlack();
		assertTrue(b.isRedBlackTree());
	}

public static void main(String args[]) {
	junit.swingui.TestRunner.run(Testing.class);
}
}