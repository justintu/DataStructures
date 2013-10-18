import java.util.ArrayList;

public class MyTests {
	
	public static void main(String[] args){
	
		testToString1();
	//	testArrayList();
	}
	
	public static void testToString1(){
		PriorityQueue<Integer> q = new PriorityQueue<Integer>();
		q.add(3);
		q.add(5);
		q.add(2);
		q.add(4);
		q.add(2);
		System.out.println(q);
		q.poll();
		System.out.println(q);
		q.poll();
		System.out.println(q);
		q.poll();
		System.out.println(q);
/*		Object[] a = q.toArray();
		for(int i=0; i < a.length; i++)
			System.out.println(a[i]);
*/
	}
	
	public static void testArrayList(){
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(null);
		System.out.println(a);
		a.add(1);
		System.out.println(a);
		a.add(2);
		System.out.println(a);
		a.add(3);
		System.out.println(a);
		a.add(1);
		System.out.println(a);
	}
	
}
