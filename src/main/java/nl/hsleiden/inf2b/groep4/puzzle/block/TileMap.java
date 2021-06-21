package nl.hsleiden.inf2b.groep4.puzzle.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.hsleiden.inf2b.groep4.puzzle.Tile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tilemap")
public class TileMap implements Serializable {

	@JsonProperty
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;


	@OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "keypair_id")
	List<TileKeyPair> values;


	public TileMap(List<TileKeyPair> values) {
		this.values = values;
	}

	public TileMap() {
		values = new ArrayList<>();
	}

	public List<TileKeyPair> getValues() {
		return values;
	}

	public void setValues(List<TileKeyPair> values) {
		this.values = values;
	}

	public int getId() {
		return id;
	}

	public int getValueByKey(String key){
		for (TileKeyPair tileKeyPair:values) {
			if(tileKeyPair.name.equals(key)){
				return tileKeyPair.getValue();
			}
		}
		return -1;
	}

	public void updateValue(String key, int value){
		for (TileKeyPair tileKeyPair:values) {
			if(tileKeyPair.name.equals(key)){
				tileKeyPair.value = value;
			}
		}
	}
	public void setId(int id) {
		this.id = id;
	}

	public void add(String name, int value){
		values.add(new TileKeyPair(name, value));
	}
	public int size(){
		if(values == null){
			return 0;
		}
		return values.size();
	}
}
