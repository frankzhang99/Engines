//https://www.youtube.com/watch?v=gN8xrMwrLSc
//attempt to optimize Sudoku_Solver and test this method
//which prunes out all bad values before testing
public class Sudoku_Solver_2 {

	//create main to test a few cases!
	public static void main(String[] args){
		int[][] board1 = 
			{{0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
	   		 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0}};
		int[][] board2 = 
			{{5, 6, 2, 0, 1, 0, 0, 4, 0},
			 {0, 4, 0, 6, 0, 0, 1, 0, 2},
			 {7, 0, 0, 0, 2, 4, 8, 5, 0},
	   		 {0, 0, 7, 0, 0, 0, 5, 9, 0},
			 {3, 0, 0, 0, 0, 0, 0, 0, 1},
			 {0, 1, 8, 0, 0, 0, 7, 0, 0},
			 {0, 3, 5, 4, 8, 0, 0, 0, 7},
			 {1, 0, 6, 0, 0, 2, 0, 8, 0},
			 {0, 8, 0, 0, 6, 0, 3, 2, 5}};
		int[][] board3 = 
			{{0, 9, 0, 0, 0, 0, 0, 7, 0},
			 {0, 0, 0, 0, 0, 5, 0, 0, 1},
			 {0, 0, 5, 9, 2, 0, 0, 0, 0},
	   		 {2, 0, 0, 0, 4, 0, 3, 0, 0},
			 {8, 4, 0, 0, 7, 0, 0, 2, 6},
			 {0, 0, 7, 0, 9, 0, 0, 0, 5},
			 {0, 0, 0, 0, 3, 8, 2, 0, 0},
			 {4, 0, 0, 2, 0, 0, 0, 0, 0},
			 {0, 2, 0, 0, 0, 0, 0, 6, 0}};
		
		
		System.out.println("My sudoku");
		print(board2);
		double start = System.currentTimeMillis();
		if (Solve(board2)){
			double stop = System.currentTimeMillis();
			System.out.println("Found a solution!");
			print(board2);
			double used = stop - start;
			System.out.println("This took " + used + " milliseconds to solve");
		}else{
			System.out.println("No solution found!");
		}
		
	}
	
	
	//creates a helper print method
	public static void print(int[][] board){
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	
	
	//define the method header
	//empty spaces are 0, and pre-set numbers are 1-9
	public static boolean Solve(int[][] board){
		//creates support array to keep track of status
		int[][] status = new int[board.length][board[0].length];
		//fill the status board
		//0 means empty, 2 means pre-set, 1 means program-set (used later)
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				status[i][j] = board[i][j]>0 ? 2:0;
			}
		}
		//now we use both array to start at the first element
		return Solve(board, status, 0, 0);
	}
	
	//recursive method
	public static boolean Solve(int[][] board, int[][] status, int x, int y){
		
		//first we see if we have come to the end
		if (x == 9){
			//check if all values are set
			int count = 0; //we need count to be 81 for it to be complete
			for (int i=0; i<9; i++){
				for (int j=0; j<9; j++){
					count += status[i][j]>0 ? 1:0; //update count only if status[i][j] is non zero (1 or 2)
					
				}
			}
			if (count == 81){
				return true; //found one solution
			}
			else{
				return false; //attempt failed
			}
		}
		//otherwise we proceed forward
		if (status[x][y] >= 1){ // or status[x][y]==2 ?
			int nextX = x;
			int nextY = y+1;
			//test if that was end of a row
			if (nextY == 9){
				nextX = x+1;
				nextY = 0;
			}
			//recursive call next position
			return Solve(board, status, nextX, nextY);
		} else {
			//key method
			//prunes out all unavailable numbers and only tests the numbers that will work once plugged in
			//creates 9-length boolean array
			boolean[] used = new boolean[9];
			
			//check row
			for (int i=0; i<9; i++){
				if (status[x][i] >= 1){ //or, status[x][i] == 2 ??
					used[board[x][i]-1] = true; //calls board to get the true value, not 0, 1, or 2 (status values)
				}
			}
			
			//check column
			for (int i=0; i<9; i++){
				if (status[i][y] >= 1){ //or, status[i][y] == 2 ??
					used[board[i][y]-1] = true; //calls board to get the true value, not 0, 1, or 2 (status values)
				}
			}
			
			//check 3x3 box
			for (int i = x-(x%3); i < x-(x%3) + 3; i++){
				for (int j = y-(y%3); j < y-(y%3) + 3; j++){
					if (status[i][j] >= 1){ //or, status[i][j] == 2 ??
						used[board[i][j]-1] = true; //calls board to get the true value, not 0, 1, or 2 (status values)
					}
				}
			}
			
			//after these checks we try each possible value
			//we designate 1 as program-set values, to differentiate from 2 (pre-set), and 0 (empty)
			//if doesn't work later, we remove all the 1s before
			for (int i=0; i<used.length; i++){
				if(!used[i]){
					//set and proceed, and reverse for next iteration
					status[x][y] = 1; //1 means program-set
					board[x][y] = i+1; //i+1 is the real value to be put the board
					//same index-increasing function
					int nextX = x;
					int nextY = y+1;
					//test if that was end of a row
					if (nextY == 9){
						nextX = x+1;
						nextY = 0;
					}
					//recursive call next position
					if (Solve(board, status, nextX, nextY)){
						return true;
					}
					//now it means settings failed and we reverse the settings
					for (int m=0; m<9; m++){
						for (int n=0; n<9; n++){
							//only reverse the values behind (x, y) position!
							if (m>x || (m==x && n>=y)){
								if (status[m][n]==1){ //only reverse the program-set values, not the pre set values!
									status[m][n] = 0;
									board[m][n] = 0; //don't forget to remove the board value too
								}
							}
						}
					}
				}
			}
		}
		//return a default false
		return false;
	}
	
		
}
