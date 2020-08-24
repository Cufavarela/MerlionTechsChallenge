import React from 'react';
import Button from '@material-ui/core/Button';
import { getEntities, updateEntity } from '../../entities/sales/sales.reducer';
import { IRootState } from 'app/shared/reducers';
import { connect } from 'react-redux';
import { ISales } from 'app/shared/model/sales.model';
import { State } from 'app/shared/model/enumerations/state.model';

export interface IBotonProps extends IOwnProps, DispatchProps {}

function buttonToMove (props: IBotonProps) {
  const { sale, accion } = props;

  function cambiarEstado () {
    switch (sale.state) {
      case State.IN_CHARGE:
        props.updateEntity({...sale, state: State.SHIPPED});
        break;
      case State.SHIPPED:
        props.updateEntity({...sale, state: State.DELIVERED});
        break;
      case State.DELIVERED:
      default:
    }
  }
    if(sale.state !== State.DELIVERED || accion === "") {
      return <Button onClick={cambiarEstado} variant="contained" color="primary">{accion}</Button>;
    }
    return null;
}

interface IOwnProps {
  sale: Readonly<ISales>;
  accion?: string;
}

const mapStateToProps = (storeState: IRootState) => ({
  products: storeState.product.entities,
  salesEntity: storeState.sales.entity,
  loading: storeState.sales.loading,
  updating: storeState.sales.updating,
  updateSuccess: storeState.sales.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  updateEntity,
};

type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(buttonToMove);