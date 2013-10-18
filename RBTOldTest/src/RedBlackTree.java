import java.util.ArrayList;

/**
 * This class implements an RB Tree. 
 *
 * @author wollowsk.
 *         Created March, 2009.
 */


public class RedBlackTree<T extends Comparable<? super T>> {
	private BinaryNode root;

	public RedBlackTree(){
		root = null;
	}
	
	public void setRoot(BinaryNode n){
		root = n;
	}
	
	public String toString(){
		if (root != null) return root.toStringTree();
		return "";
	}
	

	public ArrayList<Object> toArrayList(){
		ArrayList<Object> elements = new ArrayList<Object>();
		if (root != null) root.toArrayList(elements);
		return elements;
	}
	
	public BinaryNode getRoot(){
		return root;
	}
	
	public int height(){
		if(root == null)
			return -1;
		else
			return root.height(root);
	}
	
	public void reverse(){
		//TODO: implement
	}
	
	public boolean isRedBlackTree(){
		if(root == null)
			return true;
		if(root.color == Color.RED)
			return false;
		return root.isRBTree();
	}


	
	public enum Color {RED, BLACK}
	
	public class BinaryNode{
		private T element;
		private BinaryNode leftChild;
		private BinaryNode rightChild;
		private Color color;
		
		public BinaryNode(T element){
			this.element = element;
			this.leftChild = null;
			this.rightChild = null;	
			this.color = Color.RED;
		}
		
		public boolean isRBTree(){
			if(this.color == Color.RED){
				if(this.leftChild != null)
					if(this.leftChild.color == Color.RED)
						return false;
				if(this.rightChild != null)
					if(this.rightChild.color == Color.RED)
						return false;
			}
			
			if(leftChild != null && rightChild != null)
				if(blackHeight(leftChild) != blackHeight(rightChild))
						return false;
			else if(leftChild != null)
				if(blackHeight(leftChild) != 1)
					return false;
			else if(rightChild != null)
				if(blackHeight(rightChild) != 1)
					return false;
			
			if(leftChild != null && rightChild != null)
				return leftChild.isRBTree() && rightChild.isRBTree();
			else if(leftChild == null)
				return rightChild.isRBTree();
			else if(rightChild == null)
				return leftChild.isRBTree();
			
			return true;
		}
		
		private int blackHeight(BinaryNode p){
			int addNum = 0;
			if(p == null)
				return 1;
			if(p.color == Color.BLACK)
				addNum++;
			return addNum + Math.max(this.blackHeight(p.leftChild), this.blackHeight(p.rightChild));
		}
		
		public int height(BinaryNode p){
			if(p == null)
				return -1;
			return 1 + Math.max(this.height(p.leftChild), this.height(p.rightChild));
	    }
		
		public void setBlack(){
			color = Color.BLACK;
		}
		
		public void setRed(){
			color = Color.RED;
		}
		
		public Color getColor(){
			return color;
		}
		
		public T getElement(){
			return element;
		}
		
		public void setLeftChild(BinaryNode n){
			leftChild = n;
		}
		
		public void setRightChild(BinaryNode n){
			rightChild = n;
		}
			
		public String toString(){
			return element.toString();
		}
		
		public String toStringTree(){
			String s = "[";
			String l = "";
			String r = "";
			s += element + " ";
			if (leftChild != null){
				s += leftChild.element + " ";
				l = leftChild.toStringTree();
			} else {
				s += "[]";
			}
			if (rightChild != null){
				s += rightChild.element + " ";
				r = rightChild.toStringTree();
			} else {
				s += "[]";
			}
			return s + "]" + this.color + "\n" + l + r;
		}
		
		public void toArrayList(ArrayList<Object> elements){
			elements.add(this);
			if (leftChild != null) leftChild.toArrayList(elements);
			if (rightChild != null) rightChild.toArrayList(elements);			
		}
	}
}
