import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { ExerciceDetailComponent } from 'app/entities/exercice/exercice-detail.component';
import { Exercice } from 'app/shared/model/exercice.model';

describe('Component Tests', () => {
  describe('Exercice Management Detail Component', () => {
    let comp: ExerciceDetailComponent;
    let fixture: ComponentFixture<ExerciceDetailComponent>;
    const route = ({ data: of({ exercice: new Exercice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [ExerciceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExerciceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExerciceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load exercice on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.exercice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
