import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { SituationMatrimonialeDetailComponent } from 'app/entities/situation-matrimoniale/situation-matrimoniale-detail.component';
import { SituationMatrimoniale } from 'app/shared/model/situation-matrimoniale.model';

describe('Component Tests', () => {
  describe('SituationMatrimoniale Management Detail Component', () => {
    let comp: SituationMatrimonialeDetailComponent;
    let fixture: ComponentFixture<SituationMatrimonialeDetailComponent>;
    const route = ({ data: of({ situationMatrimoniale: new SituationMatrimoniale(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [SituationMatrimonialeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SituationMatrimonialeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SituationMatrimonialeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load situationMatrimoniale on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.situationMatrimoniale).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
