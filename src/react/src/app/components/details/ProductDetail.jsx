import React from 'react';
import axios from 'axios';
import Paper from 'material-ui/lib/paper';
import CircularProgress from 'material-ui/lib/circular-progress';
import RaisedButton from 'material-ui/lib/raised-button';
import FontIcon from 'material-ui/lib/font-icon';

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
    productRestUrl: React.PropTypes.string,
    history: React.PropTypes.object,
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

  handleBack = () => {
    this.context.history.goBack();
  }

  render() {
    if (this.state.isInfiniteLoading) {
      return (
        <div style={styles.container}>
          <CircularProgress />
        </div>
      )
    }
    let {product} = this.state;

    if (!product.price) {
      return null
    }
    return (
      <div>
        <RaisedButton
          label="Назад"
          style={styles.button}
          onTouchTap={this.handleBack}
        />
        <div style={styles.container}>
          <Paper style={{padding: "20px"}}>
            <div className="row">
              <div className="col-lg-5 col-md-12 col-sm-12">
                <div className="row">
                  <div className="sp-wrap">
                    <img src="http://lorempixel.com/320/480/fashion"/>
                  </div>
                </div>
              </div>
              <div className="col-lg-4 col-md-12 col-sm-12">
                <div className="text-center">
                  <div className="row">
                    <h2 className="col-xs-12 title">
                      <p className="text-muted">{product.manufacturer}</p>
                      {product.shortDesc}
                    </h2>
                    <div className="clearfix"></div>
                    <hr/>
                    <p className="text-muted text-center">{product.longDesc}</p>
                    <div className="clearfix"></div>
                    <hr/>

                    <div className="col-xs-12">
                      { product.attributeValues && product.attributeValues.map(attributeValue => {
                        return (
                          <div>
                            <div className="col-xs-6 text-right">
                              {attributeValue.attribute.description}
                            </div>
                            <div className="col-xs-6 text-left">
                              {attributeValue.value}
                            </div>
                          </div>
                        )
                      })
                      }
                    </div>
                    <div className="clearfix"></div>
                    <hr/>
                    <h3 className="col-xs-12">
                      {product.price.value} $
                    </h3>
                    <div className="col-xs-12">
                    <span className="label label-default">
                      Доступно
                    </span>
                    </div>
                    <div className="clearfix"></div>
                    <hr/>
                    <div>
                      <RaisedButton
                        label="Нравится"
                        primary={true}
                        onTouchTap={this.handleTouchTap}
                        icon={<FontIcon className="material-icons">thumb_up</FontIcon>}
                        style={{marginRight: "10px", marginBottom: "10px"}}
                      />
                      <RaisedButton
                        label="Узнать о наличии"
                        primary={true}
                        onTouchTap={this.handleTouchTap}
                        icon={<FontIcon className="material-icons">shopping_cart</FontIcon>}
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </Paper>
        </div>
      </div>
    );
  }
}
