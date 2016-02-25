import React from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import { pushState } from 'redux-router';
import Infinite from 'react-infinite';

import CircularProgress from 'material-ui/lib/circular-progress';
import ProductTile from './ProductTile.jsx';

const styles = {
  container: {
    textAlign: 'center',
  },
};

class ProductList extends React.Component {

  static contextTypes = {
    productRestUrl: React.PropTypes.string,
  }

  state = {
    products: [],
    isInfiniteLoading: false,
  }

  componentWillReceiveProps(nextProps){
    if(JSON.stringify(this.props.query) !== JSON.stringify(nextProps.query)){
      this.setState({products:[]});
      this.handleInfiniteLoad();
    }
  }

  handleInfiniteLoad = () => {
    let {query} = this.props;
    let that = this;
    this.setState({
      isInfiniteLoading: true,
    });
    axios.get(this.context.productRestUrl, Object.assign({offset: this.state.products.length, max: 20}, query))
      .then(function (response) {
        that.setState({
          isInfiniteLoading: false,
          products: that.state.products.concat(response.data),
        });
      })
      .catch(function (response) {
        console.log(response);
      });
  }

  elementInfiniteLoad = () => {
    return <CircularProgress />;
  }

  render () {
    return <Infinite elementHeight={50}
                     infiniteLoadBeginEdgeOffset={200}
                     useWindowAsScrollContainer={true}
                     onInfiniteLoad={this.handleInfiniteLoad}
                     loadingSpinnerDelegate={this.elementInfiniteLoad()}
                     isInfiniteLoading={this.state.isInfiniteLoading}
    >
      {this.state.products.map((product,i) => {
        return <ProductTile key={i} num={i} product={product}/>
      })}
    </Infinite>;
  }
}

export default connect(
  state => ({ query: state.router.location.query }),
  { pushState }
)(ProductList);