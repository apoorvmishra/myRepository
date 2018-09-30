/* This code prints the diamond pattern

    *  
   * *  
  * * *  
 * * * *  
* * * * *  
 * * * *  
  * * *  
   * *  
    * 
*/ 


public class patternDiamond {

    public static void main(String[] args){

        int n = 5;
        System.out.println("This is a code to print the pattern of a diamond");
         for(int i=1; i<=n; i++) {
             for (int j = n-i; j >= 1; j--) {
                 System.out.print(" ");
             }
             for (int k = 1; k <= i; k++) {
                 System.out.print("* ");
             }
             System.out.println(" ");
         }
         for(int i =n-1; i>=1; i--){
             for(int j=1; j<=n-i; j++){
                 System.out.print(" ");
             }
             for(int k =1; k<=i; k++){
                 System.out.print("* ");
             }
             System.out.println(" ");
         }
    }
}
