import junit.framework.TestCase;

public class Testing extends TestCase{

    public static void main(String args[]) {
        junit.swingui.TestRunner.run(Testing.class);
    }    

    public static void testSimple1() {
        // 1 move
        new Puzzle("123456708");
    }

    public static void testSimple2(){
        // 2 moves
        new Puzzle("123456078");
    }

    public static void testSimple3(){
        // 7 moves
        new Puzzle("136520478");
    }

    public static void testLong1(){
        // 22 moves
        new Puzzle("560231847");
    }
    
    public static void testLong2(){
    	// 20
    	new Puzzle("615402783");
    }
    
    public static void testLong3(){
    	//24 moves
    	new Puzzle("568207314");
    }
    
    public static void testLong4(){
    	//18 moves
    	new Puzzle("182405673");
    }
    
    public static void testMark1(){
    	//22
    	new Puzzle("610438527");
    }
    
    public static void testMark2(){
    	//22
    	new Puzzle("071368254");
    }
    
    public static void testMark3(){
    	//20
    	new Puzzle("052763418");
    }
    
    public static void testMark4(){
    	//26
    	new Puzzle("027543681");
    }
    
    public static void testMark5(){
    	//21
    	new Puzzle("504237186");
    }
    
    public static void testRandom1(){
    	//17
    	new Puzzle("751823406");
    }
    
    public static void testRandom2(){
    	//26
    	new Puzzle("620134857");
    }
    
    public static void testRandom3(){
    	//26
    	new Puzzle("062871543");
    }
    
    public static void testRandom4(){
    	//20
    	new Puzzle("463185720");
    }
    
    public static void testRandom5(){
    	//16
    	new Puzzle("542368170");
    }
    
    public static void testRandom6(){
    	//24
    	new Puzzle("013862547");
    }
    
    public static void testRandom7(){
    	//26 (ours)
    	//28 (theirs)
    	new Puzzle("357641028");
    }
    
    public static void testRandom8(){
    	//24
    	new Puzzle("053862417");
    }
    
    public static void testException1(){
    	new Puzzle("111111111");
    }
    
    public static void testException2(){
    	new Puzzle("123456789");
    }
    
    public static void testException3(){
    	new Puzzle("12345678");
    }
}