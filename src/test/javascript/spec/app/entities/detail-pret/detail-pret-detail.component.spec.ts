import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { DetailPretDetailComponent } from 'app/entities/detail-pret/detail-pret-detail.component';
import { DetailPret } from 'app/shared/model/detail-pret.model';

describe('Component Tests', () => {
  describe('DetailPret Management Detail Component', () => {
    let comp: DetailPretDetailComponent;
    let fixture: ComponentFixture<DetailPretDetailComponent>;
    const route = ({ data: of({ detailPret: new DetailPret(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [DetailPretDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DetailPretDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetailPretDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load detailPret on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detailPret).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
