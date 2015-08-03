package nz.ac.massey.cs.ig.games.chinesechecker;

public class ChineseCheckerPosition {
	private int pieceX = -1;
	private int pieceY = -1;
	private int destinationX=-1;
	private int destinationY=-1;

	public ChineseCheckerPosition() {
		//DO NOTHING
	}

	public int getPieceY() {
		return pieceY;
	}

	public int getPieceX() {
		return pieceX;
	}
	public void setPiecePosition(int x, int y){
		this.pieceX=x;
		this.pieceY=y;
	}
	public void setDestination(int x, int y){
		this.destinationX=x;
		this.destinationY=y;
	}
	
	public int getDestinationX(){
		return this.destinationX;
	}
	
	public int getDestinationY(){
		return this.destinationY;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pieceX;
		result = prime * result + pieceY;
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
		ChineseCheckerPosition other = (ChineseCheckerPosition) obj;
		if (pieceX != other.pieceX)
			return false;
		if (pieceY != other.pieceY)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Piece at (y=" + pieceY + ", x=" + pieceX + ") and need to move to (y="+destinationY+", x="+destinationX+")" ;
	}
}
