import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { AvantageCollabDetailComponent } from 'app/entities/avantage-collab/avantage-collab-detail.component';
import { AvantageCollab } from 'app/shared/model/avantage-collab.model';

describe('Component Tests', () => {
  describe('AvantageCollab Management Detail Component', () => {
    let comp: AvantageCollabDetailComponent;
    let fixture: ComponentFixture<AvantageCollabDetailComponent>;
    const route = ({ data: of({ avantageCollab: new AvantageCollab(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [AvantageCollabDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvantageCollabDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvantageCollabDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avantageCollab on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avantageCollab).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
