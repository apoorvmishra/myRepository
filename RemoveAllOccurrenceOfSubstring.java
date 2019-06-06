package edu.uic;

public class RemoveAllOccurrenceOfSubstring {

    public static void main(String args[]){
        final String mainString = "abbbbbab"; // the main string
        final String substring = "ab"; // the substring to be removed
        System.out.println("Max moves to remove all occurences of substring: " + maxMoves(mainString, substring));
    }

    /** Method to calculate the maximum number of moves to remove all the occurences of the substring
     *
     * @param s is the main string passed from the main function
     * @param t is the substring passed from the main function
     * @return the maximum number of moves
     */
    private static int maxMoves(String s, String t) {
        int count = 0;
        while(s.contains(t)){
            String s1 = s.replaceFirst(t,"");
            String s2 = replaceLast(s, t);

            // Here we are checking that the number of occurrences of the substring is higher when we remove it from
            // the start or from the end and based on the result we select the option and update the main string
            if(numberOfOccurrences(s1,t) >= numberOfOccurrences(s2,t)){
                s = s1;
            }
            else{
                s = s2;
            }
            count++;
        }
        return count;
    }

    /** Method to compute the string if the substring is removed from the end
     *
     * @param s is the main string
     * @param t is the substring
     * @return string after removing the substring t from s
     */
    private static String replaceLast(String s, String t){
        int last = s.lastIndexOf(t);
        if(last == -1){
            return s;
        }
        return s.substring(0,last) + "" + s.substring(last+t.length(), s.length());
    }

    /** Method to calculate the number of occurrences of the substring
     *
     * @param s is the main string
     * @param t is the substring
     * @return count of the occurrences of the substring
     */
    private static int numberOfOccurrences(String s, String t){
        int count = 0;
        int index = 0;
        while((index=s.indexOf(t, index))!= -1){
            index++;
            count++;
        }
        return count;
    }

}
