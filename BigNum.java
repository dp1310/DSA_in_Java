package model;

import java.util.Arrays;

import model.*;

public class BigNum {
    private BigNum() {
        super();
    }
    
    private String str;
    private Integer[] array;
    
    private BigNum(String stri) {
        int i = 0;
        while(i < stri.length()){
            if((stri.charAt(i) > '9') || (stri.charAt(i) < '0')) {
                throw new NumberFormatException("Incorrect Number Format paagal");
            }
            i++;
        }
        
        str = stri;
        array = new Integer[stri.length()/9 + (stri.length()%9 == 0 ? 0:1)];
        
        while(i>0){
            int temp = 0;
            i--;
            
            for(int count = (i-8 > 0 ? i-8 : 0); count <= i; count++){
                temp = (temp<<3) + (temp<<1) + stri.charAt(count) - '0';
            }
            
            array[i/9] = temp;
            i -= 8;  
        }
            
    }
    
    public static String add (String a, String b) {
        BigNum p,q;
        a = trim(a);
        b = trim(b);
        try {
            p = new BigNum(a);
            q = new BigNum(b);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Addition Failed");
            return null;
        }
        return trim(add(p,q));
    }
    
    private static String add (BigNum a, BigNum b) {
        int i = a.array.length;
        int j = b.array.length;
        Integer[] result = new Integer[i>j?i:j];
        int k = result.length;
        int carry = 0;
        int temp_sum;
        
        while((i!=0) && (j!=0)){
            i--;
            j--;
            k--;
            temp_sum = b.array[j] + a.array[i] + carry;
            if(temp_sum >= 1000000000){
                carry = 1;
                result[k] = temp_sum - 1000000000;
            }
            else{
                result[k] = temp_sum;
                carry = 0;
            }
        }
        
        if(i==0){
            while(j!=0){
                j--;
                k--;
                temp_sum = b.array[j] + carry;
                if(temp_sum >= 1000000000){
                    carry = 1;
                    result[k] = temp_sum - 1000000000;
                }
                else{
                    result[k] = temp_sum;
                    carry = 0;
                }
            }
        }
        else{
            while(i!=0){
                i--;
                k--;
                temp_sum = a.array[i] + carry;
                if(temp_sum >= 1000000000){
                    carry = 1;
                    result[k] = temp_sum - 1000000000;
                }
                else{
                    result[k] = temp_sum;
                    carry = 0;
                }
            }
        }
        
        String re = new String("");
        re = carry==0 ? re : re+1;
        for(Integer temp : result){
            String s = "" + temp;
            int len = s.length();
            while(len<9){
                s = "0" + s;
                len++;
            }
            re = re + s;
        }
        return re;
    }
    
    
    /**
     *
     * @param a Big Number in String Format
     * @param b
     * @return returns the result in String Format
     */
    public static String subs(String a, String b) {
        BigNum p,q;
        a = trim(a);
        b = trim(b);
        if(a.length() > b.length()) {
            int len_dif = a.length() - b.length();
            while(len_dif!=0) {
                b = 0+b;
                len_dif--;
            }
        }
        
        if(a.length() < b.length()) {
            int len_dif = b.length() - a.length();
            while(len_dif!=0) {
                a = 0+a;
                len_dif--;
            }
        }
        
        b = comp(b);
        
        try {
            p = new BigNum(a);
            q = new BigNum(b);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Subtraction Failed");
            return null;
        }
        return trim(subs(p,q));
    }
    
    private static String subs(BigNum a, BigNum b) {
        String t = add(a,b);
        if(t.length()>a.str.length()) {
            t = t.substring(1);
            return add(t,"1");
        }
        if(t.length()<a.str.length()) {
            int len_dif = a.str.length() - t.length();
            while(len_dif!=0) {
                t = 0+t;
                len_dif--;
            }
        }
        
        return "-"+trim(comp(t));
    }
    
    private static String trim(String a) {
        int i = 0;
        while(a.charAt(i)=='0'){
            i++;
            if(i==a.length())
                break;
        }
        return a.substring(i);
    }
    /**
     *
     * @param s
     * @return the 9's compliment of string s
     */
    private static String comp(String s) {
        int i=0;
        char c;
        int d;
        char[] str = s.toCharArray();
        
        while(i<s.length()) {            
            c = s.charAt(i);
            d = c - '0';
            c = (char)(9 - d + '0');
            str[i] = c;
            i++;            
        }
        
        s = String.valueOf(str);
        return s;
    }
    
    private static void comp(BigNum a) {
        int i = 9;
        
        while(i<=a.array[0]) {
            i = (i<<3) + (i<<1);
        }
        
        a.array[0] = i - a.array[0];
        i = 1;
        
        if(a.array.length > 1) {
            while(i<a.array.length) {
                a.array[i] = 999999999 - a.array[i];
                i++;
            }
        }        
    }
    
    public String toString() {
        return str;
    }
    
}
