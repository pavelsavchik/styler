import { combineReducers} from 'redux';
import { routerStateReducer} from 'redux-router';
import user from './user';

const reducer = combineReducers({
  router: routerStateReducer,
  user: user,
  //app: rootReducer, //you can combine all your other reducers under a single namespace like so
});

export default reducer;