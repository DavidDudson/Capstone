package test;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.languages.java.compiler.*;
import test.nz.ac.massey.cs.ig.languages.java.compiler.*; 
import java.util.*;

public class LambdaBot implements Bot<MockGame,MockMove> {
	private String id = null;

	public LambdaBot(String id) {
		super();
		this.id=id;
	}
	
	@Override 
	public MockMove nextMove(MockGame g) {
		Comparator<String> comparator = (i,j) -> i.compareTo(j);
		comparator.compare("a","b");
		return new MockMove();
	}
	
	@Override 
	public String getId() {
		return id;
	}
}