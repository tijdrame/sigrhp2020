import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { DemandeCongeDetailComponent } from 'app/entities/demande-conge/demande-conge-detail.component';
import { DemandeConge } from 'app/shared/model/demande-conge.model';

describe('Component Tests', () => {
  describe('DemandeConge Management Detail Component', () => {
    let comp: DemandeCongeDetailComponent;
    let fixture: ComponentFixture<DemandeCongeDetailComponent>;
    const route = ({ data: of({ demandeConge: new DemandeConge(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [DemandeCongeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DemandeCongeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DemandeCongeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load demandeConge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.demandeConge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
