package com.create;

import java.util.Vector;

public class test {
	public static void main(String[] args) {
		
		String a = "a";
		String b = "b";
		String c = "c";
		
		Vector vec = new Vector();
		vec.insertElementAt(a,vec.size());
		
		for (int i = 0; i < vec.size(); i++) {
			System.out.println(vec.get(i));
		}
		System.out.println("=====================");
		vec.insertElementAt(b,vec.size());		
		for (int i = 0; i < vec.size(); i++) {
			System.out.println(vec.get(i));
		}
		
		
		
	}
}
