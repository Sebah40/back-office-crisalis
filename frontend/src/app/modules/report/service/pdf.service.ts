import { Injectable } from '@angular/core';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';

@Injectable({
  providedIn: 'root',
})
export class PdfService {
  generatePdf(content: HTMLElement, namePdf: string) {
    const options = {
      scale: 2.5,
    };

    html2canvas(content, options).then((canvas) => {
      const imgData = canvas.toDataURL('image/jpeg');
      const pdf = new jsPDF('p', 'mm', 'a4');
      const imgWidth = 210;
      const imgHeight = (canvas.height * imgWidth) / canvas.width;

      pdf.addImage(imgData, 'JPEG', 0, 20, imgWidth, imgHeight);
      pdf.save(namePdf);
    });
  }
}
