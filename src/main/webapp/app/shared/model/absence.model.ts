import { Moment } from 'moment';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { IMotif } from 'app/shared/model/motif.model';
import { IExercice } from 'app/shared/model/exercice.model';

export interface IAbsence {
  id?: number;
  dateAbsence?: Moment;
  collaborateur?: ICollaborateur;
  motif?: IMotif;
  exercice?: IExercice;
}

export class Absence implements IAbsence {
  constructor(
    public id?: number,
    public dateAbsence?: Moment,
    public collaborateur?: ICollaborateur,
    public motif?: IMotif,
    public exercice?: IExercice
  ) {}
}
