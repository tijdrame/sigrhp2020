import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BulletinService } from 'app/entities/bulletin/bulletin.service';
import { IBulletin, Bulletin } from 'app/shared/model/bulletin.model';

describe('Service Tests', () => {
  describe('Bulletin Service', () => {
    let injector: TestBed;
    let service: BulletinService;
    let httpMock: HttpTestingController;
    let elemDefault: IBulletin;
    let expectedResult: IBulletin | IBulletin[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BulletinService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Bulletin(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Bulletin', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Bulletin()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Bulletin', () => {
        const returnedFromService = Object.assign(
          {
            retenueIpm: 1,
            retenuePharmacie: 1,
            autreRetenue: 1,
            brutFiscal: 1,
            netAPayer: 1,
            salaireBrutMensuel: 1,
            impotSurRevenu: 1,
            trimf: 1,
            ipresPartSalariale: 1,
            totRetenue: 1,
            ipresPartPatronales: 1,
            cssAccidentDeTravail: 1,
            cssPrestationFamiliale: 1,
            ipmPatronale: 1,
            contributionForfaitaire: 1,
            nbPart: 1,
            nbFemmes: 1,
            nbEnfants: 1,
            primeImposable: 1,
            primeNonImposable: 1,
            avantage: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Bulletin', () => {
        const returnedFromService = Object.assign(
          {
            retenueIpm: 1,
            retenuePharmacie: 1,
            autreRetenue: 1,
            brutFiscal: 1,
            netAPayer: 1,
            salaireBrutMensuel: 1,
            impotSurRevenu: 1,
            trimf: 1,
            ipresPartSalariale: 1,
            totRetenue: 1,
            ipresPartPatronales: 1,
            cssAccidentDeTravail: 1,
            cssPrestationFamiliale: 1,
            ipmPatronale: 1,
            contributionForfaitaire: 1,
            nbPart: 1,
            nbFemmes: 1,
            nbEnfants: 1,
            primeImposable: 1,
            primeNonImposable: 1,
            avantage: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Bulletin', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
