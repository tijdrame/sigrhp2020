import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { RegimeDetailComponent } from 'app/entities/regime/regime-detail.component';
import { Regime } from 'app/shared/model/regime.model';

describe('Component Tests', () => {
  describe('Regime Management Detail Component', () => {
    let comp: RegimeDetailComponent;
    let fixture: ComponentFixture<RegimeDetailComponent>;
    const route = ({ data: of({ regime: new Regime(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [RegimeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RegimeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegimeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load regime on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.regime).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
