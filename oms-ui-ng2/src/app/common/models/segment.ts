
export class Segment {
  id: number;
  segmentName: string;
  isSelected:boolean;

  constructor(id, segmentName,isSelected) {
    this.id = id;
    this.segmentName = segmentName;
    this.isSelected=isSelected;
  }
}