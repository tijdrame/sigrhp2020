import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { SigrhpTestModule } from '../../../test.module';
import { CollaborateurDetailComponent } from 'app/entities/collaborateur/collaborateur-detail.component';
import { Collaborateur } from 'app/shared/model/collaborateur.model';

describe('Component Tests', () => {
  describe('Collaborateur Management Detail Component', () => {
    let comp: CollaborateurDetailComponent;
    let fixture: ComponentFixture<CollaborateurDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ collaborateur: new Collaborateur(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [CollaborateurDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CollaborateurDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollaborateurDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load collaborateur on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.collaborateur).toEqual(jasmine.objectContaining({ id: 123 }));
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
