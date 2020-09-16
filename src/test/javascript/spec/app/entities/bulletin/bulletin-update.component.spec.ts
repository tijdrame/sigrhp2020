import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { BulletinUpdateComponent } from 'app/entities/bulletin/bulletin-update.component';
import { BulletinService } from 'app/entities/bulletin/bulletin.service';
import { Bulletin } from 'app/shared/model/bulletin.model';

describe('Component Tests', () => {
  describe('Bulletin Management Update Component', () => {
    let comp: BulletinUpdateComponent;
    let fixture: ComponentFixture<BulletinUpdateComponent>;
    let service: BulletinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [BulletinUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BulletinUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BulletinUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BulletinService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bulletin(123);
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
        const entity = new Bulletin();
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
