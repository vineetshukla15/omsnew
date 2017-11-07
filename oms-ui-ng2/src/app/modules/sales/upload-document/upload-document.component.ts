import {Component,Input,OnInit} from "@angular/core";
import {MdDialogRef} from "@angular/material";
import { UploadDocument } from '../../../common/models/uploadDocument';
import {objectSharingService} from '../../../common//services/objectSharingService';

@Component({
  selector: 'upload-document-dialog',
  templateUrl: './upload-document.component.html',
  styleUrls: ['./upload-document.component.scss'],
})
export class UploadDocumentDialog implements OnInit{
  uploadObject = new UploadDocument();
  constructor(public dialogRef: MdDialogRef<UploadDocumentDialog>,public getSetService:objectSharingService){}
  ngOnInit() {

  }

  fileChange(event) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
      this.uploadObject.file.push(file);  
    }
  }
   formSaved(){
      this.getSetService.setValue(this.uploadObject); 
      this.dialogRef.close();  
   } 
}
