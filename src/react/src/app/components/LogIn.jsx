import React from 'react';
import { connect } from 'react-redux';
import { pushState } from 'redux-router';
import axios from 'axios';
import Paper from 'material-ui/lib/paper';
import CircularProgress from 'material-ui/lib/circular-progress';
import RaisedButton from 'material-ui/lib/raised-button';
import TextField from 'material-ui/lib/text-field';
import UserAction from '../actions/user';

const styles = {
  paper: {
    height: 240,
    width: 160,
    margin: 10,
    paddingBottom: 40,
    textAlign: 'center',
    display: 'inline-block',
  },
  container: {
    textAlign: 'center',
  },
};

export default class LogIn extends React.Component {

  static contextTypes = {
    history: React.PropTypes.object,
  }

  handleBack = () => {
    this.context.history.goBack();
  }

  render() {
    return (
      <div>
        <div style={styles.container}>
          <Paper>
            <TextField
              hintText="Логин"
              floatingLabelText="Логин"
            /><br/>
            <TextField
              hintText="Пароль"
              floatingLabelText="Пароль"
              type="password"
            />
          </Paper>
        </div>
      </div>
    );
  }
}

export default connect(
  state => (
  {
    query: state.router.location.query,
    user: state.user,
  }
  ),
  Object.assign({ pushState }, UserAction)
)(LogIn);