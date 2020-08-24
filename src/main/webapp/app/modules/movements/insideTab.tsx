import React from 'react';
import { Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import BotonSales from './buttonToMove';

export interface IOwnProps {
  lista?: any;
  boton?: string;
}
export const InnerTab = (props: IOwnProps) => {
  return (
    <div className="table-responsive">
        <Table responsive>
          <thead>
            <tr>
              <th><Translate contentKey="global.field.id">ID</Translate></th>
              <th><Translate contentKey="prueba3App.sales.comprador">Comprador</Translate></th>
              <th><Translate contentKey="prueba3App.sales.fecha">Fecha</Translate></th>
              <th><Translate contentKey="prueba3App.sales.pagado">Pagado</Translate></th>
              <th><Translate contentKey="prueba3App.sales.precio">Precio</Translate></th>
              <th><Translate contentKey="prueba3App.sales.product">Producto</Translate></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {
              props.lista.map(sale => (
                <tr key={sale.id}>
                  <td>{sale.id}</td>
                  <td>{sale.comprador}</td>
                  <td>{sale.fecha}</td>
                  <td>{sale.product ? sale.product.name : ""}</td>
                  <td>{sale.pagado}</td>
                  <td>{sale.precio}</td>
                  <td><BotonSales accion={props.boton} sale={sale} /></td>
                </tr>
              ))
            }
          </tbody>
        </Table>
    </div>
  );
};

export default InnerTab;
