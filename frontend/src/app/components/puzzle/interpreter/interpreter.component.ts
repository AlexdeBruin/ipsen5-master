import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-interpreter',
  styleUrls: ['./interpreter.component.css'],
  templateUrl: './interpreter.component.html',

	})

export class InterpreterComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  text:string = "Hier moet je gaan typen";
  options:any = {maxLines: 1000, printMargin: true, fontFamily:"Monospace", height: 350};

  onChange(code) {
  }
}
