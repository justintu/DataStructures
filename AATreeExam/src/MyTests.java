public class MyTests {
	
	public static void main(String[] args){
		test1();
	}
	
	public static void test1(){
		DataStructure s = new DataStructure();
		Student s1 = new Student("2","Moe", "Stooge");
		Student s2 = new Student("1","Larry", "Stooge");
		Student s3 = new Student("4","Shemp", "Stooge");
		Student s4 = new Student("3","Curly", "Stooge");
		
		s.add(s1);
		s.add(s2);
		s.add(s3);
		s.add(s4);
		
		System.out.println(s.findKthSmallest(2).getID());
		//System.out.println(s.retrieve("3"));
	}
}
