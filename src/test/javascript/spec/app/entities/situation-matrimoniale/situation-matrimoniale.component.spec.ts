import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { SigrhpTestModule } from '../../../test.module';
import { SituationMatrimonialeComponent } from 'app/entities/situation-matrimoniale/situation-matrimoniale.component';
import { SituationMatrimonialeService } from 'app/entities/situation-matrimoniale/situation-matrimoniale.service';
import { SituationMatrimoniale } from 'app/shared/model/situation-matrimoniale.model';

describe('Component Tests', () => {
  describe('SituationMatrimoniale Management Component', () => {
    let comp: SituationMatrimonialeComponent;
    let fixture: ComponentFixture<SituationMatrimonialeComponent>;
    let service: SituationMatrimonialeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [SituationMatrimonialeComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(SituationMatrimonialeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SituationMatrimonialeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SituationMatrimonialeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SituationMatrimoniale(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.situationMatrimoniales && comp.situationMatrimoniales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SituationMatrimoniale(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.situationMatrimoniales && comp.situationMatrimoniales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
