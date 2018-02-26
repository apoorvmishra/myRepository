package com.google;

import java.util.HashSet;

class Solution1 {
    public static void main(String[] args)  {
    	 String s = "abcdhfjgkg";
         char[] c = s.toCharArray();
         char[] a;
         HashSet sub = new HashSet<>();
          for (int i=0; i<c.length; i++){
                if(!sub.contains(c[i])){
                    sub.add(c[i]);
                }
              
                	
                }
        }
        int sizeOfSubString = sub.size();
        
    
}
