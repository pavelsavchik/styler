import React from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import { pushState } from 'redux-router';
import Waypoint from 'react-waypoint';

import CircularProgress from 'material-ui/lib/circular-progress';
import StoreTile from './StoreTile.jsx';

const styles = {
  container: {
    textAlign: 'center',
  },
};

class StoreList extends React.Component {

  static contextTypes = {
    storeRestUrl: React.PropTypes.string,
  }

  state = {
    stores: [],
    isInfiniteLoading: false,
  }

  componentWillReceiveProps(nextProps){
    if(JSON.stringify(this.props.query) !== JSON.stringify(nextProps.query)){
      this.setState({stores:[]});
      this.handleInfiniteLoad();
    }
  }

  handleInfiniteLoad = () => {
    let {query} = this.props;
    let that = this;
    this.setState({
      isInfiniteLoading: true,
    });
    axios.get(this.context.storeRestUrl, Object.assign({offset: this.state.stores.length, max: 20}, query))
      .then(function (response) {
        that.setState({
          isInfiniteLoading: false,
          stores: that.state.stores.concat(response.data),
        });
      })
      .catch(function (response) {
        console.log(response);
      });
  }

  elementInfiniteLoad = () => {
    if(this.state.isInfiniteLoading) {
      return <CircularProgress />;
    } else {
      return null;
    }
  }

  render () {
    return (
      <div>
        {this.state.stores.map((store,i) => {
          return <StoreTile key={i} num={i} store={store}/>
        })}
        {this.elementInfiniteLoad()}
        <Waypoint onEnter={this.handleInfiniteLoad}/>
      </div>
    );
  }
}

export default connect(
  state => ({ query: state.router.location.query }),
  { pushState }
)(StoreList);