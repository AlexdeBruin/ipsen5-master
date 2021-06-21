import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-student-manual',
  templateUrl: './student-manual.component.html',
  styleUrls: ['./student-manual.component.css']
})
export class StudentManualComponent implements OnInit {

  constructor() { }


  public accolade = "{";
  public accolade2 = "}";

  ngOnInit() {
  }


  public scrollTo(id){
    let element = document.getElementById(id);
    element.scrollIntoView();
  }
}
