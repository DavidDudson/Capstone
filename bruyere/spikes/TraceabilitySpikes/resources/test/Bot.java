package test;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nz.ac.massey.cs.ig.games.primegame.PGBot;


public class Bot extends PGBot{
	private String hello ="hello";
	private int one =1;
	
	public Bot(String botId){
		super(botId);
	}
	
	@Override
	public Integer nextMove(List<Integer> game) {
		
		List<Integer> list =new ArrayList<>();
		
		Map<Integer,String> map =new HashMap<>();
		list.add(1);
		map.put(1, "hello");
		primitive();
		return 1;
	}
	private void primitive(){
		int i=2;
		String name ="Jack";
		double d=1.002;
		float f=1234;
		short s=1;
		long l=1234;
		char c='c';
		boolean b =true;
	}
	
	public class Person{
		String name="jack";
		int age =3;
	}
}
