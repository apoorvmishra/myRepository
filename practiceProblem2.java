package codeJam.practiceRound;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class practiceProblem2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i,j;
		int no_of_test_cases=0;
		int no_of_tickets=0;
		String[] airports = new String[2*no_of_tickets];
		Map<String, String> tics = new HashMap<String, String>();
		Map<String, String> reverse_tics = new HashMap<String, String>();
		Scanner s = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		no_of_test_cases = s.nextInt();
		for(i=0; i<no_of_test_cases; i++){
			no_of_tickets = s.nextInt();
			for(j=0; j<airports.length ; j++){
				
				airports[j] = s.next();
				
			}
			for(j=0; j<airports.length; j = j+2){
				tics.put(airports[j], airports[j+1]);
			}
			for(Map.Entry<String, String> e: tics.entrySet()){
				reverse_tics.put(e.getValue(), e.getKey());
			}
			String start = null;
			for(Map.Entry<String, String> e: tics.entrySet()){
				if(!reverse_tics.containsKey(e.getKey())){
					start = e.getKey();
					break;
				}
			}
			int m;
			m = i+1;
			System.out.print("Case #" + m + ": ");
			String dest = tics.get(start);
			while(dest != null){
				System.out.print(start +"-"+dest+" ");
				start = dest;
				dest = tics.get(dest);
			}
			
		}
		s.close();
		
	}

}
