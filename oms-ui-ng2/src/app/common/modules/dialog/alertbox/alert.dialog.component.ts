import { Component } from "@angular/core";
import { MdDialogRef } from "@angular/material";


@Component({
  selector: 'alert-dialog',
  templateUrl: './alert.dialog.component.html',
  styles: [`
    .ok-btn{
      margin: auto;
    }
  `]
})
export class AlertDialogComponent {

  constructor(public dialogRef: MdDialogRef<AlertDialogComponent>) { }

}
