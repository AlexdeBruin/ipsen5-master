import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentOverviewScreenComponent } from './document-overview-screen/document-overview-screen.component';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { SharedModule } from '../shared/shared.module';
import { DocumentService } from './document.service';
import { FormsModule }        from '@angular/forms';
import { AddDocumentComponent } from './add-document/add-document.component';

@NgModule({
  imports: [
    CommonModule,
    PdfViewerModule,
    SharedModule,
    FormsModule
  ],
  declarations: [DocumentOverviewScreenComponent, AddDocumentComponent],
  providers: [DocumentService],
  exports: [AddDocumentComponent]
})
export class DocumentOverviewModule { }
