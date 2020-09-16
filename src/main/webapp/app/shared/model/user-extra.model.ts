import { IStructure } from 'app/shared/model/structure.model';
import { IUser } from 'app/core/user/user.model';

export interface IUserExtra {
  id?: number;
  structure?: IStructure;
  user?: IUser;
}

export class UserExtra implements IUserExtra {
  constructor(public id?: number, public structure?: IStructure, public user?: IUser) {}
}
