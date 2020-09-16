import { Moment } from 'moment';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { ISexe } from 'app/shared/model/sexe.model';
import { ITypeRelation } from 'app/shared/model/type-relation.model';

export interface IMembreFamille {
  id?: number;
  prenom?: string;
  nom?: string;
  adresse?: string;
  telephone?: string;
  dateNaissance?: Moment;
  dateMariage?: Moment;
  photoContentType?: string;
  photo?: any;
  isActif?: boolean;
  collaborateur?: ICollaborateur;
  sexe?: ISexe;
  typeRelation?: ITypeRelation;
}

export class MembreFamille implements IMembreFamille {
  constructor(
    public id?: number,
    public prenom?: string,
    public nom?: string,
    public adresse?: string,
    public telephone?: string,
    public dateNaissance?: Moment,
    public dateMariage?: Moment,
    public photoContentType?: string,
    public photo?: any,
    public isActif?: boolean,
    public collaborateur?: ICollaborateur,
    public sexe?: ISexe,
    public typeRelation?: ITypeRelation
  ) {
    this.isActif = this.isActif || false;
  }
}
