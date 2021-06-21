package nl.hsleiden.inf2b.groep4.environmentStatus;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.AbstractDAO;
import nl.hsleiden.inf2b.groep4.persistance.ConnectionPool;
import org.hibernate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class StatusDAO extends AbstractDAO<StatusModel> {

	private SessionFactory sessionFactory;
	private ConnectionPool pool;
	private static StatusModel status = new StatusModel(1, "closed");
	private static StatusDAO statusDAO;

	@Inject
	public StatusDAO(SessionFactory sessionFactory, ConnectionPool connectionPool) {
		super(sessionFactory);
		statusDAO = this;
		this.sessionFactory = sessionFactory;
		this.pool = connectionPool;
		this.setEnum();
		this.addStatus(status.getStatus());
	}

	private void addStatus(String stat) {
		Connection conn = this.pool.checkout();
		ResultSet set = null;
		try {
			PreparedStatement checkIfEmpty = conn.prepareStatement("SELECT * FROM status");
			PreparedStatement statement = conn.prepareStatement("INSERT INTO status (status_id, status) VALUES (?,?)");
			statement.setInt(1,1);
			statement.setString(2, "closed");

			set = checkIfEmpty.executeQuery();
			if (set == null || set.next() == false) {
			statement.execute();
			} else if (!set.getString("status").equals(status.getStatus())) {
				status.setStatus(set.getString("status"));
			}

		} catch (SQLException SQLE) {
			SQLE.printStackTrace();
		} finally {
			pool.checkIn(conn);
		}
	}


	public void setStatus(StatusModel status) {

		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			StatusModel st = (StatusModel)session.get(StatusModel.class, this.status.getStatusId());
			st.setStatus(status.getStatus());
			StatusDAO.status = st;
			session.update(st);
			transaction.commit();
		} catch (HibernateException hbe) {
			if(transaction != null) transaction.rollback();
			hbe.printStackTrace();
		} finally {
			session.close();
		}
	}

	protected StatusModel getStatus() {
		List<StatusModel> model = list(namedQuery("nl.hsleiden.inf2b.groep4.environmentStatus.StatusModel.findAll"));
		StatusModel status = new StatusModel(model.get(0).getStatusId(), model.get(0).getStatus());
		this.status = status;
		return status;
	}

	private void setEnum(){
		Connection conn = pool.checkout();

		try{
			PreparedStatement statementDrop = conn.prepareStatement("DROP TYPE IF EXISTS environment_status");
			statementDrop.execute();
			PreparedStatement statement = conn.prepareStatement("CREATE TYPE environment_status AS ENUM ('closed','sandbox','production')");
			statement.execute();
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		} finally {
			pool.checkIn(conn);
		}
	}


	protected String statusChecker() {
		Connection conn = pool.checkout();
		String newStatus = "closed";
		ResultSet set = null;
		try {
			PreparedStatement check = conn.prepareStatement("SELECT * FROM status");
			set = check.executeQuery();
			if (set.next() != false && !set.getString("status").equals(status.getStatus())){
				newStatus = set.getString("status");
			} else {
				newStatus = status.getStatus();
			}
		} catch (SQLException SQLE) {
			SQLE.printStackTrace();
		} finally {
			pool.checkIn(conn);
		}
		return newStatus;
 	}

	public static StatusDAO getStatusDAO() {
		return statusDAO;
	}

	public static StatusModel getStatusModel() {
		return status;
	}
}
