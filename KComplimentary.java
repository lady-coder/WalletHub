package com.wallethub;
import java.util.HashMap;
import java.util.Map;

/**
 * @author j.nicholas
 * Algorithm to find complimentary pairs of numbers in an array
 * This has runtime complexity of O(N) 
 */

public class KComplimentary {
	public static void main(String args[])
	{
		int A[] ={21,6, 7, 12, 4, -2};
		int k = 19; 
		Map<Integer, Integer> compMap = new HashMap<Integer, Integer>();
		for(int i=0; i < A.length ; i++)
		{
			int compliment = k - A[i];
			int tempValue = compMap.containsKey(compliment) ? compMap.get(compliment)+1 : 1;
			compMap.put(compliment, tempValue);
		}
		//If  key is a value in array then it's complimentary
		//A[i]+A[j]=k    A[j] = k - A[i]
		//so if A[j] exists in the map then add its value to the counter
		int cnt = 0;
		for(int j=0;j<A.length;j++)
		{
			if(compMap.containsKey(A[j]))
				cnt += compMap.get(A[j]);
		}
		
		System.out.println("K complimentary is " + cnt);
	}
	
}
