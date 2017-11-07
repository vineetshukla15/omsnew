import { Component, Input, OnInit, Inject } from "@angular/core";
import { MdDialogRef } from "@angular/material";
import { objectSharingService } from '../../../services/objectSharingService';
import {MD_DIALOG_DATA} from '@angular/material';

@Component({
    selector: 'proposal-comment-dialog',
    templateUrl: './proposal-comment.component.html',
    styleUrls: ['./proposal-comment.component.scss'],
})
export class ProposalCommentDialog implements OnInit {

    proposalComment: string;

    constructor(public dialogRef: MdDialogRef<ProposalCommentDialog>, public getSetService: objectSharingService, @Inject(MD_DIALOG_DATA) public data: any) { }

    ngOnInit() {
        this.proposalComment = ""
    }

    resetForm() {
        this.proposalComment = "";
    }

    saveForm(data: any) {

        if (data != undefined && this.proposalComment!="") {
            this.getSetService.setValue(this.proposalComment);
            this.dialogRef.close();
        }
    }

    close(){
        this.dialogRef.close();
    }
}