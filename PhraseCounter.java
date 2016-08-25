package com.wallethub;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
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
		

		//Sorting the map in most frequently occuring order. 
		//Should be a linkedhashmap if we want to keep the order
		 LinkedHashMap<String, Integer> sortedMap = 
				 phraseCount.entrySet().stream()
                .sorted(Entry.comparingByValue(FREQUENT_ORDER))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                          (e1, e2) -> e1, LinkedHashMap::new));
		
		 System.out.println("Frequently occuring phrases \n"+sortedMap);
		 
		//If file size is 10GB, then it can be split into 1GB files and checked for occurences by adding all hashmap entries
		//////////////
		//splitFiles();
		//////////////
		
	}
	
	/**
	 * To split the file if the size is big
	 */
	public static void splitFiles()
	{
		try{
			RandomAccessFile raf = new RandomAccessFile("C:/Users/j.nicholas/Desktop/phrasetest.txt", "r");
	        long numSplits = 10; 
	        long sourceSize = raf.length();
	        long bytesPerSplit = sourceSize/numSplits ;
	        long remainingBytes = sourceSize % numSplits;

	        int maxReadBufferSize = 8 * 1024; //8KB
	        for(int destIx=1; destIx <= numSplits; destIx++) {
	            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("split."+destIx));
	            if(bytesPerSplit > maxReadBufferSize) {
	                long numReads = bytesPerSplit/maxReadBufferSize;
	                long numRemainingRead = bytesPerSplit % maxReadBufferSize;
	                for(int i=0; i<numReads; i++) {
	                    readWrite(raf, bw, maxReadBufferSize);
	                }
	                if(numRemainingRead > 0) {
	                    readWrite(raf, bw, numRemainingRead);
	                }
	            }else {
	                readWrite(raf, bw, bytesPerSplit);
	            }
	            bw.close();
	        }
	        if(remainingBytes > 0) {
	            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("split."+(numSplits+1)));
	            readWrite(raf, bw, remainingBytes);
	            bw.close();
	        }
	            raf.close();
		}
		catch(Exception e)
		{}
	}
	
	
	public static void readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) throws IOException {
	        byte[] buf = new byte[(int) numBytes];
	        int val = raf.read(buf);
	        if(val != -1) {
	            bw.write(buf);
	        }
	    }
	

	public static Map<String,Integer> buildPhraseCount(String fileName)
	{
		
		
		Map<String, Integer> phraseMap =  new HashMap<>();
	
		//Using try-with-resource statements for automatic resource management in Java8. All resources opened in try block will automatically closed by Java
		try (
			FileInputStream fis = new FileInputStream(fileName);
			DataInputStream dis = new DataInputStream(fis);
			//BufferedReader reads the file in chunks, so doesn't need huge heap to read
			BufferedReader br = new BufferedReader(new InputStreamReader(dis))
			){
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
					
				}
				
			}
			
			
		
		} catch (IOException e) {
			System.out.println("Problem accessing file");
			e.printStackTrace();
		}
		return phraseMap;
	}

}
