import React from 'react';
import { connect } from 'react-redux';
import TabScreen from './tabsScreen'



export type IMovementsProp = StateProps;

export const Movements = (props: IMovementsProp) => {
  const { account } = props;

  return (
      <div>
          <TabScreen />
      </div>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Movements);