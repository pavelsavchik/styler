import React from 'react';
import axios from 'axios';
import Paper from 'material-ui/lib/paper';
import CircularProgress from 'material-ui/lib/circular-progress';
import RaisedButton from 'material-ui/lib/raised-button';

const styles = {
  paper: {
    height: 240,
    width: 160,
    margin: 10,
    textAlign: 'center',
    display: 'inline-block',
  },
  container: {
    textAlign: 'center',
  },
  button: {
    margin: 12,
    marginLeft: 0,
  },
};

export default class ProductDetails extends React.Component {

  static contextTypes = {
    storeRestUrl: React.PropTypes.string,
    history: React.PropTypes.object,
  }

  static propTypes = {
    store: React.PropTypes.object,
    params: React.PropTypes.object,
  }

  state = {
    store: {},
    isInfiniteLoading: false,
  }

  componentDidMount() {
    this.loadStore();
  }

  loadStore = () => {
    let that = this;
    this.setState({
      isInfiniteLoading: true,
    });
    axios.get(this.context.storeRestUrl + "/" + this.props.params.storeId)
      .then(function (response) {
        that.setState({
          isInfiniteLoading: false,
          store: response.data,
        });
      })
      .catch(function (response) {
        console.log(response);
      });
  }

  handleBack = () => {
    this.context.history.goBack();
  }

  render() {
    if(this.state.isInfiniteLoading){
      return (
        <div style={styles.container}>
          <CircularProgress />
        </div>
      )
    }
    let {store} = this.state;
    return (
      <div>
        <RaisedButton
          label="Back"
          style={styles.button}
          onTouchTap={this.handleBack}
        />
        <div style={styles.container}>
          <Paper>
            <img src="http://lorempixel.com/480/300/fashion"/>
            <h2>{store.name}</h2>
            <p>{store.description}</p>
            <p>{JSON.stringify(store.address)}</p>
          </Paper>
        </div>
      </div>
    );
  }
}
