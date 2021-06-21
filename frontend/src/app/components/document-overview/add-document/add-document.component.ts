import { Component, OnInit } from '@angular/core';
import { DocumentService } from '../document.service';

@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.css']
})
export class AddDocumentComponent implements OnInit {

  moduleInstructions: File = null;
  moduleGuide : File = null;
  readonly MODULEINSTRUCTIONS : string = "modulehandleiding";
  readonly MODULEGUIDE : string = "modulewijzer";
  
  constructor(private documentService : DocumentService) { }

  ngOnInit() {
  }

  public handleFileInputModuleInstructions(files: FileList) {
    this.moduleInstructions = files.item(0);
  } 
  
  public moduleInstructionsIsValid() : boolean {     
    return this.moduleInstructions != null;
  }

  public uploadModuleInstructions() {
    this.documentService.postDocument(this.moduleInstructions, this.MODULEINSTRUCTIONS);
  }

  public handleFileInputModuleGuide(files: FileList) {
    this.moduleGuide = files.item(0);
  } 

  public moduleGuideIsValid() : boolean {
    return this.moduleGuide != null;
  }

  public uploadModuleGuide() {
    this.documentService.postDocument(this.moduleGuide, this.MODULEGUIDE);
  }
}
