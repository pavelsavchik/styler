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
    store: null,
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
    if(!store){
      return null;
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
            <div className="container">
              <div className="row">
                <div className="col-lg-4 thumbnail">
                  <img src="http://lorempixel.com/480/300/fashion"/>
                </div>
                <div className="col-lg-3 col-lg-offset-1">
                  <div className="text-center">
                    <div className="row">
                      <h2 className="col-xs-12 title">
                        <p className="text-muted">{store.name}</p>
                        {store.description}
                      </h2>
                    </div>
                  </div>
                </div>
                <div className="col-lg-3 col-lg-offset-1">
                  <h3 className="text-muted">Магазины:</h3>
                    <strong>{store.name}</strong>
                    <br/>
                    {
                      store.address &&
                        <div>
                          {store.address.city} {store.address.street} {store.address.home}
                          {
                            store.address.phones.map(phone=>{
                              return(
                                <div>
                                  <br/>
                                  <span className="glyphicon glyphicon glyphicon-phone-alt" aria-hidden="true"></span>
                                  {phone.number}
                                </div>
                              )
                            })
                          }
                        </div>
                    }
                    <div className="clearfix"></div>
                    <hr/>
                </div>
              </div>
            </div>
          </Paper>
        </div>
      </div>
    );
  }
}
