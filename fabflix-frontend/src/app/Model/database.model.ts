import { Table } from './table.model';

export class Database {
  tables: string[] = [];
  movieDescription: Table[] = [];
  starsDescription: Table[] = [];
  starsInMoviesDescription: Table[] = [];
  genresDescription: Table[] = [];
  genresInMoviesDescription: Table[] = [];
  customersDescription: Table[] = [];
  salesDescription: Table[] = [];
  ratingsDescription: Table[] = [];
  creditCardsDescription: Table[] = [];
  employeesDescription: Table[] = [];
}
