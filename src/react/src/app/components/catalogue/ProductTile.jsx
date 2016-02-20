import React from 'react';
import Paper from 'material-ui/lib/paper';

const style = {
  height: 240,
  width: 160,
  margin: 10,
  textAlign: 'center',
  display: 'inline-block',
};

class Main extends React.Component {

  static propTypes = {
    product: React.PropTypes.object,
  }

  render() {
    return (
      <Paper style={style}>
        <img src="http://lorempixel.com/160/240/fashion"/>
        $ {this.props.product.price.toString()}
      </Paper>
    );
  }
}

export default Main;
