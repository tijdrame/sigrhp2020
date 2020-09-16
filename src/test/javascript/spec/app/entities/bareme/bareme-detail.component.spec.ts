import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { BaremeDetailComponent } from 'app/entities/bareme/bareme-detail.component';
import { Bareme } from 'app/shared/model/bareme.model';

describe('Component Tests', () => {
  describe('Bareme Management Detail Component', () => {
    let comp: BaremeDetailComponent;
    let fixture: ComponentFixture<BaremeDetailComponent>;
    const route = ({ data: of({ bareme: new Bareme(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [BaremeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BaremeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BaremeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bareme on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bareme).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
