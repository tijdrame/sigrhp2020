import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { PrimeDetailComponent } from 'app/entities/prime/prime-detail.component';
import { Prime } from 'app/shared/model/prime.model';

describe('Component Tests', () => {
  describe('Prime Management Detail Component', () => {
    let comp: PrimeDetailComponent;
    let fixture: ComponentFixture<PrimeDetailComponent>;
    const route = ({ data: of({ prime: new Prime(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [PrimeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PrimeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrimeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load prime on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prime).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
