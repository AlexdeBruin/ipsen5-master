package nl.hsleiden.inf2b.groep4.solution;

import com.google.inject.Singleton;
import io.dropwizard.hibernate.AbstractDAO;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Singleton
public class SolutionDAO extends AbstractDAO<Solution> {

    // TODO: 28-5-2018 get max attemps from config file
    private final int MAXATTEMPTS = 5;

    @Inject
    public SolutionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Solution getSolutionById(int solutionId) {
        return get(solutionId);
    }

    public void saveSolution(Solution solution) {
        Session session = currentSession().getSession();
        session.save(solution);
        //session.getTransaction().commit();
       // session.close();
    }

    public void saveSolutionForMaker(Solution solution){
        Session session = currentSession().getSession();
        session.save(solution);
    }
    public int getAttempts(int puzzleid, int accountid) {
        List<Solution> solutions = getSolutionList(puzzleid, accountid);
        return solutions.size();
    }

    public boolean getDone(int id, int accountid) {
        return getAttempts(id, accountid) < MAXATTEMPTS;
    }

    public Solution getBestSolution(int puzzleid, int accountid) {
        List<Solution> solutions = getSolutionList(puzzleid, accountid);
        Solution max = new Solution();
        max.setScore(0);
        max.setSolutionid(0);
        for(Solution s : solutions) {
            if(s.getScore() > max.getScore()) {
                max = s;
            }
        }
        if(max.getScore() == 0) {
            for(Solution s : solutions) {
                if(s.getSolutionid() > max.getSolutionid()) {
                    max = s;
                }
            }
        }
        currentSession().getSession().clear();
        return max;
    }

    public List<Solution> getSolutionOfMaker(){
		Query query = namedQuery("nl.hsleiden.inf2b.groep4.solution.Solution.getMakerSolutions");
		return (List<Solution>) query.list();
	}

    public List<Solution> getBestSolutionsOfAccount(Account account){
		Query query = namedQuery("nl.hsleiden.inf2b.groep4.solution.Solution.getBestSolutionsOfAccount");
		query.setParameter("accountid", account.getAccountId());
		return (List<Solution>) query.list();
	}

    public List<Solution> getSolutionOfPuzzle(Puzzle puzzle, Account account){
        Query query = namedQuery("nl.hsleiden.inf2b.groep4.solution.Solution.GetSolutionsOfPuzzle");
        query.setParameter("puzzleid", puzzle.getId());
        query.setParameter("accountid", puzzle.getAccount().getAccountId());
        return (List<Solution>) query.list();
    }

    public Solution getSolutionMyPuzzle(Puzzle puzzle, Account account){
		Query query = namedQuery("nl.hsleiden.inf2b.groep4.solution.Solution.getSolutionsMyPuzzle");
		query.setParameter("puzzleid", puzzle.getId());
		query.setParameter("accountid", puzzle.getAccount().getAccountId());
		return (Solution) query.list().get(0);
	}

    private List<Solution> getSolutionList(int puzzleid, int accountid) {
        Query query = namedQuery("nl.hsleiden.inf2b.groep4.solution.Solution.getSolutionList");
        query.setParameter("puzzleid", puzzleid);
        query.setParameter("accountid", accountid);
        List<Solution> list = query.list();
        currentSession().getSession().clear();
        return list;
    }

    public ArrayList<Solution> getDoneList(int accountid) {
        Query query = namedQuery("nl.hsleiden.inf2b.groep4.solution.Solution.getDoneList");
        query.setParameter("accountid", accountid);
        List<Solution> list = query.list();
        ArrayList<Solution> newlist = distinctList(list);
        List<Solution> removeList = new ArrayList<>();
        for(Solution s : list) {
            if(!this.getDone(s.getPuzzle().getId(), s.getAccount().getAccountId())) {
                removeList.add(s);
            }
        }
        newlist.removeAll(removeList);
        currentSession().getSession().clear();
        return newlist;
    }

    public ArrayList<Solution> getdoneNoAttemptsLeft(int accountid) {
        Query query = namedQuery("nl.hsleiden.inf2b.groep4.solution.Solution.getDoneList");
        query.setParameter("accountid", accountid);
        List<Solution> list = query.list();
        ArrayList<Solution> newlist = distinctList(list);
        List<Solution> removeList = new ArrayList<>();
        for(Solution s : list) {
            if(this.getDone(s.getPuzzle().getId(), s.getAccount().getAccountId())) {
                removeList.add(s);
            }
        }
        newlist.removeAll(removeList);
        currentSession().getSession().clear();
        return newlist;
    }

    public ArrayList<Solution> getTriedList(int accountid) {
        Query query = namedQuery("nl.hsleiden.inf2b.groep4.solution.Solution.getTriedList");
        query.setParameter("accountid", accountid);
        List<Solution> list = query.list();
        ArrayList<Solution> newlist = distinctList(list);
        List<Solution> removeList = new ArrayList<>();
        for(Solution s : list) {
            if(!this.getDone(s.getPuzzle().getId(), s.getAccount().getAccountId())) {
                removeList.add(s);
            }
        }
        newlist.removeAll(removeList);
        currentSession().getSession().clear();
        return newlist;
    }

    public ArrayList<Solution> getFailList(int accountid) {
        Query query = namedQuery("nl.hsleiden.inf2b.groep4.solution.Solution.getTriedList");
        query.setParameter("accountid", accountid);
        List<Solution> list = query.list();
        ArrayList<Solution> newlist = distinctList(list);
        List<Solution> removeList = new ArrayList<>();
        for(Solution s : list) {
            if(this.getDone(s.getPuzzle().getId(), s.getAccount().getAccountId())) {
                removeList.add(s);
            }
        }
        newlist.removeAll(removeList);
        currentSession().getSession().clear();
        return newlist;
    }

    public ArrayList<Solution> distinctList(List<Solution> list) {
        ArrayList<Solution> newList = new ArrayList<>();
        if(list.size() > 0) {
            Solution tmps = list.get(0);
            newList.add(tmps);
            for(Solution s : list) {
                if(tmps.getPuzzle().getId() != s.getPuzzle().getId()) {
                    newList.add(s);
                    tmps = s;
                }
            }
        }
        return newList;
    }

    public int getMAXATTEMPTS() {
        return MAXATTEMPTS;
    }

	public int getTotalSolved(int accountid) {
        String hql = "select count(*) from Solution where account_id= :accountid";
        Query query = currentSession().getSession().createQuery(hql);
            query.setParameter("accountid", accountid);
            long totalSolved = (long) query.uniqueResult();
            return Math.toIntExact(totalSolved);
	}
}
