export interface IBareme {
  id?: number;
  revenuBrut?: number;
  trimF?: number;
  unePart?: number;
  unePartEtDemi?: number;
  deuxParts?: number;
  deuxPartsEtDemi?: number;
  troisParts?: number;
  troisPartsEtDemi?: number;
  quatreParts?: number;
  quatrePartsEtDemi?: number;
  cinqParts?: number;
}

export class Bareme implements IBareme {
  constructor(
    public id?: number,
    public revenuBrut?: number,
    public trimF?: number,
    public unePart?: number,
    public unePartEtDemi?: number,
    public deuxParts?: number,
    public deuxPartsEtDemi?: number,
    public troisParts?: number,
    public troisPartsEtDemi?: number,
    public quatreParts?: number,
    public quatrePartsEtDemi?: number,
    public cinqParts?: number
  ) {}
}
