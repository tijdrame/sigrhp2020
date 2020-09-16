import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { NationaliteDetailComponent } from 'app/entities/nationalite/nationalite-detail.component';
import { Nationalite } from 'app/shared/model/nationalite.model';

describe('Component Tests', () => {
  describe('Nationalite Management Detail Component', () => {
    let comp: NationaliteDetailComponent;
    let fixture: ComponentFixture<NationaliteDetailComponent>;
    const route = ({ data: of({ nationalite: new Nationalite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [NationaliteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NationaliteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NationaliteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nationalite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nationalite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
