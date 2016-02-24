import { combineReducers} from 'redux';
import { routerStateReducer} from 'redux-router';

const reducer = combineReducers({
  router: routerStateReducer,
  //app: rootReducer, //you can combine all your other reducers under a single namespace like so
});

export default reducer;