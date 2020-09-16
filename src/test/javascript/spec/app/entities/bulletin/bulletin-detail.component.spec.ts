import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { BulletinDetailComponent } from 'app/entities/bulletin/bulletin-detail.component';
import { Bulletin } from 'app/shared/model/bulletin.model';

describe('Component Tests', () => {
  describe('Bulletin Management Detail Component', () => {
    let comp: BulletinDetailComponent;
    let fixture: ComponentFixture<BulletinDetailComponent>;
    const route = ({ data: of({ bulletin: new Bulletin(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [BulletinDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BulletinDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BulletinDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bulletin on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bulletin).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
