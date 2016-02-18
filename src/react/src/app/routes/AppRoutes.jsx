import React from 'react';
import {
  Route,
  Redirect,
  IndexRoute,
} from 'react-router';

// Here we define all our material-ui ReactComponents.
import Master from '../components/Master.jsx';
import Ad from '../components/Ad.jsx';
import Catalogue from '../components/Catalogue.jsx';
import Stores from '../components/Stores.jsx';


const AppRoutes = (
  <Route path="/" component={Master}>
    <IndexRoute component={Ad} />
    <Route path="home" component={Ad} />
    <Route path="catalogue" component={Catalogue} />
    <Route path="stores" component={Stores} />
  </Route>
);

export default AppRoutes;
