import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { PretDetailComponent } from 'app/entities/pret/pret-detail.component';
import { Pret } from 'app/shared/model/pret.model';

describe('Component Tests', () => {
  describe('Pret Management Detail Component', () => {
    let comp: PretDetailComponent;
    let fixture: ComponentFixture<PretDetailComponent>;
    const route = ({ data: of({ pret: new Pret(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [PretDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PretDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PretDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pret on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pret).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
