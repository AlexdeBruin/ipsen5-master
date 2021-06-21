package nl.hsleiden.inf2b.groep4.puzzle.block;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="backgroundblock")
@NamedQueries(value = {
@NamedQuery(name = "nl.hsleiden.inf2b.groep4.puzzle.block.BackgroundBlock.findAll",
		query = "Select b from BackgroundBlock b"),
		})
public class BackgroundBlock  implements Serializable {

	@JsonProperty
	@Column(name = "image_src")
	private String imageSrc;

	@JsonProperty
	@Id
	@Column(name = "id")
	private int id;

	@JsonProperty
	@Column(name = "value")
	private int value;


	public BackgroundBlock(int id, String imageSrc, int value) {
		this.id = id;
		this.imageSrc = imageSrc;
		this.value = value;
	}

	public BackgroundBlock(){

	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
