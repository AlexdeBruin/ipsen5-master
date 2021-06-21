import { Score } from './score';
import { Role } from '../shared/role';
import { Puzzle } from '../puzzle/models/Puzzle';

export class Account {
  password: string;
  score: Score;
  active: boolean;
  requiresPasswordChange: boolean;
  accountId: number;
  username: string;
  accountRole: Role;
  puzzle: Puzzle;

  constructor() {
  }


}
