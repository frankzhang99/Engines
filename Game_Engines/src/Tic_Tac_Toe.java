import java.util.Scanner;
import java.util.Stack;

public class Tic_Tac_Toe {
	public static void main(String[] args){
//		3 by 3 version of Tic-Tac-Toe
		boolean[][] check = new boolean[3][3];
		boolean playerWin = false;
		boolean computerWin = false;
		boolean win = (playerWin || computerWin);
		
//		Represent the moves made by both players
		Stack<String> player = new Stack<String>();
		Stack<String> computer = new Stack<String>();

//		Must match winningCombinations to win
//		Only 8 winning combinations
		String[][] winningCombinations = {{"0", "01", "02"}, {"10", "10", "12"}, {"20", "21", "22"},
				{"00", "11", "22"}, {"02", "11", "20"}, {"00", "10", "20"},  {"01", "11", "21"}, {"02", "12", "22"}};
		
		
		System.out.println("Do you want to go first?");
		
//		Print color in the console to represent a checked position
		Scanner scan = new Scanner(System.in);
		
		
		while(!win){
			String move = scan.nextLine();
			
		}
	}
}
