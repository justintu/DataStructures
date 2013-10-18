
public class Student implements Comparable<Student>{
	private String id;
	private String firstName;
	private String lastName;
	
	public Student(String id, String firstName, String lastName){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getID(){
		return id;
	}
	
	public String toString(){
		return id + " " + firstName + " " + lastName;
	}

	public int compareTo(Student s) {
		return id.compareTo(s.id);
	}
}	
