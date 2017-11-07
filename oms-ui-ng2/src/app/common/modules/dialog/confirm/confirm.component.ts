import { Component } from "@angular/core";
import { MdDialogRef } from "@angular/material";


@Component({
  selector: 'confirm-dialog',
  templateUrl: './confirm.component.html',
  styles: [`
    .ok-btn{
      margin-left: 15px;
    }
  `]
})
export class ConfirmDialogComponent {

  constructor(public dialogRef: MdDialogRef<ConfirmDialogComponent>) { }

}
