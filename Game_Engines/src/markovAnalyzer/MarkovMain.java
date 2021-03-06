package markovAnalyzer;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import Jama.*;

public class MarkovMain {

	//functions to print out 1D or 2D arrays 
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
	public static void printArr (boolean[] A) {
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
	public static void print2DArr (boolean[][] A) {
		for(int i = 0; i < A.length; i++) {
			for(int j = 0; j < A[0].length; j++) {
				System.out.print(A[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	//function to check if int array contains only ones
	public static boolean allOne (int[] A) {
		for(int i = 0; i < A.length; i++) {
			if (i != 1) return false;
		}
		return true;
	}
	
	//function to copy sample to A, and returning a boolean to indicate whether anything is changed or not
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
	
	//find the reachable states from every state after any number of steps
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

	//funciton to get the greatest common denominator of a pair of integers
	public static int gcdInt (int a, int b) {
		return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
	}
	
	public static int findPeriod (int[][] TPM) {
		int num = TPM.length;
		boolean[][] steps = new boolean[num][num];
		for(int i = 0; i < num; i++) {
			for(int j = 0; j < num; j++) {
				steps[i][j] = false;
			}
		}
		for(int i = 0; i < num; i++) {
			steps[0][i] = (TPM[0][i] == 1) ? true : false;
		}
		
		for(int i = 1; i < num; i++) {
			//traverse row i-1
			for(int j = 0; j < num; j++) {
				if(steps[i-1][j]) {
					//copy row j from TPM to steps
					for(int k = 0; k < num; k++) {
						steps[i][k] = (TPM[j][k] == 1) ? true : false;
					}
				}
			}
		}
		
		//print2DArr(steps);
		
		int accIn = 0;
		for(int i = 0; i < num; i++) {
			accIn = (steps[i][0]) ? accIn + 1 : accIn;
		}
		if (accIn == 0) return 1;
		int[] acc = new int[accIn];
		int count = 0;
		for(int i = 0; i < num; i++) {
			if(steps[i][0]) {
				acc[count] = i + 1;
				count++;
			}
		}
		int per = acc[0];
		for(int i = 0; i < accIn - 1; i++) {
			per = gcdInt(per, acc[i + 1]);
		}
		
		return per;
	}
	
	//find the stationary distribution given TPM of a communicating class
	public static double[][] findStatDist (double[][] TPM) {
		int num = TPM.length;
		Matrix I = Matrix.identity(num, num);
		Matrix P = new Matrix(TPM);
		Matrix U = new Matrix(num, num, 1.0);
		Matrix one = new Matrix(1, num, 1.0);
		Matrix res = one.times(((I.minus(P)).plus(U)).inverse());
		return res.getArray();
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// change file path
		File file = new File ("C:\\Users\\Frank\\Desktop\\CMU General\\36218 Prob Theory for CS\\HW10\\Test2.txt");
		
		Scanner sc = new Scanner (file);
		
		//numClasses (small typo, should be numStates) represent the number of states in the markov chain
		int numClasses = sc.nextInt();
		double[][] TPM = new double[numClasses][numClasses];
		//acc and accOne are matrices that contain 1 if one state can reach the other in one step, 0 otherwise
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
		//construct the initial distribution, whether given or not
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
		
		//construct the accessible states, after arbitrary steps
		int[][] accN = new int[numClasses][numClasses];
		for(int i = 0; i < numClasses; i++) {
			accN[i] = stepN(acc, i);
		}
		
		//calculate number of communicating classes in markov chain
		//classesTemp: 2D boolean array to show whether or not two states belong to the same class
		int classesCount = 0;
		boolean[][] classesTemp = new boolean[numClasses][numClasses];
		boolean[] flags = new boolean[numClasses];
		for(int i = 0; i < numClasses; i++) {
			flags[i] = false;
			for(int j = 0; j < numClasses; j++) {
				classesTemp[i][j] = false;
			}
		}
		for(int i = 0; i < numClasses; i++) {
			if (!flags[i]) {
				flags[i] = true;
				classesTemp[classesCount][i] = true;
				for(int j = i + 1; j < numClasses; j++) {
					if(!flags[j] && accN[i][j] == 1 && accN[j][i] == 1) {
						flags[j] = true;
						classesTemp[classesCount][j] = true;
					}
				}
				classesCount++;
			}
		}
		
		//classes: 2D array, (number of classes) by (number of states)
		//The first positive number of entries in each row are the states in the communicating class
		//the rest of the row is padded with -1, all -1 (in the entire program) should be ignored
		//In addition, the ordering of classes here determine the labeling of the classes, so the 0 class is the first row and so on
		int[][] classes = new int[classesCount][numClasses];
		for(int i = 0; i < classesCount; i++) {
			for(int j = 0; j < numClasses; j++) {
				classes[i][j] = -1;
			}
		}
		for(int i = 0; i < classesCount; i++) {
			int cur = 0;
			for(int j = 0; j < numClasses; j++) {
				if(classesTemp[i][j]) {
					classes[i][cur] = j;
					cur++;
				}
			}
		}
		
		//numStates: number of states in each class
		int[] numStates = new int[classesCount];
		for(int i = 0; i < classesCount; i++) {
			int[] thisClass = classes[i];
			int numInClass = 0;
			for(int j = 0; j < thisClass.length; j++) {
				numInClass = (thisClass[j] != -1) ? numInClass + 1 : numInClass;
			}
			numStates[i] = numInClass;
		}
		
		//determine the class types, whether recurrent or transient
		//true = recurrent, false = transient
		boolean[] classTypes = new boolean[classesCount];
		for(int i = 0; i < classesCount; i++) {
			boolean ans = true;
			for(int j = 0; j < numClasses; j++){
				if(classesTemp[i][j]) {
					for(int k = 0; k < numClasses; k++) {
						if (!classesTemp[i][k] && accN[j][k] == 1) ans = false;
					}
				}
			}
			classTypes[i] = ans;
		}
		
		//determine number of transient (recurrent) classse (states)
		int numRecur = 0;
		int numTrans = 0;
		int numRecurStates = 0;
		int numTransStates = 0;
		for(int i = 0; i < classesCount; i++) {
			if(classTypes[i]) {
				numRecur++;
				numRecurStates += numStates[i];
			} else {
				numTrans++;
				numTransStates += numStates[i];
			}
		}
		
		
		//put all transient states in one array, sort in ascending order
		int[] transStates = new int[numTransStates];
		int count = 0;
		for(int i = 0; i < classesCount; i++) {
			if(!classTypes[i]) {
				for(int j = 0; j < numStates[i]; j++) {
					transStates[count] = classes[i][j];
					count++;
				}
			}
		}
		Arrays.sort(transStates);
		
		//put all recurrent states in one array, sort in ascending order
		int[] recurStates = new int[numRecurStates];
		count = 0;
		for(int i = 0; i < classesCount; i++) {
			if(classTypes[i]) {
				for(int j = 0; j < numStates[i]; j++) {
					recurStates[count] = classes[i][j];
					count++;
				}
			}
		}
		Arrays.sort(recurStates);
		
		//calculate periods for each class, by first constructing the TPM and calling helper function
		int[] periods = new int[classesCount];
		for(int i = 0; i < classesCount; i++) {
			int[] thisClass = classes[i];
			int numInClass = 0;
			for(int j = 0; j < thisClass.length; j++) {
				numInClass = (thisClass[j] != -1) ? numInClass + 1 : numInClass;
			}
			int[][] tempTPM = new int[numInClass][numInClass];
			for(int j = 0; j < numInClass; j++) {
				for(int k = 0; k < numInClass; k++) {
					tempTPM[j][k] = accOne[thisClass[j]][thisClass[k]];
				}
			}
			//System.out.println("Got through: " + i);
			//print2DArr(tempTPM);
			periods[i] = findPeriod(tempTPM);
		}
		
		//Fun with matrices
		//At this point, JAMA library is used to complete matrix calculations
		//https://math.nist.gov/javanumerics/jama/
		double[][] T = new double[numTransStates][numTransStates];
		for(int i = 0; i < numTransStates; i++) {
			for(int j = 0; j < numTransStates; j++) {
				T[i][j] = TPM[transStates[i]][transStates[j]];
			}
		}
		Matrix matrixT;
		if(numTransStates == 0) {
			matrixT = new Matrix(0, 0);
		} else {
			matrixT = new Matrix(T);
		}
		Matrix idT = Matrix.identity(numTransStates, numTransStates);
		Matrix oneT = new Matrix(numTransStates, 1, 1.0);
		Matrix matrixV = (idT.minus(matrixT)).inverse();
		Matrix matrixM = (matrixV).times(oneT);
		double[][] M = matrixM.getArray();
		double[][] V = matrixV.getArray();
		
		double[][] transProb = new double[numTransStates][1];
		for(int i = 0; i < numTransStates; i++) {
			transProb[i][0] = alpha[transStates[i]];
		}
		//Matrix matrixTransProb = new Matrix(transProb);
		Matrix matrixTransProb;
		if(numTransStates == 0) {
			matrixTransProb = new Matrix(0, 1);
		} else {
			matrixTransProb = new Matrix(transProb);
		}
		
		double[][] recurProb = new double[numRecurStates][1];
		for(int i = 0; i < numRecurStates; i++) {
			recurProb[i][0] = alpha[recurStates[i]];
		}
		Matrix matrixRecurProb = new Matrix(recurProb);
		
		//expected # of visits to each trans state
		Matrix matrixTransVisit = matrixV.transpose().times(matrixTransProb);
		double[][] transVisit = matrixTransVisit.getArray();
		
		//expected time until some recurrent class is hit
		Matrix matrixTimeTilRecur = matrixM.transpose().times(matrixTransProb);
		double timeTilRecur = matrixTimeTilRecur.getArray()[0][0];
		
		//prob of hitting each recur class
		double[][] R = new double[numTransStates][numRecur];
		for(int i = 0; i < numTransStates; i++) {
			int recurCount = 0;
			for(int j = 0; j < classesCount; j++) {
				if(classTypes[j]) {
					double temp = 0.0;
					for(int k = 0; k < numStates[j]; k++) {
						temp += TPM[transStates[i]][classes[j][k]];
					}
					R[i][recurCount] = temp;
					recurCount++;
				}
			}
		}
		//Matrix matrixR = new Matrix(R);
		Matrix matrixR;
		if(numTransStates == 0) {
			matrixR = new Matrix(0, numRecur);
		} else {
			matrixR = new Matrix(R);
		}
		Matrix matrixH = matrixV.times(matrixR);
		double[][] H = matrixH.getArray();
		double[] hitProb = new double[numRecur];
		
		int recurCount = 0;
		for(int i = 0; i < classesCount; i++) {
			if(classTypes[i]) {
				double temp = 0.0;
				for(int j = 0; j < numStates[i]; j++) {
					temp += alpha[classes[i][j]];
				}
				for(int j = 0; j < numTransStates; j++) {
					temp += H[j][recurCount] * alpha[transStates[j]];
				}
				hitProb[recurCount] = temp;
				recurCount++;
			}
		}
		
		//finding stationary distributions for recurring classes
		double[][] statDist = new double[numRecur][numClasses];
		for(int i = 0; i < numRecur; i++) {
			for(int j = 0; j < numClasses; j++) {
				statDist[i][j] = -1.0;
			}
		}
		recurCount = 0;
		for(int i = 0; i < classesCount; i++) {
			if(classTypes[i]) {
				if(periods[i] == 1) {
					//if aperiodic, calculate statDist
					int numInClass = numStates[i];
					double[][] P = new double[numInClass][numInClass];
					for(int j = 0; j < numInClass; j++) {
						for(int k = 0; k < numInClass; k++) {
							P[j][k] = TPM[classes[i][j]][classes[i][k]];
						}
					}
					double[][] temp = findStatDist(P);
					for(int j = 0; j < numInClass; j++) {
						statDist[recurCount][j] = temp[0][j];
					}
				}
				//if periodic recurrent, leave row as -1
				recurCount++;
			}
		}
		
		//output stream with some explanation
		
		//original TMP
		System.out.println("TPM: "); print2DArr(TPM);
		//One step accessible states
		System.out.println("accOne: "); print2DArr(accOne);
		//Initial distribution
		System.out.println("alpha: "); printArr (alpha);
		//N-steps accessible states
		System.out.println("accN: "); print2DArr (accN);
		//Number of communicating classes
		System.out.println("Number of Classes: " + classesCount);
		//optional, please ignore
		System.out.println("classesTemp: "); print2DArr (classesTemp);
		//Communicating classes, padded with -1's that should be ignored
		System.out.println("classes: ");print2DArr (classes);
		//Number of states in each class, ordering with respect to above classes array
		System.out.println("num of states in each class: "); printArr (numStates);
		//Class types, ordering with respect to above classes array
		System.out.println("classTypes(true = recurrent): "); printArr (classTypes);
		//Number of recurrent and transient classes
		System.out.println(numRecur + " recurrent classes; " + numTrans + " transient classes");
		//Number of recurrent and transient states
		System.out.println(numRecurStates + " recurrent states; " + numTransStates + " transient states");
		//Periods of each class, ordering with respect to above classes array
		System.out.println("periods: "); printArr (periods);
		//Transient states (potentially empty), ascending order
		System.out.println("Transient states: "); printArr(transStates);
		//Recurrent states (potentially empty), ascending order
		System.out.println("Recurrent states: "); printArr(recurStates);
		//optional, please ignore
		System.out.println("Matrix T: "); print2DArr(T);
		//optional, please ignore
		System.out.println("Matrix M: "); print2DArr(M);
		//optional, please ignore
		System.out.println("Matrix V: "); print2DArr(V);
		//optional, please ignore
		System.out.println("Trans prob: "); print2DArr(transProb);
		//optional, please ignore
		System.out.println("Recur prob: "); print2DArr(recurProb);
		//Expected number of visits to each transient state, potentially empty
		//ordering with respect to above "transient states" array
		System.out.println("Expected # of visits to each trans state: "); print2DArr(transVisit);
		//Expected time until some recurrent class is hit
		System.out.println("Expected time until some recurrent class is hit: \n" + timeTilRecur);
		//optional, please ignore
		System.out.println("Matrix R: "); print2DArr(R);
		//optional, please ignore
		System.out.println("Matrix H: "); print2DArr(H);
		//Probability of hitting each recurrent class, ordering with respect to above classes array
		System.out.println("Hitting probs: "); printArr(hitProb);
		//Stationary distributions for recurrent classes
		//contains all recurrent classes, with periodic ones only -1's
		//ordering with respect to classes array
		//please ignore the -1.0's
		System.out.println("Stationary Distributions: "); print2DArr(statDist);
	}
	
	
}
