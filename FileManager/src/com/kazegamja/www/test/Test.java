package com.kazegamja.www.test;

public class Test {
	
	
	// 판촉물 인쇄 업체
	public void print(String item, String condition) {
		
		if ("SALE".equals(condition)) {
			System.out.println(item + "을 세일합니다.");
			System.out.println("");	
		} else if ("BIGDEAL".equals(condition)) {
			System.out.println(item + "을 대박 세일합니다.");
			System.out.println("");	
		} else {
			System.out.println(item + "을 판매합니다.");
			System.out.println("");	
		}
	}
	
	
	// main 문  => 실행문
	public static void main(String[] args) {
		
		
		Test t = new Test();
		t.print("딸기", "SALE");
		
		t.print("포도", "BIGDEAL");
		
		t.print("대추", "");
		
		
	}

}
