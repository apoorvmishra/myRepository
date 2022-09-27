/* This code checks whether the input string is balanced or not. 
Eg. The string "(a(a{}))" is balanced whereas, "({)}" is not balanced.
*/

import java.util.Scanner;
import java.util.Stack;

public class BalancedString {
    static Scanner sc = new Scanner(System.in);

    public static void main (String args[]) {
        System.out.println("Enter string with parentheses");
        String inputString = sc.next();
        System.out.println(checkBalancedString(inputString));
    }

    public static boolean checkBalancedString(String input) {
        char[] inputArray = input.toCharArray();
        Stack<Character> parenthesesStack = new Stack();
        for(char character: inputArray){
            if(character == '(' || character == '{' || character == '['){
                parenthesesStack.push(character);
            }
            if((character == ')' || character == '}' || character == ']')) {

                if(parenthesesStack.empty())
                    return false;

                if ((parenthesesStack.peek() == '{' && character == '}')
                        || (parenthesesStack.peek() == '(' && character == ')')
                        || (parenthesesStack.peek() == '[' && character == ']'))
                    continue;
                else
                    return false;

            }
        }
        return true;
    }
}
