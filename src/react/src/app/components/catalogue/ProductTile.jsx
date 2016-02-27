import React from 'react';
import Paper from 'material-ui/lib/paper';

const styles = {
  paper: {
    height: 240,
    width: 160,
    margin: 10,
    textAlign: 'center',
    display: 'inline-block',
  },
  p: {
    margin: 0,
  },
};

class Main extends React.Component {

  static contextTypes = {
    history: React.PropTypes.object,
  }

  static propTypes = {
    product: React.PropTypes.object,
  }

  onClick = () => {
    this.context.history.push(`/product/${this.props.product.id}`);
  }

  render() {
    let {product, num} = this.props;
    return (
      <Paper style={styles.paper}>
        <img src={"http://lorempixel.com/160/240/fashion/" + ( num % 11 ).toString()} onClick={this.onClick}/>
        <p style={styles.p} onClick={this.onClick}>{product.shortDesc}</p>
        <p style={styles.p} onClick={this.onClick}>$ {product.price.value}</p>
      </Paper>
    );
  }
}

export default Main;
