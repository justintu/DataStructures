
public class MyTests {
	public static void main(String[] args){
		test1();
	}
	
	public static void test1(){
		int[][] a = {{0,1},{2,3},{4,5}};
		int[][] b = a.clone();
		b[2][0] = 9;
		for(int i =0; i < a.length; i++)
			for(int j =0; j < a.length; j++)
				System.out.print(a[i][j] + " ");
		System.out.println(a.equals(b));
		for(int i =0; i < b.length; i++)
			for(int j =0; j < b.length; j++)
				System.out.print(b[i][j] + " ");
	}
}
