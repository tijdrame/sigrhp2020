import { IConvention } from 'app/shared/model/convention.model';

export interface IStructure {
  id?: number;
  denomination?: string;
  telephone?: string;
  adresse?: string;
  ninea?: string;
  capital?: number;
  numeroIpres?: string;
  numeroCss?: string;
  logoContentType?: string;
  logo?: any;
  ipm?: boolean;
  signatureContentType?: string;
  signature?: any;
  montantIpm?: number;
  convention?: IConvention;
}

export class Structure implements IStructure {
  constructor(
    public id?: number,
    public denomination?: string,
    public telephone?: string,
    public adresse?: string,
    public ninea?: string,
    public capital?: number,
    public numeroIpres?: string,
    public numeroCss?: string,
    public logoContentType?: string,
    public logo?: any,
    public ipm?: boolean,
    public signatureContentType?: string,
    public signature?: any,
    public montantIpm?: number,
    public convention?: IConvention
  ) {
    this.ipm = this.ipm || false;
  }
}
