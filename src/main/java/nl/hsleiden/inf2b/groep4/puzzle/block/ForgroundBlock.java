package nl.hsleiden.inf2b.groep4.puzzle.block;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;

@Entity
@Table(name="forgroundblock")
@NamedQueries(value = {
@NamedQuery(name = "nl.hsleiden.inf2b.groep4.puzzle.block.ForgroundBlock.findAll",
		query = "Select c from ForgroundBlock c"),
		})
public class ForgroundBlock implements Serializable {

	@JsonProperty
	@Column(name = "image_src")
	private String imageSrc;

	@JsonProperty
	@Id
	@Column(name = "id")
	private int id;

	@JsonProperty
	@Column(name = "type")
	private BlockType type;

	@OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "tilemap_id")
	private TileMap tileMap;

	public ForgroundBlock(int id, String imageSrc, BlockType type, TileMap tileMap) {
		this.id = id;
		this.imageSrc = imageSrc;
		this.type = type;
		this.tileMap = tileMap;
	}

	public ForgroundBlock(){

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

	public nl.hsleiden.inf2b.groep4.puzzle.block.TileMap getTileMap() {
		return tileMap;
	}

	public void setTileMap(nl.hsleiden.inf2b.groep4.puzzle.block.TileMap tileMap) {
		tileMap = tileMap;
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
	}
}
