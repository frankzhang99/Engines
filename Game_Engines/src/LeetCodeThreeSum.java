import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class LeetCodeThreeSum {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] test = {0, 0, 0, 1, -1, 2, 3, -4, -5, -3, -3, 1, 1};
		print2(threeSum(test));
		//System.out.println(Arrays.binarySearch(test, 5));
	}

	
	public static List<List<Integer>> threeSum(int[] nums){
		int num_pos = 0;
		int num_neg = 0;
		int num_zero = 0;
		for(int i : nums){
			if (i > 0){
				num_pos++;
			}
			if (i < 0){
				num_neg++;
			}
			if (i == 0){
				num_zero++;
			}
		}
		int[] pos = new int[num_pos];
		int pp = 0;
		int[] neg = new int[num_neg];
		int nn = 0;
		int[] zero = new int[num_zero];
		int zz = 0;
		for(int i = 0; i < nums.length; i++){
			if(nums[i] >0){
				pos[pp] = nums[i];
				pp++;
			}
			if(nums[i] <0){
				neg[nn] = nums[i];
				nn++;
			}
			if(nums[i] ==0){
				zero[zz] = nums[i];
				zz++;
			}
		}
		bubbleSort(pos);
		print(pos);
		bubbleSort(neg);
		print(neg);
		bubbleSort(zero);
		ArrayList<List<Integer>> answer = new ArrayList<List<Integer>> ();
		
		//add case [0, 0, 0]
		if(num_zero>=3){
			List<Integer> temp = new ArrayList<Integer>();
			temp.add(0);
			temp.add(0);
			temp.add(0);
			answer.add(temp);
		}
		//main algorithm 1
		//pair 1 pos with 1 neg and 1 zero
		if(num_zero>0){
			int last_tested = -1;
			for(int i = 0; i < pos.length; i++){
				//if neg contains the ith index of pos
				if(pos[i] != last_tested){
					last_tested = pos[i];
					int index = Arrays.binarySearch(neg, -pos[i]);
					if (index >= 0){
						List<Integer> temp = new ArrayList<Integer>();
						temp.add(0);
						temp.add(pos[i]);
						temp.add(neg[index]);
						answer.add(temp);
					}
				}
			}
		}
		
		
		//for each int in pos
		//find all possible combinations of 2 int in neg that adds up to that
		
		
		//for each int in neg
		//find pair of int in pos
		
		
		
		return answer;
	}
	
	
	
	public static void bubbleSort(int[] numArray) {
	    int n = numArray.length;
	    int temp = 0;
	    for (int i = 0; i < n; i++) {
	        for (int j = 1; j < (n - i); j++) {

	            if (numArray[j - 1] > numArray[j]) {
	                temp = numArray[j - 1];
	                numArray[j - 1] = numArray[j];
	                numArray[j] = temp;
	            }

	        }
	    }
	}
	
	public static void print(int[] a){
		for(int i = 0; i < a.length; i++){
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
	
	public static void print2(List<List<Integer>> a){
		for(int i = 0; i < a.size(); i++){
			for (int j = 0; j < a.get(0).size(); j++){
				System.out.print(a.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}
}
