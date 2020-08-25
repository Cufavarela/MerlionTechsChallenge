import React, { useState, useEffect } from 'react';
import { AppBar, Box, Tabs, Tab, makeStyles, Theme } from '@material-ui/core';
import InnerTab from './insideTab';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { getEntities } from '../../entities/sales/sales.reducer';

interface TabPanelProps {
  children?: React.ReactNode;
  index: any;
  value: any;
}


interface ListaProps extends StateProps, DispatchProps {}

const mapStateToProps = ({ sales }: IRootState) => ({
  salesList: sales.entities,
  updateSuccess: sales.updateSuccess
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;


function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3}>
          {children}
        </Box>
      )}
    </div>
  );
}

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
  },
  header: {
    backgroundColor: "#2A6A9E",
  },
}));

function SimpleTabs(props: ListaProps) {

  const { salesList } = props;
  const [salesListEncargado, setSalesListEncargado] = useState([]);
  const [salesListEnviado, setSalesListEnviado] = useState([]);
  const [salesListEntregado, setSalesListEntregado] = useState([]);

  function filterSales() {
    setSalesListEncargado(salesList.filter(sale => sale.state === 'IN_CHARGE'));
    setSalesListEnviado(salesList.filter(sale => sale.state === 'SHIPPED'));
    setSalesListEntregado(salesList.filter(sale => sale.state === 'DELIVERED'));
  }
  useEffect(() => {
    props.getEntities();
    filterSales();
  }, []);

  useEffect(() => {
    filterSales();
  }, [salesList]);


  const classes = useStyles();
  const [value, setValue] = React.useState(0);

  const handleChange = (event: React.ChangeEvent<{}>, newValue: number) => {
    setValue(newValue);
  };

  return (
    <div className={classes.root}>
      <AppBar position="static" className={classes.header}>
        <Tabs value={value} onChange={handleChange} aria-label="simple tabs example" TabIndicatorProps={{ style: { backgroundColor:"#fff", }}} >
          <Tab label="ENCARGADOS" disableRipple={true} className="sinFocus"/>
          <Tab label="ENVIADOS" disableRipple={true} className="sinFocus"/>
          <Tab label="ENTREGADOS" disableRipple={true} className="sinFocus"/>
        </Tabs>
      </AppBar>

      <TabPanel value={value} index={0}>
        <InnerTab boton="Enviar" lista={salesListEncargado} />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <InnerTab boton="Entregar" lista={salesListEnviado}/>
      </TabPanel>
      <TabPanel value={value} index={2}>
        <InnerTab lista={salesListEntregado}/>
      </TabPanel>
    </div>
  );
}

export default connect(mapStateToProps, mapDispatchToProps)(SimpleTabs);