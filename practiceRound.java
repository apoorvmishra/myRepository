/*There exists a straight line along which cities are built.

Each city is given a number starting from 1. So if there are 10 cities, city 1 has a number 1, city 2 has a number 2,... city 10 has a number 10.

Different buses (named GBus) operate within different cities, covering all the cities along the way. The cities covered by a GBus are represented as 'first_city_number last_city_number' So, if a GBus covers cities 1 to 10 inclusive, the cities covered by it are represented as '1 10'

We are given the cities covered by all the GBuses. We need to find out how many GBuses go through a particular city.*/
package codeJam.practiceround1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;


public class practiceRound {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int t,i,j,k;
		int n;
		int p;
		int m;
		Scanner s = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		t = s.nextInt();
		for(j=0; j<t; j++){
			n = s.nextInt();
			int[] buslist = new int[2*n];
			for (i=0; i<2*n; i++){
				buslist[i] = s.nextInt();
			}
			p = s.nextInt();
			int[] city = new int[p];
			int[] count = new int[p]; 
			for (i=0; i<p; i++){
				city[i] =  s.nextInt();
			}
			for(i=0; i<2*n; i=i+2){
				for(k=0; k<p; k++){
					if(city[k]>=buslist[i] && city[k]<=buslist[i+1]){
						count[k]++;
					}
				}
			}
			m=j+1;
			System.out.print("Case #" + m + ": ");
			for(k=0; k<p; k++){
				System.out.print(count[k]+" ");
			}
			System.out.println();
		}
		
		s.close();
	}

}
