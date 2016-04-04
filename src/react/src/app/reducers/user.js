import { UPDATE_USER } from '../actions/user'

const initialState = null;

export  default function user(state = initialState, action) {
  switch (action.type) {
    case UPDATE_USER:
      return Object.assign({}, state, action.user);
    default:
      return state;
  }
}