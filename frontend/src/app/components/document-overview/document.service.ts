import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";
import { DocumentOverviewScreenComponent } from './document-overview-screen/document-overview-screen.component';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  overview : DocumentOverviewScreenComponent;

  constructor(private http : HttpClient) { 
    
  }

  public postDocument(file : File, filename : string) {
    let data = new FormData();
    data.append("file", file);
    this.http.post<Response>("/api/document/?filename=" + filename, data).subscribe(
      data => {
        alert("Het bestand is succesvol opgeslagen");
      },
      error => {
        alert("Iets is fout gegaan");
      }
    );
  }

  public getDocument(filename : String) {
    this.http.get("/api/document/?filename=" + filename, {responseType: 'arraybuffer'}).subscribe(
      data => {
        var file = new Blob([data], {type: 'application/pdf'});
        var fileURL = URL.createObjectURL(file);
        this.overview.setDocument(fileURL);
      }
    )
  }

  public setOverview(DocumentOverviewScreenComponent) {
    this.overview = DocumentOverviewScreenComponent;
  }
}
