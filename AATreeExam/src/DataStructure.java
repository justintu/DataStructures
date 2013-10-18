import java.util.HashMap;
import java.util.PriorityQueue;

public class DataStructure {

	private HashMap<String, Student> students;
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
	
	public DataStructure(){
		this.students = new HashMap<String, Student>();
	}
	
	public void add(Student s){
		this.students.put(s.getID(), s);
	}
	
	public Student retrieve(String key){
		return this.students.get(key);
	}
	
	public Student findKthSmallest(int k){
		PriorityQueue<Student> q = new PriorityQueue<Student>(this.students.values());
		Student kth = null;
		for(int i = 0; i<k; i++){
			kth = q.poll();
		}
		return kth;
	}
	
}
