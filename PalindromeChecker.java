package com.wallethub;

/**
 * 
 * @author j.nicholas
 * Palindrome check
 */
public class PalindromeChecker {
	public static boolean isPalindrome(String s) {
	    int len = s.length();
	    //return TRUE if empty of single string
	    if (len <= 1) {
	        return true;
	    }
	    String lower = s.toLowerCase();
	    
	    return (lower.charAt(0) == lower.charAt(len - 1)) &&
	           isPalindrome(lower.substring(1, len - 1));
	}

	public static void main(String args[]) {		
		System.out.println(isPalindrome("RaceCar"));
		System.out.println(isPalindrome("civic"));
		System.out.println(isPalindrome("WALLetHuB"));
	}
}
