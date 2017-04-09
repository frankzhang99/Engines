
public class Sudoku_Solver {

	//mother grid user passed in
	//use 0 to represent empty box
	static int userInput[][] = new int[][]
			   {{0, 0, 0, 1, 2, 3, 0, 0, 0},
				{0, 0, 0, 4, 5, 6, 0, 0, 0},
				{0, 0, 0, 7, 8, 9, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0}};
	
	//grid to work on
	static int grid[][] = new int[9][9];
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				grid[i][j]=userInput[i][j];
			}
		}
		
		//print(userInput);
		System.out.println(validity(2, 3, grid));
		//String s = "020020001";
		//System.out.println(check(s, 1));
	}
	
	//checks if a 9x9 grid at a given point is valid
	//checks row, column, and 3x3 box
	//note: only checks the number at that point
	public static boolean validity(int x, int y, int[][] grid){
		String vertical = "", horizontal = "", box = "";
		int goal = grid[x][y];
		for (int i = 0; i < 9; i++){
			vertical += grid[i][y];
			horizontal += grid[x][i];
			box += grid[(x/3)*3+i/3][(y/3)*3+i%3];
		}
		return check(vertical, goal) && check(horizontal, goal) && check(box, goal);
	}
	
	//checks if a string of number only contains 1 goal number
	public static boolean check(String s, int a){
		int counter = 0;
		for (int i = 0; i < s.length(); i++){
			if (Integer.parseInt(s.substring(i, i+1)) == a){
				counter++;
			}
		}
		return counter==1;
	}
	
	//prints a grid
	public static void print(int[][] grid){
		System.out.println();
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++){
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
}
