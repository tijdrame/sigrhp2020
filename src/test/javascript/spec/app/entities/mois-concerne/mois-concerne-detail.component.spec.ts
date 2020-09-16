import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { MoisConcerneDetailComponent } from 'app/entities/mois-concerne/mois-concerne-detail.component';
import { MoisConcerne } from 'app/shared/model/mois-concerne.model';

describe('Component Tests', () => {
  describe('MoisConcerne Management Detail Component', () => {
    let comp: MoisConcerneDetailComponent;
    let fixture: ComponentFixture<MoisConcerneDetailComponent>;
    const route = ({ data: of({ moisConcerne: new MoisConcerne(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [MoisConcerneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MoisConcerneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MoisConcerneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load moisConcerne on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.moisConcerne).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
