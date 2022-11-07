import { Component, OnInit } from '@angular/core';
import { Database } from 'src/app/Model/database.model';
import { EmployeeService } from 'src/app/Services/employee.service';

@Component({
  selector: 'app-database',
  templateUrl: './database.component.html',
  styleUrls: ['./database.component.css'],
})
export class DatabaseComponent implements OnInit {
  constructor(private employeeService: EmployeeService) {}
  DatabaseMetadata: Database = new Database();
  ngOnInit(): void {
    this.employeeService.getDatabaseMetadata().subscribe((res) => {
      this.DatabaseMetadata = res;
      console.log(res);
    });
  }
}
