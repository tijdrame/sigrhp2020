import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { FonctionDetailComponent } from 'app/entities/fonction/fonction-detail.component';
import { Fonction } from 'app/shared/model/fonction.model';

describe('Component Tests', () => {
  describe('Fonction Management Detail Component', () => {
    let comp: FonctionDetailComponent;
    let fixture: ComponentFixture<FonctionDetailComponent>;
    const route = ({ data: of({ fonction: new Fonction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [FonctionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FonctionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FonctionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fonction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fonction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
