import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { MaterialModule } from "@angular/material";

import { ConfirmDialogComponent } from "./confirm/confirm.component";
import { ConfirmReviewDialogComponent } from "./confirmReview/confirm.component";
import { AlertDialogComponent } from "./alertbox/alert.dialog.component";
import { ProposalCommentDialog } from "./proposal-comment/proposal-comment.component";


@NgModule({
  imports: [FormsModule, CommonModule, MaterialModule],
  declarations: [
    ConfirmDialogComponent,
    ConfirmReviewDialogComponent,
    AlertDialogComponent,
    ProposalCommentDialog

  ],
  exports: [ConfirmDialogComponent, ConfirmReviewDialogComponent, AlertDialogComponent, ProposalCommentDialog],
  entryComponents: [
    ConfirmDialogComponent,
    ConfirmReviewDialogComponent,
    AlertDialogComponent,
    ProposalCommentDialog
  ]

})
export class DialogModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: DialogModule
    }
  }
}
