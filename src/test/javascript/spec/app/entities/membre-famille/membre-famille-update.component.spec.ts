import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { MembreFamilleUpdateComponent } from 'app/entities/membre-famille/membre-famille-update.component';
import { MembreFamilleService } from 'app/entities/membre-famille/membre-famille.service';
import { MembreFamille } from 'app/shared/model/membre-famille.model';

describe('Component Tests', () => {
  describe('MembreFamille Management Update Component', () => {
    let comp: MembreFamilleUpdateComponent;
    let fixture: ComponentFixture<MembreFamilleUpdateComponent>;
    let service: MembreFamilleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [MembreFamilleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MembreFamilleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MembreFamilleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MembreFamilleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MembreFamille(123);
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
        const entity = new MembreFamille();
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
