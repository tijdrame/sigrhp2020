import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PiecesService } from 'app/entities/pieces/pieces.service';
import { IPieces, Pieces } from 'app/shared/model/pieces.model';

describe('Service Tests', () => {
  describe('Pieces Service', () => {
    let injector: TestBed;
    let service: PiecesService;
    let httpMock: HttpTestingController;
    let elemDefault: IPieces;
    let expectedResult: IPieces | IPieces[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PiecesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Pieces(0, 'AAAAAAA', currentDate, currentDate, 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCreation: currentDate.format(DATE_FORMAT),
            dateExpiration: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Pieces', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCreation: currentDate.format(DATE_FORMAT),
            dateExpiration: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate,
            dateExpiration: currentDate,
          },
          returnedFromService
        );

        service.create(new Pieces()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Pieces', () => {
        const returnedFromService = Object.assign(
          {
            libelle: 'BBBBBB',
            dateCreation: currentDate.format(DATE_FORMAT),
            dateExpiration: currentDate.format(DATE_FORMAT),
            image: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate,
            dateExpiration: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Pieces', () => {
        const returnedFromService = Object.assign(
          {
            libelle: 'BBBBBB',
            dateCreation: currentDate.format(DATE_FORMAT),
            dateExpiration: currentDate.format(DATE_FORMAT),
            image: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate,
            dateExpiration: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Pieces', () => {
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
