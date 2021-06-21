package nl.hsleiden.inf2b.groep4.interpreter;

import nl.hsleiden.inf2b.groep4.interpreter.hero.Hero;
import nl.hsleiden.inf2b.groep4.interpreter.hero.HeroFactory;
import nl.hsleiden.inf2b.groep4.puzzle.Tile;
import nl.hsleiden.inf2b.groep4.cost_card.CostCard;
import nl.hsleiden.inf2b.groep4.solution.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {

	private Solution solution;
	private Hero hero;
	private Tile endTile;
	private TileActions tileActions;

	//The solution code
	private String[] code;

	//the current reading line of the solution code
	private int codeline = -1;

	//the current word position of the current codeline
	private int linepos = 0;

	private String logLines = "";

	private HashMap<String, Integer> variables = new HashMap();
	private ArrayList<Integer> keys = new ArrayList<>();
	//imports
	private boolean zwOogImported = false;
	private boolean kleurOogImported = false;
	private boolean specialeAanvalImported = false;

	public Interpreter(Solution solution) {

		log("Starten van lezen van code...");

		this.solution = solution;
		tileActions = new TileActions(this);
		String tempCode = solution.getCode();

		//Replace all double or more spaces with a single space, remove tabs and upper cases
		tempCode = tempCode.trim().replaceAll(" +", " ");
		tempCode = tempCode.trim().replaceAll("\t", "");
		tempCode = tempCode.toLowerCase();

		this.code = tempCode.split("\n");
	}


	/**
	 * Runs through the entire code
	 */
	public Solution start() {
		//if something goes wrong like "out of energy or out of lifes" an exception is thrown, catched here.
		try {
			if (this.code == null){
				end("geen code gevonden");
			}

			endTile = solution.getPuzzle().findVillianTile();
			if(endTile == null){
				end("Geen einde gevonden in de puzzel");
			}

			handleInitialCosts();
			nextSentence();
		} catch (Exception e) {
			if(!(e instanceof InterpreterException) ){
				//these errors should not happen, these are thrown by Java and are not on purpose
				e.printStackTrace();
			}
			boolean succes = false;

			if(hero != null) {
				succes = isHeroAtEndPosition();
				solution.setSucces(succes);
				solution.setPositions(hero.getHeroPositions());
				if(succes) {
					solution.setScore(solution.getPuzzle().getCostCard().getEnergy());
					log("===* Einde gevonden! puzzel succesvol doorlopen *===");
				} else {
					solution.setScore(0);
					log("===* De hero is helaas niet op het einde gekomen, de puzzel is dus niet succesvol voltooid *===");

				}
			} else {
				log("Er was geen hero geselecteerd dus het einde is niet gevonden!");
			}

			logEnergyLeft();

			solution.setConsoleOutput(logLines);

			return solution;
		}
		return solution;
	}


	private void handleInitialCosts() throws Exception {
		substractInitialCost("if ", findMatches("if "), getCostCard().getCost_if());
		substractInitialCost("var ", findMatches("var "), getCostCard().getCost_var());
		substractInitialCost("while ", findMatches("while "), getCostCard().getCost_while());
		substractInitialCost("toekenning", findMatches("[ %+-/*]= "), getCostCard().getCost_assign());
		String[] opdrachten = {"stapvooruit","stapachteruit","draai","vliegomhoog", "vliegvooruit", "vliegomlaag", "specialekracht", "spring"};
		for (String opdracht:opdrachten) {
			substractInitialCost("opdracht: " + opdracht, findMatches(opdracht), getCostCard().getCost_instruction());
		}
	}


	private void substractInitialCost(String type, int amount, int costForEach) throws Exception {
		if(amount > 0) {
		if(getCostCard().getEnergy() < costForEach * amount) {
			end("niet genoeg energie over");
			return;
		}

		getCostCard().substractEnergy(amount * costForEach);
		log("Kosten voor het gebruik van " + type + ": " + amount + " * " + costForEach + " (" + amount * costForEach + ").");
		logEnergyLeft();
		}
	}

	/**
	 * Substracts the cost of a run action
	 * @param action the cause of the substraction like " operation "
	 * @param cost how much the operation costs
	 * @throws Exception if not enough energy left
	 */
	public void substractRunCost(String action, int cost) throws Exception {
		log("Kosten voor " + action + ": " + cost);
		if(getCostCard().getEnergy() < cost) {
			end("niet genoeg energie over");
			return;
		}
		getCostCard().substractEnergy(cost);
		//logEnergyLeft();
	}


	private int findMatches(String find){
		Pattern p = Pattern.compile(find);
		Matcher m = p.matcher(solution.getCode());
		int count = 0;
		while (m.find())
			count++;
		return count;
	}

	/**
	 * Parses to the next sentence in the code
	 */
	private void nextSentence() throws Exception {

		if (code.length == codeline + 1){
			end("Einde van de code bereikt");
			//TODO add check if hero is at the finish
		}

		codeline++;
		linepos = 0;

		//check if the line is empty
		if(isEndofLine() || getLine()[0].equals("")){
			nextSentence();
			return;
		}

		readLine();
	}

	/**
	 * parses the first word of the next line
	 */
	private void readLine() throws Exception {

		String woord = nextWord();
		switch (woord){

			case "use":
				setHero();
				break;
			case "import":
				executeImportStatement();
				break;
			case "if":
				executeIfStatement();
				break;
			case "while":
				executeWhileStatement();
				break;
			case "stapvooruit":
			case "stapachteruit":
			case "draai":
			case "vliegomhoog":
			case "vliegvooruit":
			case "vliegomlaag":
			case "specialekracht":
			case "spring":
				doHeroAction(woord);
				nextSentence();
				break;
			case "}":
				break; //do nothing so it "ends" the current block
			default:
				updateVariable(woord);
				break;

		}
	}
	private void setHero() throws Exception{

		if(hero != null){
			end("er is al een hero geselecteerd.");
		}

		HeroFactory heroFactory = new HeroFactory();
		String heroName = nextWord();

		if(!isValidHero(heroName)){
			end("je mag de geselecteerde hero niet gebruiken");
		}
		hero = heroFactory.getHeroObject(heroName);

		if(hero == null){
			end("hero " + heroName + " bestaat niet");
		}
		hero.setInterpreter(this);

		Tile heroTile = solution.getPuzzle().findHeroTile();
		if(heroTile == null){
			end("Geen starttile aanwezig");
		}
		hero.setPosition(heroTile.getTile_x(), heroTile.getTile_y());
		nextSentence();
	}

	private boolean isValidHero(String heroName){
		//This is NOT the interpreter hero object but the one used in the frontend/database
		for (nl.hsleiden.inf2b.groep4.puzzle.block.Hero hero:solution.getPuzzle().getHeroes()) {
			if(heroName.equals(hero.getCodeHeroName())){
				this.solution.setHero(hero);
				return true;
			}
		}
		return false;
	}
	private void doHeroAction(String action) throws Exception {
		if(hero == null){
			end("Geen hero geactiveerd, zorg dat er bovenin je code 'use HERONAME' staat " );
		}
		hero.doAction(action);
	}

	private void executeImportStatement() throws Exception {
		String toImport = nextWord();

		switch (toImport){
			case "zwoog":
				if(!zwOogImported){
					zwOogImported = true;
					substractRunCost("Importeren van zwOog", getCostCard().getCost_import_zwoog());
				} else {
					end("zwOog is al imported");
				}
				break;
			case "kleuroog":
				if(!kleurOogImported) {
					kleurOogImported = true;
					substractRunCost("Importeren van kleurOog", getCostCard().getCost_import_kleuroog());
				} else {
					end("kleurOog is al imported");
				}
				break;
			case "specialeAanval":
				if(!specialeAanvalImported) {
					specialeAanvalImported = true;
					substractRunCost("Importeren van specialeAanval", getCostCard().getCost_import_specialeAanval());
				} else {
					end("specialeAanval is al imported");
				}
				break;
			default:
				end("Onbekende import");
				return;
		}
		nextSentence();
	}


	private void executeIfStatement() throws Exception {

		boolean expressionResult = calculateStatementTrue();

		if(expressionResult){
			doBlock();
			String nextWord = nextWord();
			if (nextWord != null && nextWord.equals("else")) {
				codeline = getEndOfBlokLine();
			}
			nextSentence();

		} else {
			codeline = getEndOfBlokLine(); //skip the "if" block
			linepos = 1; // we need to check for an else so skip the }
			String nextWord = nextWord();

			if (nextWord != null && nextWord.equals("else")){
				doBlock();
			}
			nextSentence();
		}
	}

	private void executeWhileStatement() throws Exception {

		boolean expressionResult = calculateStatementTrue();

		int codelineBackTo = codeline;

		while (expressionResult) {
			doBlock();
			codeline = codelineBackTo;
			linepos = 1;
			expressionResult = calculateStatementTrue();
		}

		codeline = getEndOfBlokLine();
		nextSentence();
	}

	public boolean calculateStatementTrue() throws Exception{
		int firstValue = calculateExpressionValue(getValue(nextWord()));
		String compareWith = nextWord();
		int secondValue = calculateExpressionValue(getValue(nextWord()));

		return  checkEqual(firstValue, compareWith, secondValue);
	}
	/**
	 * Runs a while/if block from { to }
	 * @throws Exception
	 */
	private void doBlock() throws Exception {
		if(!nextWord().equals("{")){
			end("Verwacht een {");
			return;
		}
		nextSentence();

	}

	private void createVariable(String variableName) throws Exception {

		Pattern p = Pattern.compile("[\\W]");
		Matcher m = p.matcher(variableName);

		if(m.find()){
			end("variabele " + variableName + " is not allowed because it contains a special character");
			return;
		}

		String[] notAllowedNames= {"if", "while", "var", "kleuroog", "zwoog", "import", "use",
		"stapvooruit", "stapachteruit", "draai", "vliegomhoog", "vliegvooruit", "vliegomlaag",
		"specialekracht", "spring"};

		for (String notAllowedName : notAllowedNames) {
			if(variableName.equals(notAllowedName)){
				end("variabele " + variableName + " is not allowed because it contains the keyword " + notAllowedName);
				return;
			}
		}

		String operator = nextWord();
		if (!operator.equals("=")){
			end("expected '='");
		}

		int value =	calculateExpressionValue(getValue(nextWord()));
		updateVariableValue(variableName, value);
	}


	private void updateVariable(String woord) throws Exception {

		if(!isVariableDeclared(woord)) {
			createVariable(woord);
			nextSentence();
			return;
		}

		String operator = nextWord();
		int value = calculateExpressionValue(getValue(nextWord()));

		switch (operator) {
			case "=":
				updateVariableValue(woord, value);
				break;
			case "+=":
				updateVariableValue(woord, variables.get(woord) + value );
				break;
			case "-=":
				updateVariableValue(woord, variables.get(woord) - value );
				break;
			case "*=":
				updateVariableValue(woord, variables.get(woord) * value );
				break;
			case "/=":
				updateVariableValue(woord, variables.get(woord) / value );
				break;
			case "%=":
				updateVariableValue(woord, variables.get(woord) % value );
				break;
		}

		nextSentence();
	}

	private boolean checkEqual(int value1, String compare, int value2) throws Exception {

			boolean result;

			switch(compare){
				case "==":
					result = value1 == value2;
					break;
				case ">=":
				case "=>":
					result = value1 >= value2;
					break;
				case "=<":
				case "<=":
					result = value1 <= value2;
					break;
				case ">":
					result = value1 > value2;
					break;
				case "<":
					result = value1 < value2;
					break;
				case "!=":
					result = value1 != value2;
					break;
				default:
					end("Ongeldige vergelijking");
					return false;
			}
			substractRunCost("vergelijken", getCostCard().getCost_run_comparison());
			return result;
		}

	/**
	 * Calculated the entire value of x amount of expressions ex: 1 + 2 / 2 * 2
	 * @param initialValue
	 * @return the value
	 * @throws Exception
	 */
	private int calculateExpressionValue(int initialValue) throws Exception {
		while(!isEndofLine()) {
			String whileOperator = nextWord();
			if (whileOperator.matches("[-+*/%]")) {
				int whileValue = getValue(nextWord());
				initialValue = doOperation(initialValue, whileOperator, whileValue);
			} else {
				linepos -= 1;
				return initialValue;
			}
		}
		return initialValue;
	}

	private void updateVariableValue(String variableName, int value) throws Exception {
		if (value > 200000){
	 		end("Te grote waarde");
		}
		if (value < -200000){
			end("Te kleine waarde");
		}
		variables.put(variableName, value);
		substractRunCost("toekenning", getCostCard().getCost_assign());
	}

	private int doOperation(int value1, String operator, int value2) throws Exception {

		int finalValue;
		switch (operator) {
			case "+":
				finalValue = value1 + value2;
				break;
			case "-":
				finalValue =  value1 - value2;
				break;
			case "*":
				finalValue = value1 * value2;
				break;
			case "/":
				finalValue = value1 / value2;
				break;
			case "%":
				finalValue = value1 % value2;
				break;
			default:
				end("ongeldige operatie");
				return 0;
		}
		substractRunCost("operatie", getCostCard().getCost_run_operation());
		return finalValue;

	}

	private boolean isVariableDeclared(String woord) {
		return variables.containsKey(woord);
	}

	/**
	 * Returns the next word in the codeline and moves the linepos by 1
	 * @return
	 */
	private String nextWord() {
		String[] line = getLine();

		if (linepos >= line.length) {
			return null;
		}
		String word = line[linepos];
		linepos++;
		return word;
	}

	/**
	 * checks if a word is a variable then returns the value of that variable
	 * otherwise it just returns the value
	 * @param woord
	 * @return value
	 */
	private int getValue(String woord) throws Exception {

		switch(woord){
			case "kleuroog":
				return getColorEye();
			case "zwoog":
				return getBlackWhiteEye();
		}

		if(variables.containsKey(woord)){
			return variables.get(woord);
		}
		try {
			int value = Integer.parseInt(woord);
			return value;
		} catch(NumberFormatException e){
			end(woord + " is geen getal");
		}
		return 0;
	}


	private int getColorEye() throws Exception {
		if(kleurOogImported) {
			substractRunCost("gebruik van kleuroog", getCostCard().getCost_run_kleuroog());
			int value = getTileByPosition(hero.getPosX(), hero.getPosY()).getBackgroundValue();
			log("kleuroog gaf waarde " + value);
			return value;

		}
		end("kleuroog is niet geimporteerd");
		return 0;
	}

	private int getBlackWhiteEye() throws Exception {
		if(zwOogImported){
			substractRunCost("gebruik van zwoog", getCostCard().getCost_run_zwoog());
			int value = Math.min(1, getTileByPosition(hero.getPosX(), hero.getPosY()).getBackgroundValue());
			//everything greater than 1 will return 1, because it doesn't see the difference between any non black color
			log("zwoog gaf waarde " + value);
			return value;
		}
		end("zwoog is niet geimporteerd");
		return 0;
	}

	private String[] getLine(){
		return code[codeline].split(" ");
	}

	public void log(String log){
		this.logLines += log + "\n";
	}

	/**
	 * Returns true if the the current linepos is at the end of the line
	 * @return
	 */
	private boolean isEndofLine(){
		int lineLength = getLine().length;
		return linepos >= lineLength;
	}

	/**
	 * calculates where a block (if/while loop) ends
	 * @return the codeline of the }
	 */
	private int getEndOfBlokLine() {
		int localCodeLine = codeline;
		int stop = 1;

		while (stop > 0 && localCodeLine < code.length - 1) {
			localCodeLine++;

			String line = code[localCodeLine];
			if(line.contains("{") && !line.contains("}")){
				stop += 1;
			} else if (line.contains("}") && !line.contains("{")) {
				stop -= 1;
			} else if (line.contains("{") && line.contains("}") && stop == 1){
				stop -= 1;
			}
		}
		return localCodeLine;

	}

	public void end(String log) throws Exception {
		log("Error on codeline " +  codeline + ": " + log);
		throw new InterpreterException(log);
	}

	private void logEnergyLeft(){
		log("Energie over: " + getCostCard().getEnergy());
	}

	//Used for unit tests
	public HashMap<String, Integer> getVariables() {
		return variables;
	}

	public Solution getSolution() {
		return solution;
	}

	public CostCard getCostCard(){
		return solution.getPuzzle().getCostCard();
	}
	public Tile getTileByPosition(int x, int y){
		return solution.getPuzzle().getTileByPosition(x,y);
	}

	public boolean isHeroAtEndPosition(){
		return endTile.getTile_x() == hero.getPosX() && endTile.getTile_y() == hero.getPosY();

	}

	public TileActions getTileActions() {
		return tileActions;
	}

	public void addKey(int id){
		if (keys.contains(id)){
			log("Duplicate key gevonden");
		}
		keys.add(id);
	}

	public Hero getHero(){
		return hero;
	}

	public ArrayList<Integer> getKeys() {
		return keys;
	}
}
