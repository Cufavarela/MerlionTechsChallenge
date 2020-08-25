import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="home.title">Welcome</Translate>
        </h2>
        <p className="lead">
          <Translate contentKey="home.subtitle">This is challenge was made by <b>Facundo Varela</b></Translate>
        </p>
        {account && account.login ? (
          <>
          <div className="loginSuccessWrapper">
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
          <div>
            <p className="">
              <Translate contentKey="home.link.description">Click on the <b>Movements</b> button to find the challenge window.</Translate>
            </p>
            <p>
              <Translate contentKey="home.like">If you like it, fell free to send an </Translate>{' '}
              <a href="mailto:cufa.varela@gmail.com" target="_blank" rel="noopener noreferrer">
                Email
              </a>
              📧
            </p>
          </div>
          </>
        ) : (
          <div>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>
              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Translate>
            </Alert>

            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </div>
        )}
      </Col>
      <Col md="4" className="pad logoMerlionWrapper">
        {/* <span className="hipster rounded" /> */}
        <img className="logoMerlion" src="../../../content/images/LogoMerlion.png"></img>
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
