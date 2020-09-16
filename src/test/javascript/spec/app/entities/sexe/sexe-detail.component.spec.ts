import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { SexeDetailComponent } from 'app/entities/sexe/sexe-detail.component';
import { Sexe } from 'app/shared/model/sexe.model';

describe('Component Tests', () => {
  describe('Sexe Management Detail Component', () => {
    let comp: SexeDetailComponent;
    let fixture: ComponentFixture<SexeDetailComponent>;
    const route = ({ data: of({ sexe: new Sexe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [SexeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SexeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SexeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sexe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sexe).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
