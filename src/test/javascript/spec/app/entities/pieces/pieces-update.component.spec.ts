import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { PiecesUpdateComponent } from 'app/entities/pieces/pieces-update.component';
import { PiecesService } from 'app/entities/pieces/pieces.service';
import { Pieces } from 'app/shared/model/pieces.model';

describe('Component Tests', () => {
  describe('Pieces Management Update Component', () => {
    let comp: PiecesUpdateComponent;
    let fixture: ComponentFixture<PiecesUpdateComponent>;
    let service: PiecesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [PiecesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PiecesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PiecesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PiecesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pieces(123);
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
        const entity = new Pieces();
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
