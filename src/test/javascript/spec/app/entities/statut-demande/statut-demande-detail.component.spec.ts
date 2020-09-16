import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { StatutDemandeDetailComponent } from 'app/entities/statut-demande/statut-demande-detail.component';
import { StatutDemande } from 'app/shared/model/statut-demande.model';

describe('Component Tests', () => {
  describe('StatutDemande Management Detail Component', () => {
    let comp: StatutDemandeDetailComponent;
    let fixture: ComponentFixture<StatutDemandeDetailComponent>;
    const route = ({ data: of({ statutDemande: new StatutDemande(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [StatutDemandeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(StatutDemandeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatutDemandeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load statutDemande on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.statutDemande).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
