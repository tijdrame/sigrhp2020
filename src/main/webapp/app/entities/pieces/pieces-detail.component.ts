import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiBase64Service, JhiDataUtils } from 'ng-jhipster';

import { jsPDF } from 'jspdf';
import  html2canvas from 'html2canvas';

import { IPieces } from 'app/shared/model/pieces.model';

@Component({
  selector: 'jhi-pieces-detail',
  templateUrl: './pieces-detail.component.html',
})
export class PiecesDetailComponent implements OnInit {
  pieces: IPieces | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute,
    private jhiBase64Service: JhiBase64Service) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pieces }) => (this.pieces = pieces));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }

  downLoadPdf():void {
    const data = document.getElementById('myPiece') as HTMLElement;
     html2canvas(data).then(canvas =>{
      const imgData = this.pieces!.image;
      console.log('img= '+ imgData);
      const imgWidth = 208;
        // let pageHeight = 295;
        const imgHeight = canvas.height * imgWidth / canvas.width;
        // let heightLeft = imgHeight;
        // const contentDataURL = canvas.toDataURL('image/png'); for classique
        const pdf = new jsPDF('p', 'mm', 'a4');
        console.log('height='+imgHeight);
        // alert('height'+imgHeight)
        // pdf.addImage(contentDataURL, 'PNG', 0, 0, imgWidth, imgHeight); for classique
        pdf.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
        pdf.save(this.pieces!.collaborateur!.prenom + '_' + this.pieces!.collaborateur!.nom! + '_' +this.pieces!.libelle+'.pdf');
    }); 
  }
}
