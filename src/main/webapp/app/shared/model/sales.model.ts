import { IProduct } from 'app/shared/model/product.model';
import { State } from 'app/shared/model/enumerations/state.model';

export interface ISales {
  id?: number;
  state?: State;
  comprador?: string;
  fecha?: string;
  pagado?: string;
  precio?: string;
  product?: IProduct;
}

export const defaultValue: Readonly<ISales> = {};
