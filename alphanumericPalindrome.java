/* This code takes string as an input and checks if it is a palindrome, while considering alphabets and numerics only*/

public class AlphaNumericPalindrome {
    public static void main(String[] args){
        String input = "Ab?/Ba";
        input = input.toLowerCase().replaceAll(" ", "");
        input = input.replaceAll("[^a-zA-Z0-9]","");
        char[] inputArray = input.toCharArray();
        for(int i=0; i<inputArray.length; i++){
            if(inputArray[i] != inputArray[inputArray.length-1-i]){
                System.out.println("NO!!!!!!");
                break;
            }
            if(i==inputArray.length-1)
                System.out.println("YES!!!!!!!!!!");
        }

    }
}
