import { Moment } from 'moment';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';

export interface IPieces {
  id?: number;
  libelle?: string;
  dateCreation?: Moment;
  dateExpiration?: Moment;
  imageContentType?: string;
  image?: any;
  collaborateur?: ICollaborateur;
}

export class Pieces implements IPieces {
  constructor(
    public id?: number,
    public libelle?: string,
    public dateCreation?: Moment,
    public dateExpiration?: Moment,
    public imageContentType?: string,
    public image?: any,
    public collaborateur?: ICollaborateur
  ) {}
}
