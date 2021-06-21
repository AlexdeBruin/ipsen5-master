package nl.hsleiden.inf2b.groep4.puzzle;


import nl.hsleiden.inf2b.groep4.solution.Solution;

import javax.persistence.*;

@Entity
@Table(name = "coord")
public class Coord {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int coordid;

	@Column
	private int x;

	@Column
	private int y;


	public Coord(){

	}

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getCoordid() {
		return coordid;
	}

	public void setCoordid(int coordid) {
		this.coordid = coordid;
	}
}
