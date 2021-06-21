import { Puzzle } from "./Puzzle";
import { Account } from "../../admin/account";

export class PuzzleListModel{

  tried : Array<Account>;
  todo : Array<Account>;
  done : Array<Account>;
  fail : Array<Account>;
  doneNoAttemptsLeft : Array<Account>;

}
