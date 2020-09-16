import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PretService } from 'app/entities/pret/pret.service';
import { IPret, Pret } from 'app/shared/model/pret.model';

describe('Service Tests', () => {
  describe('Pret Service', () => {
    let injector: TestBed;
    let service: PretService;
    let httpMock: HttpTestingController;
    let elemDefault: IPret;
    let expectedResult: IPret | IPret[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PretService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Pret(0, 'AAAAAAA', 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datePret: currentDate.format(DATE_FORMAT),
            dateDebutRemboursement: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Pret', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datePret: currentDate.format(DATE_FORMAT),
            dateDebutRemboursement: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePret: currentDate,
            dateDebutRemboursement: currentDate,
          },
          returnedFromService
        );

        service.create(new Pret()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Pret', () => {
        const returnedFromService = Object.assign(
          {
            libelle: 'BBBBBB',
            nbRemboursement: 1,
            datePret: currentDate.format(DATE_FORMAT),
            dateDebutRemboursement: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePret: currentDate,
            dateDebutRemboursement: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Pret', () => {
        const returnedFromService = Object.assign(
          {
            libelle: 'BBBBBB',
            nbRemboursement: 1,
            datePret: currentDate.format(DATE_FORMAT),
            dateDebutRemboursement: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePret: currentDate,
            dateDebutRemboursement: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Pret', () => {
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
