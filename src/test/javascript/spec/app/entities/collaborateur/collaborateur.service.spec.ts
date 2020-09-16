import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CollaborateurService } from 'app/entities/collaborateur/collaborateur.service';
import { ICollaborateur, Collaborateur } from 'app/shared/model/collaborateur.model';

describe('Service Tests', () => {
  describe('Collaborateur Service', () => {
    let injector: TestBed;
    let service: CollaborateurService;
    let httpMock: HttpTestingController;
    let elemDefault: ICollaborateur;
    let expectedResult: ICollaborateur | ICollaborateur[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CollaborateurService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Collaborateur(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        currentDate,
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateNaissance: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Collaborateur', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateNaissance: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService
        );

        service.create(new Collaborateur()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Collaborateur', () => {
        const returnedFromService = Object.assign(
          {
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            matricule: 'BBBBBB',
            adresse: 'BBBBBB',
            tauxHoraire: 1,
            salaireDeBase: 1,
            surSalaire: 1,
            retenueRepas: 1,
            dateNaissance: currentDate.format(DATE_FORMAT),
            photo: 'BBBBBB',
            login: 'BBBBBB',
            email: 'BBBBBB',
            primeTransport: 1,
            telephone: 'BBBBBB',
            numeroRib: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Collaborateur', () => {
        const returnedFromService = Object.assign(
          {
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            matricule: 'BBBBBB',
            adresse: 'BBBBBB',
            tauxHoraire: 1,
            salaireDeBase: 1,
            surSalaire: 1,
            retenueRepas: 1,
            dateNaissance: currentDate.format(DATE_FORMAT),
            photo: 'BBBBBB',
            login: 'BBBBBB',
            email: 'BBBBBB',
            primeTransport: 1,
            telephone: 'BBBBBB',
            numeroRib: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Collaborateur', () => {
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
