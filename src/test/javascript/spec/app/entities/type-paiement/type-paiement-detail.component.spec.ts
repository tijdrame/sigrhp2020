import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { TypePaiementDetailComponent } from 'app/entities/type-paiement/type-paiement-detail.component';
import { TypePaiement } from 'app/shared/model/type-paiement.model';

describe('Component Tests', () => {
  describe('TypePaiement Management Detail Component', () => {
    let comp: TypePaiementDetailComponent;
    let fixture: ComponentFixture<TypePaiementDetailComponent>;
    const route = ({ data: of({ typePaiement: new TypePaiement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [TypePaiementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypePaiementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypePaiementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typePaiement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typePaiement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
