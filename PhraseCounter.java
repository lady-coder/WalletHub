package com.wallethub;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 
 * @author j.nicholas
 * Top 100000 most frequent phrases in a file, works in JDK 8
 * 
 */
public class PhraseCounter {
	
	static final Comparator<Integer> FREQUENT_ORDER = new Comparator<Integer>() {
        public int compare(Integer t1, Integer t2) {            
                return t2.compareTo(t1);

        }
	};
	public static void main(String args[]) throws FileNotFoundException, IOException {
		Map<String, Integer> phraseCount = buildPhraseCount("C:/Users/j.nicholas/Desktop/phrasetest.txt");
		//Now sort the map in most frequently occuring order. 
		//Should be a linkedhashmap if we want to keep the order
		 LinkedHashMap<String, Integer> sortedMap = 
				 phraseCount.entrySet().stream()
                .sorted(Entry.comparingByValue(FREQUENT_ORDER))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                          (e1, e2) -> e1, LinkedHashMap::new));
		
		 System.out.println("Frequently occuring phrases \n"+sortedMap);
	}
	

	  
	public static Map<String,Integer> buildPhraseCount(String fileName)
	{
		
		int initSize = (int) Math.ceil(5);
		Map<String, Integer> phraseMap =  new HashMap<>();
		
		//Using try-with-resource statements for automatic resource management in Java8. All resources opened in try block will automatically closed by Java
		try (
			FileInputStream fis = new FileInputStream(fileName);
			DataInputStream dis = new DataInputStream(fis);
			//BufferedReader reads the file in chunks, so doesn't need huge heap to read
			BufferedReader br = new BufferedReader(new InputStreamReader(dis))){
			String line = null;
			Pattern p = Pattern.compile("\\|");

			while((line = br.readLine())!=null)
			{
				line = line.toLowerCase();
				String[] phrases = p.split(line);
				for(String str : phrases)
				{
					if(phraseMap.containsKey(str))
						phraseMap.put(str,phraseMap.get(str)+1);
					else
						phraseMap.put(str, 1);
					//if(phraseMap.size()>100000)
					//	break;
				}
				
			}
			
			
		
		} catch (IOException e) {
			System.out.println("Problem accessing file");
			e.printStackTrace();
		}
		return phraseMap;
	}

}
