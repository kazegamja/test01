package com.kazegamja.www.test;

public class Test {
	
	
	// ���˹� �μ� ��ü
	public void print(String item, String condition) {
		
		if ("SALE".equals(condition)) {
			System.out.println(item + "�� �����մϴ�.");
			System.out.println("");	
		} else if ("BIGDEAL".equals(condition)) {
			System.out.println(item + "�� ��� �����մϴ�.");
			System.out.println("");	
		} else {
			System.out.println(item + "�� �Ǹ��մϴ�.");
			System.out.println("");	
		}
	}
	
	
	// main ��  => ���๮
	public static void main(String[] args) {
		
		
		Test t = new Test();
		t.print("����", "SALE");
		
		t.print("����", "BIGDEAL");
		
		t.print("����", "");
		
		
	}

}
