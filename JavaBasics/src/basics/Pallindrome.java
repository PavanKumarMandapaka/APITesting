package basics;

import java.util.Scanner;

public class Pallindrome {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your String");
		String str=sc.next();
		String temp="";
		for(int i=str.length()-1;i>=0;i--) {
			temp=temp+str.charAt(i);
		}
		System.out.println(temp);
		if(str.equalsIgnoreCase(temp)) 
			System.out.println("Given String "+str+" is a Pallindrome");
		else System.out.println("Given String "+str+" is not a Pallindrome");
	}

}
