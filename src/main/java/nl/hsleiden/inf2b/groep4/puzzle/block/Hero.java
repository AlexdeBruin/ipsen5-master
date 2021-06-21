package nl.hsleiden.inf2b.groep4.puzzle.block;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="hero")
@NamedQueries(value = {
@NamedQuery(name = "nl.hsleiden.inf2b.groep4.puzzle.block.Hero.findAll",
		query = "Select c from Hero c"),
		})
public class Hero implements Serializable {

	@JsonProperty
	@Id
	@Column(name = "id")
	private int id;


	@JsonProperty
	@Column(name = "hero_name")
	private String heroName;

	@JsonProperty
	@Column(name = "hero_code_name")
	private String codeHeroName;

	@JsonProperty
	@Column(name = "image_src")
	private String imageSrc;

	@JsonProperty
	@Column(name = "methods")
	private String methods;

	@JsonProperty
	@Column(name = "special_move")
	private String specialMove;


	public Hero(int id, String heroName, String codeHeroName, String imageSrc, String methods, String specialMove) {
		this.id = id;
		this.heroName = heroName;
		this.codeHeroName = codeHeroName;
		this.imageSrc = imageSrc;
		this.methods = methods;
		this.specialMove = specialMove;
	}

	public Hero(){

	}

	public String getCodeHeroName() {
		return codeHeroName;
	}

	public void setCodeHeroName(String codeHeroName) {
		this.codeHeroName = codeHeroName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeroName() {
		return heroName;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getMethods() {
		return methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public String getSpecialMove() {
		return specialMove;
	}

	public void setSpecialMove(String specialMove) {
		this.specialMove = specialMove;
	}
}
