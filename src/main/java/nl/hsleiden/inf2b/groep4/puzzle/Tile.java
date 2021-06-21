package nl.hsleiden.inf2b.groep4.puzzle;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.hsleiden.inf2b.groep4.puzzle.block.BackgroundBlock;
import nl.hsleiden.inf2b.groep4.puzzle.block.BlockType;
import nl.hsleiden.inf2b.groep4.puzzle.block.ForgroundBlock;
import nl.hsleiden.inf2b.groep4.puzzle.block.TileMap;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tile")
public class Tile implements Serializable {

	@JsonProperty
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonProperty
	@Column(name = "tile_x")
	private int tile_x;

	@JsonProperty
	@Column(name = "tile_y")
	private int tile_y;

	@JoinColumn(name = "forgoundblock_id")
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
	private ForgroundBlock forgroundBlock;

	@JoinColumn(name = "backgroundblock_id")
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
	private BackgroundBlock backgroundBlock;

	@OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "tilemap_id")
	private TileMap tileMap;


	public Tile(int tile_x, int tile_y, ForgroundBlock forgroundImplemented, BackgroundBlock backgroundBlock) {
		this.tile_x = tile_x;
		this.tile_y = tile_y;
		this.forgroundBlock = forgroundImplemented;
		this.backgroundBlock = backgroundBlock;
	}
	public Tile(){

	}

	public int getTile_x() {
		return tile_x;
	}

	public void setTile_x(int tile_x) {
		this.tile_x = tile_x;
	}

	public int getTile_y() {
		return tile_y;
	}

	public void setTile_y(int tile_y) {
		this.tile_y = tile_y;
	}

	public BackgroundBlock getBackgroundBlock() {
		return backgroundBlock;
	}

	public void setBackgroundBlock(BackgroundBlock backgroundBlock) {
		this.backgroundBlock = backgroundBlock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ForgroundBlock getForgroundBlock() {
		return forgroundBlock;
	}

	public void setForgroundBlock(ForgroundBlock forgroundBlock) {
		this.forgroundBlock = forgroundBlock;
	}

	public TileMap getTileMap() {
		return tileMap;
	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public boolean isSolidTile(){
		if(forgroundBlock == null){
			return false;
		}
		 return forgroundBlock.getType() == BlockType.SOLIDBLOCK ;
	}

	public boolean isDoor(){
		if(forgroundBlock == null){
			return false;
		}
		return forgroundBlock.getType() == BlockType.DOOR;
	}

	public boolean isMoveableTile(){
		if(forgroundBlock == null){
			return false;
		}
		return forgroundBlock.getType() == BlockType.MOVEABLEBLOCK;
	}

	public boolean isPortal(){
		if(forgroundBlock == null){
			return false;
		}
		return forgroundBlock.getType() == BlockType.TELEPORT;
	}

	public boolean isSpike(){
		if(forgroundBlock == null){
			return false;
		}
		return forgroundBlock.getType() == BlockType.SPIKE;
	}
	public boolean isBomb(){
		if(forgroundBlock == null){
			return false;
		}
		return forgroundBlock.getType() == BlockType.BOMB;
	}

	public boolean isInstantKill(){
		if(forgroundBlock == null){
			return false;
		}
		return forgroundBlock.getType() == BlockType.INSTAKILLBLOCK;
	}
	public boolean isType(BlockType type){
		if(forgroundBlock == null){
			return false;
		}
		return forgroundBlock.getType() == type;
	}
	public int getBackgroundValue(){
		return backgroundBlock == null ? 1 : backgroundBlock.getValue();
	}

	public boolean destroySolidForground(){

		if(isSolidTile()){
			forgroundBlock = null;
			return true;
		}
		return false;
	}

	/**
	 * This method destroys the forground of the tile, use with care
	 */
	public void destroyAllForground(){
		forgroundBlock = null;
	}
}
