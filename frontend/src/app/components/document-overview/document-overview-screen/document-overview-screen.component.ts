import { Component, OnInit } from '@angular/core'; 
import { DocumentService } from '../document.service';
 
@Component({ 
  selector: 'app-document-overview-screen', 
  templateUrl: './document-overview-screen.component.html', 
  styleUrls: ['./document-overview-screen.component.css'] 
}) 
export class DocumentOverviewScreenComponent implements OnInit { 
 
  documentName : string = "modulehandleiding" 
  readonly MODULEINSTRUCTIONS : string = "modulehandleiding";
  readonly MODULEGUIDE : string = "modulewijzer";
  file : String = "";
  
  constructor(private service : DocumentService) { 
    this.service.setOverview(this);
    this.showInstructions();
  } 
 
  ngOnInit() { 
  } 
 
  public showInstructions() { 
    this.service.getDocument(this.MODULEINSTRUCTIONS);
  } 
 
  public showGuide() { 
    this.service.getDocument(this.MODULEGUIDE);
  } 

  public setDocument(file : String) {
    this.file = file;
  }
 
} 