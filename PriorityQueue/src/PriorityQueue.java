import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Nick Crawford
 * 
 * Java implementation of a PriorityQueue. Uses a min Heap structure to hold elements.
 *
 * @param <T>
 */
public class PriorityQueue <T extends Comparable<? super T>> extends ArrayList<T>{
	
	private int size;
	
	/**
	 * Creates a new PriorityQueue
	 */
	public PriorityQueue(){
		this.size = 0;
	}
	
	/**
	 * @returns the size (number of elements) in the queue
	 */
	public int size(){
		return this.size;
	}
	
	/**
	 * Inserts an element to the PriorityQueue in minimum fashion (smallest on top).
	 * @param e the element to add
	 * @returns true if sucessful, false if otherwise
	 */
	public boolean add(T e){
		if(e == null)
			throw new NullPointerException();
		boolean temp = super.add(e);
		if(temp){
			this.size++;
			this.percolateUp(this.size - 1);
		}
		return temp;
	}
	
	/**
	 * Inserts an element to the PriorityQueue in minimum fashion (smallest on top).
	 * @param e the element to add
	 * @returns true if sucessful, false if otherwise
	 */
	public boolean offer(T e){
		return this.add(e);
	}
	
	/**
	 * Returns, but does not remove, the highest priority element in the queue.
	 * @return the highest priority element in the queue.
	 */
	public T peek(){
		if(this.size == 0)
			return null;
		return super.get(0);
	}
	
	/**
	 * Returns and removes the highest priority element in the queue.
	 * @return the highest priority element in the queue.
	 */
	public T poll(){
		if(this.size == 0)
			return null;
		T removed = super.remove(0);
		if(this.size == 1){
			size--;
			return removed;
		}
		if(removed != null){
			this.size--;
			super.add(0, super.remove(this.size - 1));
			this.percolateDown(0);
			return removed;
		}
		return null;
	}
	
	private void percolateUp(int index){
		if((this.size != 0 || this.size != 1) && index != 0){
			T lastElem = super.get(index);
			T parent = super.get(index / 2);
			if(index % 2 == 0)
				parent = super.get(index / 2 - 1);
			if(lastElem.compareTo(parent) < 0){
				super.set(index, parent);
				if(index % 2 == 0){
					super.set(index / 2 - 1, lastElem);
					this.percolateUp(index / 2 - 1);
				}else{
					super.set(index / 2, lastElem);
					this.percolateUp(index / 2);
				}
			}
		}
	}
	
	private void percolateDown(int index){
		if(this.size != 0 || this.size != 1){
			T parent = super.get(index);
			T leftChild = null;
			T rightChild = null;
			if(index*2 + 1 < this.size)
				leftChild = super.get(index*2 + 1);
			if(index*2 + 2 < this.size)
				rightChild = super.get(index*2 + 2);
			int slot = this.determineSlot(parent, leftChild, rightChild);
			if(slot == 0){
				//pass, no children to check
			}else if(slot == 1){
				super.set(index*2 + 1, parent);
				super.set(index, leftChild);
				this.percolateDown(index*2 + 1);
			}else if(slot == 2){
				super.set(index*2 + 2, parent);
				super.set(index, rightChild);
				this.percolateDown(index*2 + 2);
			}	
		}
	}
	
	private int determineSlot(T parent, T LC, T RC){
		//0 = nowhere, 1 = left, 2 =  right
		if(LC == null && RC == null){
			//no children
		}else{
			if(LC != null && RC != null){
				if(LC.compareTo(parent) < 0 && LC.compareTo(RC) < 0)
					return 1;
				if(RC.compareTo(parent) < 0 && RC.compareTo(LC) < 0)
					return 2;
			}else if(LC != null && RC == null){
				if(LC.compareTo(parent) < 0)
					return 1;
			}
		}
		return 0;
	}
	
}
