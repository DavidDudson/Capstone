package nz.ac.massey.cs.ig.games.chinesechecker;

public class CCPossibleMove {
	private int x= -1;
	private int y= -1;
	public CCPossibleMove(int x, int y){
		this.x=x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		 CCPossibleMove other = ( CCPossibleMove) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "move at (y=" + y + ", x=" + x + ")" ;
	}
	
}
