import React from 'react';
import {
  Route,
  Redirect,
  IndexRoute,
} from 'react-router';

import Master from '../components/Master.jsx';
import News from '../components/News.jsx';
import Catalogue from '../components/Catalogue.jsx';
import Stores from '../components/Stores.jsx';
import ProductDetails from '../components/details/ProductDetail.jsx';
import StoreDetails from '../components/details/StoreDetail.jsx';
import LogIn from '../components/LogIn.jsx';


const AppRoutes = (
  <Route path="/" component={Master}>
    <IndexRoute component={News} />
    <Route path="news" component={News} />
    <Route path="catalogue" component={Catalogue} />
    <Route path="stores" component={Stores} />
    <Route path="product/:productId" component={ProductDetails} />
    <Route path="store/:storeId" component={StoreDetails} />
    <Route path="login" component={LogIn}/>
  </Route>
);

export default AppRoutes;
