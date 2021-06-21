package nl.hsleiden.inf2b.groep4.cost_card;


import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name="cost_card")
@NamedQueries(value = {
        @NamedQuery(name = "nl.hsleiden.inf2b.groep4.cost_card.findAll",
        query = "SELECT c FROM CostCard c")
})
public class CostCard implements Serializable {

//  _________________________________
//    Variables for hibernate
//  _________________________________
    @Id
    @Column(name = "cost_card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int costCardId;

//	@JoinColumn(name = "puzzle_id")
//	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.MERGE)
//	private Puzzle puzzle;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 5000")
	private int energy = 5000;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 100")
	private int cost_import_zwoog = 100;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 100")
    private int cost_import_kleuroog = 100;

	@Min(5)
	@Max(5000)
	@Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 100")
    private int cost_import_specialeAanval = 100;

	@Min(5)
	@Max(5000)
	@Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
	private int cost_run_zwoog = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
	private int cost_run_kleuroog = 10;

	@Min(5)
	@Max(5000)
	@Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 100")
	private int cot_run_specialeAanval = 100;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 45")
	private int cost_run_comparison = 45;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
	private int cost_run_operation = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 20")
	private int cost_run_assign = 20;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
	private int cost_run_stapVooruit = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
    private int cost_run_stapAchteruit = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
    private int cost_run_draai = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
    private int cost_run_vliegOmhoog = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
	private int cost_run_vliegOmlaag = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
	private int cost_run_vliegVooruit = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
	private int cost_run_jump = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 100")
	private int cost_punishment_botsen = 100;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 25")
	private int cost_punishment_vallen = 25;

	@Min(5)
	@Max(5000)
	@Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 30")
	private int cost_instruction = 30;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
	private int cost_if = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 20")
	private int cost_while = 20;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 10")
	private int cost_var = 10;

	@Min(5)
	@Max(5000)
    @Column(nullable = false, columnDefinition = "INT NOT NULL DEFAULT 20")
	private int cost_assign = 20;
    

//	_________________________________
//	Construtor
//	_________________________________

    public CostCard() {

    }

    public CostCard(int energy,
                    int cost_import_zwoog,
                    int cost_import_kleuroog,
                    int cost_import_specialeAanval,
                    int cost_run_zwoog,
                    int cost_run_kleuroog,
                    int cot_run_specialeAanval,
                    int cost_run_comparison,
                    int cost_run_operation,
                    int cost_run_assign,
                    int cost_run_stapVooruit,
                    int cost_run_stapAchteruit,
                    int cost_run_draai,
                    int cost_run_vliegOmhoog,
                    int cost_run_vliegOmlaag,
                    int cost_run_vliegVooruit,
                    int cost_run_jump,
                    int cost_punishment_botsen,
                    int cost_punishment_vallen,
                    int cost_if,
                    int cost_while,
                    int cost_var,
                    int cost_assign) {

        this.energy = energy;
        this.cost_import_zwoog = cost_import_zwoog;
        this.cost_import_kleuroog = cost_import_kleuroog;
        this.cost_import_specialeAanval = cost_import_specialeAanval;
        this.cost_run_zwoog = cost_run_zwoog;
        this.cost_run_kleuroog = cost_run_kleuroog;
        this.cot_run_specialeAanval = cot_run_specialeAanval;
        this.cost_run_comparison = cost_run_comparison;
        this.cost_run_operation = cost_run_operation;
        this.cost_run_assign = cost_run_assign;
        this.cost_run_stapVooruit = cost_run_stapVooruit;
        this.cost_run_stapAchteruit = cost_run_stapAchteruit;
        this.cost_run_draai = cost_run_draai;
        this.cost_run_vliegOmhoog = cost_run_vliegOmhoog;
        this.cost_run_vliegOmlaag = cost_run_vliegOmlaag;
        this.cost_run_vliegVooruit = cost_run_vliegVooruit;
        this.cost_run_jump = cost_run_jump;
        this.cost_punishment_botsen = cost_punishment_botsen;
        this.cost_punishment_vallen = cost_punishment_vallen;
        this.cost_if = cost_if;
        this.cost_while = cost_while;
        this.cost_var = cost_var;
        this.cost_assign = cost_assign;
    }

//    _________________________________
//    Getters and setters
//    _________________________________

	public void substractEnergy(int amt){
		energy -= amt;
	}

	public void addEnergy(int amt) {
		energy += amt;
	}

	public int getCost_if() {
		return cost_if;
	}

	public int getCost_while() {
		return cost_while;
	}

	public int getCost_var() {
		return cost_var;
	}

	public int getCost_assign() {
		return cost_assign;
	}

	public int getCost_import_zwoog() {
		return cost_import_zwoog;
	}

	public int getCost_import_kleuroog() {
		return cost_import_kleuroog;
	}

	public void setCost_import_kleuroog(int cost_import_kleuroog) {
		this.cost_import_kleuroog = cost_import_kleuroog;
	}

	public int getCost_import_specialeAanval() {
		return cost_import_specialeAanval;
	}

	public void setCost_import_specialeAanval(int cost_import_specialeAanval) {
		this.cost_import_specialeAanval = cost_import_specialeAanval;
	}

	public int getCost_run_comparison() {
		return cost_run_comparison;
	}

	public int getCost_run_operation() {
		return cost_run_operation;
	}

	public int getCot_run_specialeAanval() {
		return cot_run_specialeAanval;
	}

	public int getCost_run_kleuroog() {
		return cost_run_kleuroog;
	}

	public int getCost_run_zwoog() {
		return cost_run_zwoog;
	}

	public int getEnergy() {
		return energy;
	}

	public int getCost_run_assign() {
		return cost_run_assign;
	}

	public int getCost_run_stapVooruit() {
		return cost_run_stapVooruit;
	}

	public void setCost_run_stapVooruit(int cost_run_stapVooruit) {
		this.cost_run_stapVooruit = cost_run_stapVooruit;
	}

	public int getCost_run_stapAchteruit() {
		return cost_run_stapAchteruit;
	}

	public void setCost_run_stapAchteruit(int cost_run_stapAchteruit) {
		this.cost_run_stapAchteruit = cost_run_stapAchteruit;
	}

	public int getCost_run_draai() {
		return cost_run_draai;
	}

	public void setCost_run_draai(int cost_run_draai) {
		this.cost_run_draai = cost_run_draai;
	}

	public int getCost_run_vliegOmhoog() {
		return cost_run_vliegOmhoog;
	}

	public void setCost_run_vliegOmhoog(int cost_run_vliegOmhoog) {
		this.cost_run_vliegOmhoog = cost_run_vliegOmhoog;
	}

	public int getCost_run_vliegOmlaag() {
		return cost_run_vliegOmlaag;
	}

	public void setCost_run_vliegOmlaag(int cost_run_vliegOmlaag) {
		this.cost_run_vliegOmlaag = cost_run_vliegOmlaag;
	}

	public int getCost_run_vliegVooruit() {
		return cost_run_vliegVooruit;
	}

	public void setCost_run_vliegVooruit(int cost_run_vliegVooruit) {
		this.cost_run_vliegVooruit = cost_run_vliegVooruit;
	}


	public int getCost_punishment_botsen() {
		return cost_punishment_botsen;
	}

	public void setCost_punishment_botsen(int cost_punishment_botsen) {
		this.cost_punishment_botsen = cost_punishment_botsen;
	}

	public int getCost_punishment_vallen() {
		return cost_punishment_vallen;
	}

	public int getCost_run_jump() {
		return cost_run_jump;
	}

	public int getCost_instruction() {
		return cost_instruction;
	}

	public void setCost_instruction(int cost_instruction) {
		this.cost_instruction = cost_instruction;
	}

}
