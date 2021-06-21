package nl.hsleiden.inf2b.groep4.solution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.puzzle.Coord;
import nl.hsleiden.inf2b.groep4.puzzle.block.Hero;
import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="solution")
@NamedQueries( value = {
        @NamedQuery(name = "nl.hsleiden.inf2b.groep4.solution.Solution.getSolutionList",
                query = "Select a from Solution a where puzzle_id = :puzzleid and account_id = :accountid "),
        @NamedQuery(name = "nl.hsleiden.inf2b.groep4.solution.Solution.getDoneList",
                query = "Select a from Solution a where account_id = :accountid and succes = true"),
        @NamedQuery(name = "nl.hsleiden.inf2b.groep4.solution.Solution.getTriedList",
                query = "select a from Solution a where account_id = :accountid and succes = false "),
		@NamedQuery(name = "nl.hsleiden.inf2b.groep4.solution.Solution.GetSolutionsOfPuzzle",
                query = "select a from Solution a where puzzle_id = :puzzleid and account_id <> :accountid and succes = true "),
		@NamedQuery(name = "nl.hsleiden.inf2b.groep4.solution.Solution.getSolutionsMyPuzzle",
                query = "select a from Solution a where puzzle_id = :puzzleid and account_id = :accountid and succes = true "),
		@NamedQuery(name = "nl.hsleiden.inf2b.groep4.solution.Solution.getBestSolutionsOfAccount",
                //query = "select a from Solution a where account_id = :accountid and succes = true GROUP BY account_id, solution_id"),
                query = "select a from Solution a where account_id = :accountid and succes = true"),
		@NamedQuery(name = "nl.hsleiden.inf2b.groep4.solution.Solution.getMakerSolutions",
                query = "select sol from Solution as sol JOIN Account as acc ON sol.puzzle = acc.puzzle WHERE sol.succes = true AND sol.account = acc")
    }
)
public class Solution {

    @Id
    @Column(name = "solution_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int solutionid;

    @Column(name = "code", columnDefinition="TEXT")
    private String code;

    @JsonIgnore
    @JoinColumn(name = "puzzle_id")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Puzzle puzzle;

	@JoinColumn(name = "solution_id")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Coord> positions;

    @Column(name = "succes")
    private boolean succes = false;

    @Column(name = "score")
    private int score = 0;

    @Column(name = "output", columnDefinition="TEXT")
	private String consoleOutput;

    @JsonIgnore
    @JoinColumn(name = "account_id")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Account account;

    @JoinColumn(name = "hero_id")
	@ManyToOne(cascade =  CascadeType.MERGE, fetch = FetchType.EAGER)
	private Hero hero;

    @Column(name = "hash")
    private String hash;

	public Solution(String code, Puzzle puzzle){
		this.code = code;
		this.puzzle = puzzle;
	}
	public Solution(){

	}

	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	public void addPosition(int x, int y){
		this.positions.add(new Coord(x,y));
	}

	public void setPositions(List<Coord> positions) {
		this.positions = positions;
	}

	public List<Coord> getPositions() {
		return positions;
	}

	public boolean isSucces() {
		return succes;
	}

	public void setSucces(boolean succes) {
		this.succes = succes;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

    public int getSolutionid() {
        return solutionid;
    }

    public void setSolutionid(int solutionid) {
        this.solutionid = solutionid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account accountModel) {
        this.account = accountModel;
    }

	public String getConsoleOutput() {
		return consoleOutput;
	}

	public void setConsoleOutput(String consoleOutput) {
		this.consoleOutput = consoleOutput;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
