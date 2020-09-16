import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { RemboursementDetailComponent } from 'app/entities/remboursement/remboursement-detail.component';
import { Remboursement } from 'app/shared/model/remboursement.model';

describe('Component Tests', () => {
  describe('Remboursement Management Detail Component', () => {
    let comp: RemboursementDetailComponent;
    let fixture: ComponentFixture<RemboursementDetailComponent>;
    const route = ({ data: of({ remboursement: new Remboursement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [RemboursementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RemboursementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RemboursementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load remboursement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.remboursement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
