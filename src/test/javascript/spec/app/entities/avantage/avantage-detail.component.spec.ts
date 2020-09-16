import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { AvantageDetailComponent } from 'app/entities/avantage/avantage-detail.component';
import { Avantage } from 'app/shared/model/avantage.model';

describe('Component Tests', () => {
  describe('Avantage Management Detail Component', () => {
    let comp: AvantageDetailComponent;
    let fixture: ComponentFixture<AvantageDetailComponent>;
    const route = ({ data: of({ avantage: new Avantage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [AvantageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvantageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvantageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avantage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avantage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
