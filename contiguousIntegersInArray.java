/* This code checks whether integers in a list are contiguous integers or not. (Duplicates are considered to be true) 
Eg. If a list has [1,5,2,3,4] then it will result to TRUE because the elements are [1,2,3,4,5]. Whereas, if list is [1,3,5,7,9] then the result will be false. 
*/
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ContiguousIntegers {

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("How many integers you want to enter?");
        int n = sc.nextInt();
        List<Integer> inputIntegerArray = new ArrayList<Integer>();
        System.out.println("Enter Integer array");
        for(int i=0; i<n; i++){
            inputIntegerArray.add(sc.nextInt());
        }
        System.out.println(areThereContiguousIntegers(inputIntegerArray));
    }

    public static boolean areThereContiguousIntegers(List<Integer> input){
        input.sort(Comparator.naturalOrder());
        boolean result = false;
        System.out.println(input);
        for(int i=0; i<input.size()-1; i++){
            if (((input.get(i)-input.get(i+1))==-1)||(((input.get(i)-input.get(i+1))==0))){
                result = true;
            }else {
                result = false;
            }
        }
        return result;

    }
}
