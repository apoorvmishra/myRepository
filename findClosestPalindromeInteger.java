/* This code can be used to get the closest palindrome integer for any integer input*/

package edu.uic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindClosestPalindromeInteger {

    public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);

        System.out.println("Enter any number: ");
        int n = sc.nextInt();
        List<Integer> reverseNumberInput = new ArrayList<>();
        while(n!=0){
            reverseNumberInput.add(n%10);
            n=n/10;
        }
        List<Integer> numberInput = new ArrayList<>();
        for(int i= reverseNumberInput.size()-1; i>=0; i--){
            numberInput.add(reverseNumberInput.get(i));
        }
        for(int i=0; i<numberInput.size()/2; i++){
            if(!(numberInput.get(i).equals(reverseNumberInput.get(i)))){
                reverseNumberInput.set(i, numberInput.get(i));
            }
        }

        for(Integer digit: reverseNumberInput){
            System.out.print(digit);
        }
    }
}
