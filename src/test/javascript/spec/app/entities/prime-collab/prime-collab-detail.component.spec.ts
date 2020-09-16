import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { PrimeCollabDetailComponent } from 'app/entities/prime-collab/prime-collab-detail.component';
import { PrimeCollab } from 'app/shared/model/prime-collab.model';

describe('Component Tests', () => {
  describe('PrimeCollab Management Detail Component', () => {
    let comp: PrimeCollabDetailComponent;
    let fixture: ComponentFixture<PrimeCollabDetailComponent>;
    const route = ({ data: of({ primeCollab: new PrimeCollab(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [PrimeCollabDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PrimeCollabDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrimeCollabDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load primeCollab on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.primeCollab).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
