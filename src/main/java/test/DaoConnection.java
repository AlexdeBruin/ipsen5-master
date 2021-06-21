package test;

import io.dropwizard.testing.junit.DAOTestRule;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.Role;
import nl.hsleiden.inf2b.groep4.account.Score;
import nl.hsleiden.inf2b.groep4.cost_card.CostCard;
import nl.hsleiden.inf2b.groep4.puzzle.Coord;
import nl.hsleiden.inf2b.groep4.puzzle.Tile;
import nl.hsleiden.inf2b.groep4.puzzle.block.*;
import nl.hsleiden.inf2b.groep4.solution.Solution;

public class DaoConnection {

	public final static DAOTestRule DATABASE = DAOTestRule.newBuilder()
			.addEntityClass(Solution.class)
			.addEntityClass(Account.class)
			.addEntityClass(BackgroundBlock.class)
			.addEntityClass(ForgroundBlock.class)
			.addEntityClass(Puzzle.class)
			.addEntityClass(Hero.class)
			.addEntityClass(Coord.class)
			.addEntityClass(Role.class)
			.addEntityClass(Score.class)
			.addEntityClass(CostCard.class)
			.addEntityClass(TileMap.class)
			.addEntityClass(Tile.class)
			.addEntityClass(TileKeyPair.class).build();
}
