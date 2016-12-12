package lab10;

/**
 * 
 * COMP 3021
 * 
This is a class that prints the maximum value of a given array of 90 elements

This is a single threaded version.

Create a multi-thread version with 3 threads:

one thread finds the max among the cells [0,29] 
another thread the max among the cells [30,59] 
another thread the max among the cells [60,89]

Compare the results of the three threads and print at console the max value.

 * 
 * @author valerio
 *
 */
public class FindMax {
	// this is an array of 90 elements
	// the max value of this array is 9999
	static int[] array = { 1, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2, 3, 4543,
			234, 3, 454, 1, 2, 3, 1, 9999, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3, 1, 34, 5, 6, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3 };

	public static void main(String[] args) {
		//new FindMax().printMax();
		FindMax obj = new FindMax();
		Max1 max1 = obj.new Max1();
		Max2 max2 = obj.new Max2();
		Max3 max3 = obj.new Max3();
		Thread thread1 = new Thread(max1);
		Thread thread2 = new Thread(max2);
		Thread thread3 = new Thread(max3);
		thread1.start(); thread2.start(); thread3.start();
		try {
			thread1.join(); thread2.join(); thread3.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		int max = max1.getNum();
		if (max2.getNum() > max)
			max = max2.getNum();
		if (max3.getNum() > max)
			max = max3.getNum();
		System.out.println("the max value is " + max);
	}

	public void printMax() {
		// this is a single threaded version
		int max = findMax(0, array.length - 1);
		System.out.println("the max value is " + max);
	}

	/**
	 * returns the max value in the array within a give range [begin,range]
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private int findMax(int begin, int end) {
		// you should NOT change this function
		int max = array[begin];
		for (int i = begin + 1; i <= end; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
	
	private class Max1 implements Runnable {
		private int maxNum1;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int max = array[0];
			for (int i = 0 + 1; i <= 29; i++) {
				if (array[i] > max) {
					max = array[i];
				}
			}
			maxNum1 = max;
		}
		public int getNum() {
			return maxNum1;
		}
	}
	
	private class Max2 implements Runnable {
		private int maxNum2;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int max = array[30];
			for (int i = 30 + 1; i <= 59; i++) {
				if (array[i] > max) {
					max = array[i];
				}
			}
			maxNum2 = max;
		}
		public int getNum() {
			return maxNum2;
		}
	}
	
	private class Max3 implements Runnable {
		private int maxNum3;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int max = array[60];
			for (int i = 60 + 1; i <= 89; i++) {
				if (array[i] > max) {
					max = array[i];
				}
			}
			maxNum3 = max;
		}
		public int getNum() {
			return maxNum3;
		}
	}
}
