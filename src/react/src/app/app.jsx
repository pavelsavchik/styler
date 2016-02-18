import React from 'react';
import ReactDOM from 'react-dom';
import {Router} from 'react-router';
import injectTapEventPlugin from 'react-tap-event-plugin';
import AppRoutes from './routes/AppRoutes.jsx'; // Our custom react component
import createHistory from 'history/lib/createHashHistory';

//Needed for onTouchTap
//Can go away when react 1.0 release
//Check this repo:
//https://github.com/zilverline/react-tap-event-plugin
injectTapEventPlugin();

// Render the main app react component into the app div.
// For more details see: https://facebook.github.io/react/docs/top-level-api.html#react.render
ReactDOM.render(
  <Router
    history={createHistory({queryKey: false})}
    onUpdate={() => window.scrollTo(0, 0)}
  >
    {AppRoutes}
  </Router>
  , document.getElementById('app'));
