import { Component, Input, OnInit } from '@angular/core';
import { Table } from 'src/app/Model/table.model';

@Component({
  selector: 'app-table-description',
  templateUrl: './table-description.component.html',
  styleUrls: ['./table-description.component.css'],
})
export class TableDescriptionComponent implements OnInit {
  @Input() TableData: Table[] = [];
  constructor() {}

  ngOnInit(): void {}
}
