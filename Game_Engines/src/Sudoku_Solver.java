
public class Sudoku_Solver {

	//mother grid user passed in
	//use 0 to represent empty box
	static int userInput[][] = new int[][]
			   {{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 8, 0, 0}};
	
	//grid to work on
	static int grid[][] = new int[9][9];
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				grid[i][j]=userInput[i][j];
			}
		}
		System.out.println("Original: ");
		print(grid);
		System.out.println("\n\n");
		double start = System.currentTimeMillis();
		System.out.println("Solved: ");
		print(loop(0, 0, grid));
		double stop = System.currentTimeMillis();
		double used = stop - start;
		System.out.println("\nThis took " + used + " milliseconds to solve");
		//System.out.println(validity(2, 3, grid));
		//String s = "020020001";
		//System.out.println(check(s, 1));
	}
	
	//main loop
	public static int[][] loop (int y, int x, int[][] grid){
		
		while(!checkAll(grid) || grid[8][8] == 0){
			//while not solved
			if (userInput[y][x] != 0){
				int yy, xx;
				if (x == 8){
					yy = y+1;
					xx=0;
				} else {
					yy = y;
					xx = x+1;
				}
				loop(yy, xx, grid);
			} else {
				if (grid[y][x] < 9){
					grid[y][x]++;
					if (validity(y, x, grid)){
						int yy, xx;
						if (x == 8){
							yy = y+1;
							xx=0;
						} else {
							yy = y;
							xx = x+1;
						}
						loop(yy, xx, grid);
					}
				} else {
					grid[y][x] = 0;
					break;
				}
			}
		}
		return grid;
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
	
	//brute force checks whole board for error
	public static boolean checkAll(int[][] grid){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if (!validity(i, j, grid)){
					return false;
				}
			}
		}
		return true;
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
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++){
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
}
