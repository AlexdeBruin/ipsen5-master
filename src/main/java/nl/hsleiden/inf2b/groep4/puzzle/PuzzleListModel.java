package nl.hsleiden.inf2b.groep4.puzzle;

import nl.hsleiden.inf2b.groep4.account.Account;

import java.util.List;

/**
 * This class is used to send a few lists to the frontend.
 * These list show the puzzle that the group can still do, have tried and have done.
 */
public class PuzzleListModel {

    private List<Account> todo;
    private List<Account> tried;
    private List<Account> done;
    private List<Account> fail;
    private List<Account> doneNoAttemptsLeft;

    public PuzzleListModel() {

    }

    public List<Account> getTodo() {
        return todo;
    }

    public void setTodo(List<Account> todo) {
        this.todo = todo;
    }

    public List<Account> getTried() {
        return tried;
    }

    public void setTried(List<Account> tried) {
        this.tried = tried;
    }

    public List<Account> getDone() {
        return done;
    }

    public void setDone(List<Account> done) {
        this.done = done;
    }

    public List<Account> getFail() {
        return fail;
    }

    public void setFail(List<Account> fail) {
        this.fail = fail;
    }

    public List<Account> getDoneNoAttemptsLeft() {
        return doneNoAttemptsLeft;
    }

    public void setNoDoneAttemptsLeft(List<Account> doneNoAttemptsLeft) {
        this.doneNoAttemptsLeft = doneNoAttemptsLeft;
    }
}
