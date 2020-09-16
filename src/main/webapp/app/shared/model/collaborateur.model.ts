import { Moment } from 'moment';
import { IFonction } from 'app/shared/model/fonction.model';
import { ICategorie } from 'app/shared/model/categorie.model';
import { INationalite } from 'app/shared/model/nationalite.model';
import { IStatut } from 'app/shared/model/statut.model';
import { ISituationMatrimoniale } from 'app/shared/model/situation-matrimoniale.model';
import { ITypeContrat } from 'app/shared/model/type-contrat.model';
import { IRegime } from 'app/shared/model/regime.model';
import { IUser } from 'app/core/user/user.model';
import { ISexe } from 'app/shared/model/sexe.model';
import { IStructure } from 'app/shared/model/structure.model';

export interface ICollaborateur {
  id?: number;
  prenom?: string;
  nom?: string;
  matricule?: string;
  adresse?: string;
  tauxHoraire?: number;
  salaireDeBase?: number;
  surSalaire?: number;
  retenueRepas?: number;
  dateNaissance?: Moment;
  photoContentType?: string;
  photo?: any;
  login?: string;
  email?: string;
  primeTransport?: number;
  telephone?: string;
  numeroRib?: string;
  fonction?: IFonction;
  categorie?: ICategorie;
  nationalite?: INationalite;
  statut?: IStatut;
  situationMatrimoniale?: ISituationMatrimoniale;
  typeContrat?: ITypeContrat;
  regime?: IRegime;
  userCollab?: IUser;
  sexe?: ISexe;
  structure?: IStructure;
}

export class Collaborateur implements ICollaborateur {
  constructor(
    public id?: number,
    public prenom?: string,
    public nom?: string,
    public matricule?: string,
    public adresse?: string,
    public tauxHoraire?: number,
    public salaireDeBase?: number,
    public surSalaire?: number,
    public retenueRepas?: number,
    public dateNaissance?: Moment,
    public photoContentType?: string,
    public photo?: any,
    public login?: string,
    public email?: string,
    public primeTransport?: number,
    public telephone?: string,
    public numeroRib?: string,
    public fonction?: IFonction,
    public categorie?: ICategorie,
    public nationalite?: INationalite,
    public statut?: IStatut,
    public situationMatrimoniale?: ISituationMatrimoniale,
    public typeContrat?: ITypeContrat,
    public regime?: IRegime,
    public userCollab?: IUser,
    public sexe?: ISexe,
    public structure?: IStructure
  ) {}
}
