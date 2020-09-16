import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { SituationMatrimonialeUpdateComponent } from 'app/entities/situation-matrimoniale/situation-matrimoniale-update.component';
import { SituationMatrimonialeService } from 'app/entities/situation-matrimoniale/situation-matrimoniale.service';
import { SituationMatrimoniale } from 'app/shared/model/situation-matrimoniale.model';

describe('Component Tests', () => {
  describe('SituationMatrimoniale Management Update Component', () => {
    let comp: SituationMatrimonialeUpdateComponent;
    let fixture: ComponentFixture<SituationMatrimonialeUpdateComponent>;
    let service: SituationMatrimonialeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [SituationMatrimonialeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SituationMatrimonialeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SituationMatrimonialeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SituationMatrimonialeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SituationMatrimoniale(123);
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
        const entity = new SituationMatrimoniale();
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
