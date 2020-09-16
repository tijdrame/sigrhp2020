import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { SigrhpTestModule } from '../../../test.module';
import { MembreFamilleDetailComponent } from 'app/entities/membre-famille/membre-famille-detail.component';
import { MembreFamille } from 'app/shared/model/membre-famille.model';

describe('Component Tests', () => {
  describe('MembreFamille Management Detail Component', () => {
    let comp: MembreFamilleDetailComponent;
    let fixture: ComponentFixture<MembreFamilleDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ membreFamille: new MembreFamille(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [MembreFamilleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MembreFamilleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MembreFamilleDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load membreFamille on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.membreFamille).toEqual(jasmine.objectContaining({ id: 123 }));
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
