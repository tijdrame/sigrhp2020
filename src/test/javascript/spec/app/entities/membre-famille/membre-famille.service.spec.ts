import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MembreFamilleService } from 'app/entities/membre-famille/membre-famille.service';
import { IMembreFamille, MembreFamille } from 'app/shared/model/membre-famille.model';

describe('Service Tests', () => {
  describe('MembreFamille Service', () => {
    let injector: TestBed;
    let service: MembreFamilleService;
    let httpMock: HttpTestingController;
    let elemDefault: IMembreFamille;
    let expectedResult: IMembreFamille | IMembreFamille[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MembreFamilleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MembreFamille(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'image/png',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateNaissance: currentDate.format(DATE_FORMAT),
            dateMariage: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MembreFamille', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateNaissance: currentDate.format(DATE_FORMAT),
            dateMariage: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
            dateMariage: currentDate,
          },
          returnedFromService
        );

        service.create(new MembreFamille()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MembreFamille', () => {
        const returnedFromService = Object.assign(
          {
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            adresse: 'BBBBBB',
            telephone: 'BBBBBB',
            dateNaissance: currentDate.format(DATE_FORMAT),
            dateMariage: currentDate.format(DATE_FORMAT),
            photo: 'BBBBBB',
            isActif: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
            dateMariage: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MembreFamille', () => {
        const returnedFromService = Object.assign(
          {
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            adresse: 'BBBBBB',
            telephone: 'BBBBBB',
            dateNaissance: currentDate.format(DATE_FORMAT),
            dateMariage: currentDate.format(DATE_FORMAT),
            photo: 'BBBBBB',
            isActif: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
            dateMariage: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MembreFamille', () => {
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
