import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { TypeContratDetailComponent } from 'app/entities/type-contrat/type-contrat-detail.component';
import { TypeContrat } from 'app/shared/model/type-contrat.model';

describe('Component Tests', () => {
  describe('TypeContrat Management Detail Component', () => {
    let comp: TypeContratDetailComponent;
    let fixture: ComponentFixture<TypeContratDetailComponent>;
    const route = ({ data: of({ typeContrat: new TypeContrat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [TypeContratDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeContratDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeContratDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeContrat on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeContrat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
