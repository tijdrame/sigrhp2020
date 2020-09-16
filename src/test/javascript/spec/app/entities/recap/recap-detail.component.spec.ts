import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { RecapDetailComponent } from 'app/entities/recap/recap-detail.component';
import { Recap } from 'app/shared/model/recap.model';

describe('Component Tests', () => {
  describe('Recap Management Detail Component', () => {
    let comp: RecapDetailComponent;
    let fixture: ComponentFixture<RecapDetailComponent>;
    const route = ({ data: of({ recap: new Recap(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [RecapDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RecapDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecapDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recap on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recap).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
