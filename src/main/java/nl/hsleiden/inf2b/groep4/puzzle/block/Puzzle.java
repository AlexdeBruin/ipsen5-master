package nl.hsleiden.inf2b.groep4.puzzle.block;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.puzzle.Tile;
import nl.hsleiden.inf2b.groep4.cost_card.CostCard;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import javax.ws.rs.DefaultValue;
import java.util.List;
import java.util.Set;

@Entity(name = "puzzle")
@Table(name="puzzle")
@NamedQueries( value = {
		@NamedQuery(name = "nl.hsleiden.inf2b.groep4.solution.Solution.getPuzzleids",
				query = "Select id from puzzle where is_sandbox = true")
	}
)
public class Puzzle implements Serializable {

	@JoinColumn(name = "puzzle_id")
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Tile> levelGrid;

	@JsonProperty
	@Column (name = "heroes")
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Hero> heroes;

	@JsonProperty
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="cost_card_id")
	@Valid
	private CostCard costCard = new CostCard();

	@Column(name = "is_sandbox")
	@DefaultValue(value = "false")
	private boolean sandbox;
	@JsonIgnore
	@OneToOne (mappedBy="puzzle", fetch = FetchType.LAZY)
	private Account account;


	public Puzzle(){
	}


	public Puzzle(Puzzle puzzle){
		this.levelGrid = puzzle.levelGrid;
		this.heroes = puzzle.heroes;
		this.costCard = puzzle.costCard;
	}

	public Set<Hero> getHeroes() {
		return heroes;
	}

	public void setHeroes(Set<Hero> heroes) {
		this.heroes = heroes;
	}


	public Set<Tile> getLevelGrid() {
		return levelGrid;
	}

	public void setLevelGrid(Set<Tile> levelGrid) {
		this.levelGrid = levelGrid;
	}

	public CostCard getCostCard() {
		return costCard;
	}

	public void setCostCard(CostCard costCard) {
		this.costCard = costCard;
	}

	public Tile getTileByPosition(int x, int y){
		if(y < 0 || y > 19){
			return null;
		}
		if(x >= 25){
			x -= 25;
		}
		if(x < 0){
			x += 25;
		}
		for (Tile tile :levelGrid) {
			if(tile.getTile_x() == x && tile.getTile_y() == y){
				return tile;
			}
		}
		return null;
	}

	public Tile findHeroTile(){
		for (Tile tile:levelGrid) {
			if(tile.getForgroundBlock() != null && tile.getForgroundBlock().getType() == BlockType.HEROBLOCK){
				return tile;
			}
		}
		return null;
	}

	public Tile findVillianTile(){
		for (Tile tile:levelGrid) {
			if(tile.getForgroundBlock() != null && tile.getForgroundBlock().getType() == BlockType.VILLIANBLOCK){
				return tile;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	public boolean isSandbox() {
		return sandbox;
	}

	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}
}
