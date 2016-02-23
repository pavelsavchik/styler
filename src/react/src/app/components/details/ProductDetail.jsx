import React from 'react';
import axios from 'axios';
import Paper from 'material-ui/lib/paper';
import CircularProgress from 'material-ui/lib/circular-progress';

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
};

export default class ProductDetail extends React.Component {

  static contextTypes = {
    productRestUrl: React.PropTypes.string,
  }

  static propTypes = {
    product: React.PropTypes.object,
    params: React.PropTypes.object,
  }

  state = {
    product: {},
    isInfiniteLoading: false,
  }

  componentDidMount() {
    this.loadProduct();
  }

  loadProduct = () => {
    let that = this;
    this.setState({
      isInfiniteLoading: true,
    });
    axios.get(this.context.productRestUrl + "/" + this.props.params.productId)
      .then(function (response) {
        that.setState({
          isInfiniteLoading: false,
          product: response.data,
        });
      })
      .catch(function (response) {
        console.log(response);
      });
  }

  render() {
    if(this.state.isInfiniteLoading){
      return <CircularProgress />;
    }
    let {product} = this.state;
    return (
      <div style={styles.container}>
        <Paper>
          <img src="http://lorempixel.com/320/480/fashion" onClick={this.onClick}/>
        </Paper>


        <Paper>
          <p>{product.manufacturer}</p>
          <h2>{product.shortDesc}</h2>
          <p>{product.longDesc}</p>
          <p>{JSON.stringify(product.attributeValues)}</p>
          <h3>$ {product.price}</h3>
        </Paper>
      </div>
    );
  }
}
