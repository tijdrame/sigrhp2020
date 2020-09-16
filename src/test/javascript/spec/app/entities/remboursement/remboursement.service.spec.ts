import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RemboursementService } from 'app/entities/remboursement/remboursement.service';
import { IRemboursement, Remboursement } from 'app/shared/model/remboursement.model';

describe('Service Tests', () => {
  describe('Remboursement Service', () => {
    let injector: TestBed;
    let service: RemboursementService;
    let httpMock: HttpTestingController;
    let elemDefault: IRemboursement;
    let expectedResult: IRemboursement | IRemboursement[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RemboursementService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Remboursement(0, currentDate, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateRemboursement: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Remboursement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateRemboursement: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateRemboursement: currentDate,
          },
          returnedFromService
        );

        service.create(new Remboursement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Remboursement', () => {
        const returnedFromService = Object.assign(
          {
            dateRemboursement: currentDate.format(DATE_FORMAT),
            montant: 1,
            isRembourse: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateRemboursement: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Remboursement', () => {
        const returnedFromService = Object.assign(
          {
            dateRemboursement: currentDate.format(DATE_FORMAT),
            montant: 1,
            isRembourse: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateRemboursement: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Remboursement', () => {
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
