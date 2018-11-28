package markovAnalyzer;

import java.io.*;
import java.util.*;

public class MarkovMain {

	public static void printArr (int[] A) {
		for(int i = 0; i < A.length; i++) {
			System.out.print(A[i] + " ");
		}
		System.out.println();
	}
	public static void printArr (double[] A) {
		for(int i = 0; i < A.length; i++) {
			System.out.print(A[i] + " ");
		}
		System.out.println();
	}
	public static void print2DArr (int[][] A) {
		for(int i = 0; i < A.length; i++) {
			for(int j = 0; j < A[0].length; j++) {
				System.out.print(A[i][j] + " ");
			}
			System.out.println();
		}
	}
	public static void print2DArr (double[][] A) {
		for(int i = 0; i < A.length; i++) {
			for(int j = 0; j < A[0].length; j++) {
				System.out.print(A[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static boolean allOne (int[] A) {
		for(int i = 0; i < A.length; i++) {
			if (i != 1) return false;
		}
		return true;
	}
	public static boolean copy (int[] A, int[] sample) {
		int num = A.length;
		boolean flag = false;
		for(int i = 0; i < num; i++) {
			if (sample[i] == 1) {
				if (A[i] == 0) {
					A[i] = 1; flag = true;
				}
			}
		}
		return flag;
	}
	
	
	public static int[] stepN (int[][] A, int n) {
		int num = A.length;
		int[] row = A[n];
		boolean flag = true;
		while (flag) {
			flag = false;
			for(int i = 0; i < num; i++) {
				if (row[i] == 1) {
					boolean temp = copy(row, A[i]);
					if (temp) flag = temp;
				}
			}
			if (allOne(row)) return row;
		}
		
		return row;
	}

	
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// change file path
		File file = new File ("C:\\Users\\Frank\\Desktop\\CMU General\\36218 Prob Theory for CS\\HW10\\Test2.txt");
		
		Scanner sc = new Scanner (file);
		
		int numClasses = sc.nextInt();
		double[][] TPM = new double[numClasses][numClasses];
		int[][] acc = new int[numClasses][numClasses];
		int[][] accOne = new int[numClasses][numClasses];
		for(int i = 0; i < numClasses; i++) {
			for(int j = 0; j < numClasses; j++) {
				double temp = sc.nextDouble();
				TPM[i][j] = temp;
				acc[i][j] = temp > 0 ? 1 : 0;
				accOne[i][j] = temp > 0 ? 1 : 0;
			}
		}
		double[] alpha = new double[numClasses];
		if (sc.hasNextDouble()) {
			for(int i = 0; i < numClasses; i++) {
				alpha[i] = sc.nextDouble();
			}
		} else {
			alpha[0] = 1.0;
			for(int i = 1; i < numClasses; i++) {
				alpha[i] = 0.0;
			}
		}
		
		//construct the accessible states
		int[][] accN = new int[numClasses][numClasses];
		for(int i = 0; i < numClasses; i++) {
			accN[i] = stepN(acc, i);
		}
		
		System.out.println("TPM: ");
		print2DArr(TPM);
		System.out.println("accOne: ");
		print2DArr(accOne);
		System.out.println("alpha: ");
		printArr (alpha);
		System.out.println("accN: ");
		print2DArr (accN);
		
	}
	
	
}
