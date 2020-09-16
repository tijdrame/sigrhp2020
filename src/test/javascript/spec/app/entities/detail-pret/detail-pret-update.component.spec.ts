import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { DetailPretUpdateComponent } from 'app/entities/detail-pret/detail-pret-update.component';
import { DetailPretService } from 'app/entities/detail-pret/detail-pret.service';
import { DetailPret } from 'app/shared/model/detail-pret.model';

describe('Component Tests', () => {
  describe('DetailPret Management Update Component', () => {
    let comp: DetailPretUpdateComponent;
    let fixture: ComponentFixture<DetailPretUpdateComponent>;
    let service: DetailPretService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [DetailPretUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DetailPretUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetailPretUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetailPretService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetailPret(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetailPret();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
