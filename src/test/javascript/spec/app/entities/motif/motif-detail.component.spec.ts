import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { MotifDetailComponent } from 'app/entities/motif/motif-detail.component';
import { Motif } from 'app/shared/model/motif.model';

describe('Component Tests', () => {
  describe('Motif Management Detail Component', () => {
    let comp: MotifDetailComponent;
    let fixture: ComponentFixture<MotifDetailComponent>;
    const route = ({ data: of({ motif: new Motif(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [MotifDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MotifDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MotifDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load motif on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.motif).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
