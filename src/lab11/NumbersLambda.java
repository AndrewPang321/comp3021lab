package lab11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NumbersLambda {
	
	// Find numbers with certain properties in a unified form
	// The property is specified in Predicate
	
	public static List<Integer> findNumbers(List<Integer> list, Predicate<Integer> predicate) {
		List<Integer> results = new ArrayList<Integer>();
		for (int n : list) {
			if (predicate.test(n)) results.add(n);
		}
		return results;
	}
	
	public static List<Integer> calculateScore(List<Integer> list, Function<Integer, Integer> function) {
		// TODO: Task 3
		List<Integer> results = new ArrayList<>();
		for (Integer num : list) {
			results.add(function.apply(num));
		}
		return results;
	}
	
	public static Function<Integer, Integer> policy() {
		// TODO: Task 3
		Predicate<Integer> odd = isOdd();
		Predicate<Integer> prime = isPrime();
		Predicate<Integer> palindrome = isPalindrome();
		Function<Integer, Integer> results = s -> {
			int credit = 0;
			if (odd.test(s))
				credit += 1;
			if (prime.test(s))
				credit += 2;
			if (palindrome.test(s))
				credit += 4;
			
			return credit;
		};
		return results;
	}
	
	/**
	 * BETTER version of policy()
	 * @return
	 */
	public static Function<Integer, Integer> policy1() {
		// TODO: Task 3

		return s -> {
			int credit = 0;
			if (isOdd().test(s))
				credit += 1;
			if (isPrime().test(s))
				credit += 2;
			if (isPalindrome().test(s))
				credit += 4;
			
			return credit;
		};	
	}
	
	
	public static Predicate<Integer> isOdd() {
		// TODO: Task 2
		return x -> x % 2 != 0;
	}
	
	public static Predicate<Integer> isPrime() {
		// TODO: Task 2
		return x -> {
			boolean flag = false;
			int m = x / 2;
			for (int i = 2; i <= m; i++) {
				if (x % i == 0) {
					flag = true;
					return false;
				}
			}
			if (flag == false)
				return true;
			
			return false;
		};
	}
	
	public static Predicate<Integer> isPalindrome() {
		// TODO: Task 2
		return x -> {
			int palindrome = x;
			int reverse = 0;
			
			while (palindrome > 0) {
				int remainder = palindrome % 10;
				reverse = reverse * 10 + remainder;
				palindrome = palindrome / 10;
			}
			if (x == reverse)
				return true;
			else
				return false;
		};
	}
	
//	public static Function<Integer, Integer> policy() {
//		// TODO: Task 3
//		Predicate<Integer> predicate;
//		Function<NumbersLambda, Integer> results = s -> {
//			
//		};
//	}
	
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(480,514,484,389,709,935,328,169,649,300,685,429,243,532,308,87,25,282,91,415);
		
		System.out.println("The odd numbers are : " + findNumbers(numbers,isOdd()));
		System.out.println("The prime numbers are : " + findNumbers(numbers,isPrime()));
		System.out.println("The palindrome numbers are : " + findNumbers(numbers,isPalindrome()));
		
		System.out.println("The score of the all numbers are :" + calculateScore(numbers, policy1()));
	}
}
