export interface IExercice {
  id?: number;
  debutExercice?: number;
  finExercice?: number;
  actif?: boolean;
}

export class Exercice implements IExercice {
  constructor(public id?: number, public debutExercice?: number, public finExercice?: number, public actif?: boolean) {
    this.actif = this.actif || false;
  }
}
