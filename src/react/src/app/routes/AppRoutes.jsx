import React from 'react';
import {
  Route,
  Redirect,
  IndexRoute,
} from 'react-router';

// Here we define all our material-ui ReactComponents.
import Master from '../components/Master.jsx';
import News from '../components/News.jsx';
import Catalogue from '../components/Catalogue.jsx';
import Stores from '../components/Stores.jsx';
import ProductDetails from '../components/details/ProductDetail.jsx';
import StoreDetails from '../components/details/StoreDetail.jsx';


const AppRoutes = (
  <Route path="/" component={Master}>
    <IndexRoute component={News} />
    <Route path="news" component={News} />
    <Route path="catalogue" component={Catalogue} />
    <Route path="stores" component={Stores} />
    <Route path="product/:productId" component={ProductDetails} />
    <Route path="store/:storeId" component={StoreDetails} />
  </Route>
);

export default AppRoutes;
