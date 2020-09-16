import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { SigrhpTestModule } from '../../../test.module';
import { PiecesDetailComponent } from 'app/entities/pieces/pieces-detail.component';
import { Pieces } from 'app/shared/model/pieces.model';

describe('Component Tests', () => {
  describe('Pieces Management Detail Component', () => {
    let comp: PiecesDetailComponent;
    let fixture: ComponentFixture<PiecesDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ pieces: new Pieces(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [PiecesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PiecesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PiecesDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load pieces on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pieces).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
