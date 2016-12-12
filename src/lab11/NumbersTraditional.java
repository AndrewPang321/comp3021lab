package lab11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumbersTraditional {
	
	public static List<Integer> isOdd(List<Integer> numbers) {
		List<Integer> results = new ArrayList<Integer>();
		for (int n : numbers) {
			if (n % 2 != 0) results.add(n);
		}
		return results;
	}
	
	public static List<Integer> isPrime(List<Integer> numbers) {
		List<Integer> results = new ArrayList<Integer>();
		// TODO
		// Find out all the prime numbers 
		int m = 0;
		
		for (Integer num : numbers) {
			boolean flag = false;
			m = num / 2;
			
			for (int i = 2; i <= m; i++) {
				if (num % i == 0) {
					flag = true;
					break;
				}
			}			
			if (flag == false)
				results.add(num);
		}
		return results;
	}
	
	public static List<Integer> isPalindrome(List<Integer> numbers) {
		List<Integer> results = new ArrayList<Integer>();
		// TODO
		// Find out all the palindrome numbers, such as 484 and 121.
		for (Integer num : numbers) {
			int palindrome = num;
			int reverse = 0;
			
			while (palindrome > 0) {
				int remainder = palindrome % 10;
				reverse = reverse * 10 + remainder;
				palindrome = palindrome / 10;
			}
			if (num == reverse)
				results.add(num);
		}
		return results;
	}
		
	
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(480,514,484,389,709,935,328,169,649,300,685,429,243,532,308,87,25,282,91,415);
		
		System.out.println("The odd numbers are : " + isOdd(numbers));
		System.out.println("The prime numbers are : " + isPrime(numbers));
		System.out.println("The palindrome numbers are : " + isPalindrome(numbers));
		
	}
}
