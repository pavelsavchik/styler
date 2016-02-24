import React from 'react';
import { compose, createStore } from 'redux';
import { reduxReactRouter} from 'redux-router';
import createHistory from 'history/lib/createHashHistory';
import routes from '../routes/AppRoutes.jsx';
import reducer from '../reducers/index';

const store = compose(
  reduxReactRouter({
    routes,
    createHistory,
  })
)(createStore)(reducer);

export default store;