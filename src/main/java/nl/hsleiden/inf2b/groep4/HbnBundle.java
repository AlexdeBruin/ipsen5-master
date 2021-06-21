package nl.hsleiden.inf2b.groep4;

import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import nl.hsleiden.inf2b.groep4.environmentStatus.StatusModel;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.Role;
import nl.hsleiden.inf2b.groep4.account.Score;
import nl.hsleiden.inf2b.groep4.cost_card.CostCard;
import nl.hsleiden.inf2b.groep4.puzzle.Coord;
import nl.hsleiden.inf2b.groep4.puzzle.Tile;
import nl.hsleiden.inf2b.groep4.puzzle.block.*;
import nl.hsleiden.inf2b.groep4.solution.Solution;

import static com.google.common.base.Preconditions.checkNotNull;

public class HbnBundle extends HibernateBundle<IPOSEConfiguration> {

	protected HbnBundle() {
		super(Role.class,
				Score.class,
				Account.class,
				BackgroundBlock.class,
				ForgroundBlock.class,
				Tile.class,
				Puzzle.class,
				Hero.class,
				TileMap.class,
				TileKeyPair.class,
				CostCard.class,
				Solution.class,
				Coord.class,
				StatusModel.class);

	}

	@Override
	public PooledDataSourceFactory getDataSourceFactory(IPOSEConfiguration iposeConfiguration) {
		return iposeConfiguration.getDataSourceFactory();
	}

}
