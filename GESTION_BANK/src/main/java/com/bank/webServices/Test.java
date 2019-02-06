package com.bank.webServices;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		zeta();
	}
		
	public void beta() {
		System.out.println("beta calls zeta");
		zeta();
	}
	
	public static void zeta() {
		System.out.println("zeta call beta");
		//beta();
	}
}
