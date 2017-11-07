import {
  Component, Input, HostListener, EventEmitter, Output, SimpleChanges, OnChanges,
  AfterViewInit, OnInit,OnDestroy
} from '@angular/core';
import { AmChartsService } from "@amcharts/amcharts3-angular";

@Component({
  selector: 'am-chart',
  template: `
          <div [attr.id]="elemID" [style.width.%]="100" [style.height.px]="ht"></div>
    `,
  styles: [`
      .amcharts-pie-slice {
          transform: scale(1);
          transform-origin: 50% 50%;
          transition-duration: 0.3s;
          transition: all .3s ease-out;
          -webkit-transition: all .3s ease-out;
          -moz-transition: all .3s ease-out;
          -o-transition: all .3s ease-out;
          cursor: pointer;
          box-shadow: 0 0 30px 0 #000;
        }

        .amcharts-pie-slice:hover {
          transform: scale(1.1);
          filter: url(#shadow);
        }
        a[href="http://www.amcharts.com/javascript-charts/"] {
            display: none!important;
        }
    `]
})
export class ChartComponent implements OnInit  ,AfterViewInit, OnDestroy{
  // @Input() data;
  @Input() ht;
  @Input() config;
  @Input() updatedData
  @Input() graphData
  private chart: any;
  elemID = 'dt-' + Math.random().toString(36).slice(2);
  constructor(private AmCharts: AmChartsService) {}

  ngOnInit() {
    //console.log(this.data);
    this.ht = this.ht || 400;
  }
  ngAfterViewInit(){
    this.chartInit(this.config);
  }

  ngOnDestroy() {
    this.AmCharts.destroyChart(this.chart);
  }

  chartInit(data:Array<Object>){
    if(this.chart){
      this.AmCharts.destroyChart(this.chart);
      this.chart = null;
    }

    this.chart = this.AmCharts.makeChart(this.elemID, this.config);

  }

  ngOnChanges(changes: SimpleChanges) {
    let updatedData = changes['updatedData'];
    let graphData = changes['graphData'];
    this.updateChart(updatedData, graphData);
  }

  updateChart(updatedData, graphData){
   if(this.chart){
      this.AmCharts.updateChart(this.chart, () => {
        // Change whatever properties you want, add event listeners, etc.
        this.chart.dataProvider = updatedData ? updatedData.currentValue : [];
        this.chart.graphs = graphData ? graphData.currentValue : [];
        this.chart.validateData();
      });
   }
  }
}
