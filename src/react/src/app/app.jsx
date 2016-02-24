import React from 'react';
import ReactDOM from 'react-dom';
import {Router} from 'react-router';
import injectTapEventPlugin from 'react-tap-event-plugin';
import AppRoutes from './routes/AppRoutes.jsx'; // Our custom react component
import ContextSetter from './components/ContextSetter.jsx';
import store from './store/index';
import { ReduxRouter } from 'redux-router';
import { Provider } from 'react-redux'

//Needed for onTouchTap
//Can go away when react 1.0 release
//Check this repo:
//https://github.com/zilverline/react-tap-event-plugin
injectTapEventPlugin();

// Render the main app react component into the app div.
// For more details see: https://facebook.github.io/react/docs/top-level-api.html#react.render
function renderSatchUi(element, props) {
  //let history = createHistory({queryKey: false});
  window.store = store;
  ReactDOM.render(
      <Provider store={store}>
        <ContextSetter {...props} history={store.history}>
          <ReduxRouter />
        </ContextSetter>
      </Provider>
    , element);
}

export default {
  renderSatchUi: renderSatchUi,
};