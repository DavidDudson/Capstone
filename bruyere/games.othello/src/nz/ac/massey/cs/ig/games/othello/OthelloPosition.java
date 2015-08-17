package nz.ac.massey.cs.ig.games.othello;

/**
 * Position on an {@link OthelloBoard}
 * 
 * @author Johannes Tandler
 *
 */
public class OthelloPosition {

	private int x = -1;
	private int y = -1;

	public OthelloPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	/**
	 * get top left field of this position
	 * 
	 * @return
	 */
	public OthelloPosition getTopLeft() {
		return new OthelloPosition(x - 1, y - 1);
	}

	/**
	 * get top field of this position
	 * 
	 * @return
	 */
	public OthelloPosition getTop() {
		return new OthelloPosition(x, y - 1);
	}

	/**
	 * get top right field of this position
	 * 
	 * @return
	 */
	public OthelloPosition getTopRight() {
		return new OthelloPosition(x + 1, y - 1);
	}

	/**
	 * get right field of this position
	 * 
	 * @return
	 */
	public OthelloPosition getRight() {
		return new OthelloPosition(x + 1, y);
	}

	/**
	 * get bottom right field of this position
	 * 
	 * @return
	 */
	public OthelloPosition getBottomRight() {
		return new OthelloPosition(x + 1, y + 1);
	}

	/**
	 * get bottom field of this position
	 * 
	 * @return
	 */
	public OthelloPosition getBottom() {
		return new OthelloPosition(x, y + 1);
	}

	/**
	 * get bottom left field of this position
	 * 
	 * @return
	 */
	public OthelloPosition getBottomLeft() {
		return new OthelloPosition(x - 1, y + 1);
	}

	/**
	 * get left field of this position
	 * 
	 * @return
	 */
	public OthelloPosition getLeft() {
		return new OthelloPosition(x - 1, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OthelloPosition other = (OthelloPosition) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Move (y=" + y + ", x=" + x + ")";
	}

}
