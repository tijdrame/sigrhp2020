import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StructureService } from 'app/entities/structure/structure.service';
import { IStructure, Structure } from 'app/shared/model/structure.model';

describe('Service Tests', () => {
  describe('Structure Service', () => {
    let injector: TestBed;
    let service: StructureService;
    let httpMock: HttpTestingController;
    let elemDefault: IStructure;
    let expectedResult: IStructure | IStructure[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StructureService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Structure(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        false,
        'image/png',
        'AAAAAAA',
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Structure', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Structure()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Structure', () => {
        const returnedFromService = Object.assign(
          {
            denomination: 'BBBBBB',
            telephone: 'BBBBBB',
            adresse: 'BBBBBB',
            ninea: 'BBBBBB',
            capital: 1,
            numeroIpres: 'BBBBBB',
            numeroCss: 'BBBBBB',
            logo: 'BBBBBB',
            ipm: true,
            signature: 'BBBBBB',
            montantIpm: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Structure', () => {
        const returnedFromService = Object.assign(
          {
            denomination: 'BBBBBB',
            telephone: 'BBBBBB',
            adresse: 'BBBBBB',
            ninea: 'BBBBBB',
            capital: 1,
            numeroIpres: 'BBBBBB',
            numeroCss: 'BBBBBB',
            logo: 'BBBBBB',
            ipm: true,
            signature: 'BBBBBB',
            montantIpm: 1,
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

      it('should delete a Structure', () => {
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
