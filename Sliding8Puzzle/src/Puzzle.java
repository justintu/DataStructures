/**
 * @author Nick Crawford
 * @author Samad Jawaid
 * 
 * Millisecond code readout gratis Mark Vitale.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * A representation of a sliding N-puzzle with dimensions of (N-1)x(N-1). The Puzzle class contains a "goal" state, i.e. the solution to the puzzle, as well as the initial puzzle state.
 * When the a new Puzzle object is instantiated, the starting state will be processed and a list of moves will be output that provide the optimal solution to the problem.
 *
 */
public class Puzzle {
    private final static int DIM = 3; // dimension
    private final State GOAL = new State("123456780");
    private PriorityQueue<State> open = new PriorityQueue<State>();
    private ArrayList<State> closed = new ArrayList<State>();
//    private HashSet<State> closed = new HashSet<State>();
    private State current;
    private int openCounter;

    /**
     * Creates a new Puzzle and prompts the user for input in the terminal.
     * A state is given in the format "123456780" reading from top left to bottom right of the state, with 0 being the empty slot.
     * If an invalid state is given, the user will be prompted until a valid state is provided.
     * 
     * A sequence of moves for the optimal solution will be provided along with the run time (ms) of the calculations performed.
     */
    public Puzzle() {
        System.out.println("Enter the initial state:");
        Scanner bob = new Scanner(System.in);
        String state = bob.next();
        while (!Puzzle.isValidState(state)) {
            System.out.println("Enter the initial state:");
            state = bob.next();
        }
        this.openCounter = 1;
        this.current = new State(state);
        long startTime = System.currentTimeMillis();
        this.solve();
        System.out.println("Time: " + (System.currentTimeMillis() - startTime) + " milliseconds");
    }

    /**
     * Creates a new Puzzle with given input string.
     * A state is given in the format "123456780" reading from top left to bottom right of the state, with 0 being the empty slot.
     * If an invalid state is given, the user will be prompted until a valid state is provided.
     * 
     * A sequence of moves for the optimal solution will be provided along with the run time (ms) of the calculations performed, given input is a valid state.
     * @param input the initial state of the Puzzle.
     */
    public Puzzle(String input){
        if(!Puzzle.isValidState(input)){
			System.out.println("\n" + input + " is an invalid Puzzle state. No action taken.\n");
			return ;
        }
    	this.openCounter = 1;
        this.current = new State(input);
        long startTime = System.currentTimeMillis();
        this.solve();
        System.out.println("Time: " + (System.currentTimeMillis() - startTime) + " milliseconds");
    }

    /**
     * Finds the optimal sequence of moves for the Puzzle from it's current state to the GOAL state.
     */
    public void solve() {
        System.out.println("---\n");
        State x;
        this.open.offer(this.current);
        while (!this.open.isEmpty()) {
            x = this.open.poll();
            if (x.equals(this.GOAL)) {
                x.print();
                System.out.println("# of Moves: " + x.f);
                System.out.println("Total # of elements added to P.Q.: " + this.openCounter);
                return ;
            } else {
                ArrayList<State> children = x.generateChildren();
                this.closed.add(x);
                for (State child : children) {
                    if (!this.isPreviousState(child)) {
                        this.open.offer(child);
                        this.openCounter++;
                    }
                }
            }
        }
    }

    /**
     * Checks to see if the given state is a valid Puzzle configuration. This does not check if it is an impossible position.
     * @param state the state to check.
     * @return true if valid, false if otherwise.
     */
    public static boolean isValidState(String state) {
        if (state.length() != Math.pow(DIM, 2)) {
            return false;
        }
        ArrayList<String> nums = new ArrayList<String>();
        for (int i = 0; i < Math.pow(DIM, 2); i++) {
            nums.add(i + "");
        }
        for (String s : state.split("")) {
            nums.remove(s);
        }
        return nums.size() == 0;
    }
    
    private boolean isPreviousState(State s){
    	for (State oldState : this.closed){
    		if(s.equals(oldState))
    			return true;
    	}
    	
    	for (State oldState : this.open){
    		if(s.equals(oldState))
    			return true;
    	}
    	
    	return false;
    }

    private class State implements Comparable<State> {
        private int[][] state = new int[DIM][DIM];
        private State parent;
        private int h;
        private int f;
        private int g;

        /**
         * Creates a new State with given configuration
         * @param state a String representation of the State to create.
         */
        public State(String state) {
            String[] nums = state.split("");
            int k = 1;
            for (int i = 0; i < DIM; i++) {
                for (int j = 0; j < DIM; j++) {
                    this.state[i][j] = Integer.parseInt(nums[k]);
                    k++;
                }
            }
            this.parent = null;
            this.h = 0;
            this.f = 0;
            this.g = 0;
        }

        /**
         * Creates a new State from a given array.
         * @param state a primitive array representation of the State to create.
         * @param parent the previous State that this new State was derived from.
         */
        public State(int[][] state, State parent) {
            this.state = state;
            this.parent = parent;
            this.h = this.h();
            this.f = this.parent.f + 1;
            this.g = this.h + this.f;
        }

        /**
         * Generates all valid moves for this state
         * @return an ArrayList of States that represent each move possible from this State.
         */
        public ArrayList<State> generateChildren() {
            ArrayList<State> children = new ArrayList<State>();
            int[] xy = this.locate(0);
            int x = xy[0];
            int y = xy[1];
            int[][] child;
            if (x - 1 >= 0) {
                child = this.clone(this.state);
                child[x][y] = child[x-1][y];
                child[x-1][y] = 0;
                children.add(new State(child, this));
            }
            if (x + 1 < DIM) {
                child = this.clone(this.state);
                child[x][y] = child[x+1][y];
                child[x+1][y] = 0;
                children.add(new State(child, this));
            }
            if (y - 1 >= 0) {
                child = this.clone(this.state);
                child[x][y] = child[x][y-1];
                child[x][y-1] = 0;
                children.add(new State(child, this));
            }
            if (y + 1 < DIM) {
                child = this.clone(this.state);
                child[x][y] = child[x][y+1];
                child[x][y+1] = 0;
                children.add(new State(child, this));
            }
            return children;
        }

        private int h() {
            int h1 = 0;
            for (int i = 0; i < DIM; i++) {
                for (int j = 0; j < DIM; j++) {
                    if (this.state[i][j] != 0) {
                        h1 += h(i, j);
                    }
                }
            }
            return h1;
        }

        private int h(int i, int j) {
            int[] xy = GOAL.locate(this.state[i][j]);
            return diff(i, xy[0]) + diff(j, xy[1]);
        }

        private int[] locate(int n) {
            for (int i = 0; i < DIM; i++) {
                for (int j = 0; j < DIM; j++) {
                    if (this.state[i][j] == n) {
                        int[] xy = {i, j};
                        return xy;
                    }
                }
            }
            return null;
        }

        private int diff(int n1, int n2) {
            return Math.abs(n1 - n2);
        }
        
        /**
         * Creates a new, separate array representation of this State.
         * @param array the array representation of this State.
         * @return a new primitive array representation of this same State.
         */
        public int[][] clone(int[][] array) {
            int[][] newArray = (int[][]) array.clone();
            for(int row = 0; row < array.length; row++){
                newArray[row] = (int[]) array[row].clone();
            }
            return newArray;
        }

        /**
         * Determines if this State is equal to another. This is determined by the position of each element in the two States.
         * @param o the State to compare to.
         * @return true if the same, false if otherwise.
         */
        public boolean equals(State o){
        	for (int i = 0; i<DIM; i++)
        		if(!Arrays.equals(this.state[i], o.state[i]))
        				return false;
            return true;
        }

        /**
         * Compares the overall goodness value of this State to another.
         * This is determined by (heuristic + total moves to this position) of each. The lower the better.
         * @return an int, either 0, 1, -1. 0 they are equal, 1 this is bigger, -1 this is smaller.
         */
        public int compareTo(State o) {
        	if(this.g > o.g)
        		return 1;
        	else if(this.g < o.g)
        		return -1;
        	else
        		return 0;
        }

        /**
         * Prints out the current State.
         */
        public void print() {
            if (this.parent != null) {
                this.parent.print();
            }
            System.out.println(this);
        }

        /**
         * Produces a String representation of this State.
         * @return a String representation of this State.
         */
        public String toString() {
            String s = "";
            for (int i = 0; i < DIM; i++) {
                for (int j = 0; j < DIM; j++) {
                    s += this.state[i][j] + " ";
                }
                s = s.trim();
                s += "\n";
            }
            return s.replace("0", " ");
        }
    }
}