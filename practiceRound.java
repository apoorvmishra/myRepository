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
