import {
  Component, Input, HostListener, EventEmitter, Output, SimpleChanges, OnChanges,
  AfterViewInit, OnInit
} from '@angular/core';
import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-responsive';
import * as _ from 'lodash';
import { LocalStorageService } from "../../services/local-storage.service";
import { ConstantConfig } from "../../config/constant.config";



@Component({
  selector: 'dt-table',
  template: `
       <div class="custom-dt-table">
          <table [attr.id]="elemID" class="dt-table display responsive nowrap" cellspacing="0" width="100%"></table>
        </div>
    `
})
export class DTComponent implements OnChanges, AfterViewInit, OnInit {

  @Input() config;
  @Input() sortIndex;
  @Input() searchText;
  @Input() searchColIndex;
  @Input() reload;
  @Output() onMenuSelect: EventEmitter<any> = new EventEmitter();
  @Output() onRowClick: EventEmitter<any> = new EventEmitter();

  elemID = 'dt-' + Math.random().toString(36).slice(2);
  dt: any = null;
  COMMON_CONFIG: Object = {};

  //hiding tooltip on component leave
  @HostListener('mouseleave') mouseleave() {
    $('#' + this.elemID + ' .tooltipDel').hide();
    // let elem = document.querySelectorAll('#'+ this.elemID +' .tooltipDel')[0];
    // elem["style"].display = 'none';
  }

  //Constructor
  constructor(private localStorage: LocalStorageService) { }

  //on component init
  ngOnInit() {
    //$.fn.dataTable.ext.errMode = 'none';
    //$.fn.dataTableExt.sErrMode = "console";
    this.initConfig();
  }

  //when data changes
  ngOnChanges(changes: SimpleChanges) {

    let searchColIndex = changes['searchColIndex'];
    let searchTxt = changes['searchText'];
    let reloadDT = changes['reload'];
    if (searchTxt) {
      if (searchTxt['currentValue'] != searchTxt['previousValue']) {
        this.searchTable(this.searchColIndex, this.searchText);
      }
    }
    if (searchColIndex) {
      if (searchColIndex['currentValue'] != searchColIndex['previousValue']) {
        this.searchTable(this.searchColIndex, this.searchText);
      }
    }
    if (reloadDT) {
      setTimeout(this.reloadTable(), 500);
    }
  }
  //called after view is initialized
  ngAfterViewInit() {
    //hiding data table errors
    //$.fn.dataTable.ext.errMode = 'none';

    //attaching token
    let token_db = this.localStorage.get(ConstantConfig.AUTH_TOKEN);

    //overriding ajax json node
    if (!this.config.ajax) {
      _.extend(
        this.COMMON_CONFIG['ajax'],
        {
          beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + token_db);
          },
          url: this.config.apiURL
        }
      );
    }
    if (this.config.dataSrc != undefined) {
      _.extend(
        this.COMMON_CONFIG['ajax'],
        {
          dataSrc: this.config.dataSrc
        }
      );

    }

    //merging common options and user options
    let options: Object = _.extend(
      {},
      this.COMMON_CONFIG,
      this.config
    );
    this.initDT(options);


  }

  //init data table
  initDT(options: Object): void {
    if (this.dt) {
      this.dt.clear().destroy();
      this.dt = null;
      setTimeout(() => this.initDT(options), 0);
    } else {
      this.dt = $('#' + this.elemID).DataTable(options);
    }
  }

  //Search table
  searchTable(colIndex: Number, searchTxt: string): void {
    if (this.dt && colIndex > -1) {
      this.dt
        .columns()
        .search('')
        .columns(colIndex)
        .search(searchTxt)
        .draw();
    } else if (this.dt && !colIndex) {
      this.dt
        .search('')
        .search(searchTxt)
        .draw();
    }
  }

  //reload table
  reloadTable(): void {
    if (this.dt) {
      this.dt.ajax.reload();
    }
  }

  //init data Table common config
  initConfig() {
    let self = this;
    this.COMMON_CONFIG = {
      "processing": true,
      "serverSide": true,
      paginationType: 'full',
      displayLength: 10,
      dom: 'trpl',
      destroy: true,
      order: [],
      lengthMenu: [[10, 20, 25], [10, 20, 25]],
      "initComplete": function (settings, json) {
        var table = $('#' + self.elemID + "_wrapper .dt-table").DataTable();

        //adding divider between pagination and page length
        $('#' + self.elemID + "_wrapper .dataTables_paginate").after('<div class="vertical-divider"></div>');

        //dots functionality
        $('#' + self.elemID + "_wrapper .dt-table").on('click', '.dots', function () {
          $('#' + self.elemID + " .tooltipDel").hide();
          $(this).next('.tooltipDel').show();
        });

        //dots menu functionality
        $('#' + self.elemID + "_wrapper .dt-table").on('click', 'a', function () {
          self.onMenuClicked({ 'clickedOn': $(this).attr('data-name'), "value": $(this).attr('data-custom') });
          $('#' + self.elemID + " .tooltipDel").hide();
        });

        //dots menu functionality
        $('#' + self.elemID + "_wrapper .dt-table").on('click', 'tr', function () {
          self.onRowClicked({ "value": table.row(this).data() });
        });

        //sorting icon alignment hack
        var spanSorting = '<span class="arrow-hack sort">&nbsp;&nbsp;&nbsp;</span>',
          spanAsc = '<span class="arrow-hack asc">&nbsp;&nbsp;&nbsp;</span>',
          spanDesc = '<span class="arrow-hack desc">&nbsp;&nbsp;&nbsp;</span>';

        $('#' + self.elemID + "_wrapper .dt-table").off('click', 'th');
        $('#' + self.elemID + "_wrapper .dt-table").on('click', 'th', function () {
          setTimeout(function () {
            $('#' + self.elemID + "_wrapper .dt-table thead th").each(function (i, th) {
              $(th).find('.arrow-hack').remove();

              var html = $(th).html(),
                cls = $(th).attr('class');
              switch (cls) {
                case 'sorting_asc':
                  $(th).html(html + spanAsc); break;
                case 'sorting_desc':
                  $(th).html(html + spanDesc); break;
                default:
                  $(th).html(html + spanSorting); break;
              }
            });
          }, 50);
        });
       // $('#' + self.elemID + " th").eq(0).click().click();
      },
      "drawCallback": function (settings) {
        if (settings.aoData.length > 0) {
          var api = this.api();
          var pgNo = api.page.info();
          var currPg = pgNo.page;
          var totalPg = pgNo.pages;
          var paginationTxt;
          // set the label for page number details
          if (totalPg == 0) {
            paginationTxt = '<span class="cur-page">' + (currPg) + '</span> of ' + totalPg;
          } else {
            paginationTxt = '<span class="cur-page">' + (currPg + 1) + '</span> of ' + totalPg;
          }

          $('#' + self.elemID + "_wrapper .paginationTxt").remove();
          $('#' + self.elemID + "_wrapper .paginate_button.previous").after('<span class="paginationTxt">' + paginationTxt + '</span>');
        }
      },
      language: {
        "lengthMenu": "Show: _MENU_",
        "processing": '<img src="./assets/images/data-table/loader.gif"/>',
        "zeroRecords": "No data found",
        "paginate": {
          "first": '',
          "last": '',
          "next": '<span class="allICons left-arrow"></span>',
          "previous": '<span class="allICons right-arrow"></span>'
        }
      },
      "ajax": {
        type: "POST",
        contentType: "application/json",
        dataType: 'json',
        data: function (d) {
          return JSON.stringify(d);
        }
      }
    };
  }

  //on drop down menu event bind
  onMenuClicked(obj: any): void {
    this.onMenuSelect.emit(obj);
  }
  // on row clicked
  onRowClicked(obj: any): void {
    this.onRowClick.emit(obj);
  }

}
