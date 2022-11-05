import { Genre } from './genre.model';
import { Star } from './star.model';

export class Movie {
  id: string = '';
  rating: number = 0;
  genres: Genre[] = [];
  stars: Star[] = [];
  director: string = '';
  title: string = '';
  year: number = 0;
}
