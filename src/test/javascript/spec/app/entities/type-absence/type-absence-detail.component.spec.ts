import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { TypeAbsenceDetailComponent } from 'app/entities/type-absence/type-absence-detail.component';
import { TypeAbsence } from 'app/shared/model/type-absence.model';

describe('Component Tests', () => {
  describe('TypeAbsence Management Detail Component', () => {
    let comp: TypeAbsenceDetailComponent;
    let fixture: ComponentFixture<TypeAbsenceDetailComponent>;
    const route = ({ data: of({ typeAbsence: new TypeAbsence(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [TypeAbsenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeAbsenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeAbsenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeAbsence on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeAbsence).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
