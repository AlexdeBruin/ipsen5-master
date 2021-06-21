package nl.hsleiden.inf2b.groep4.puzzle.block;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tilekeypair")
public class TileKeyPair implements Serializable {

	@JsonProperty
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;


	@Column(name = "name")
	String name;

	@Column(name = "value")
	int value;


	public TileKeyPair(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public TileKeyPair() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
