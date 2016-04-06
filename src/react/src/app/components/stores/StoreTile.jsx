import React from 'react';
import Paper from 'material-ui/lib/paper';

const styles = {
  paper: {
    height: 180,
    width: 220,
    margin: 10,
    textAlign: 'center',
    display: 'inline-block',
  },
  p: {
    margin: 0,
  },
};

class StoreTile extends React.Component {

  static contextTypes = {
    history: React.PropTypes.object,
  }

  static propTypes = {
    store: React.PropTypes.object,
  }

  onClick = () => {
    this.context.history.push(`/store/${this.props.store.id}`);
  }

  render() {
    let {store, num} = this.props;
    return (
      <Paper style={styles.paper}>
        <img src={"http://lorempixel.com/220/180/fashion/" + ( num % 11 ).toString()} onClick={this.onClick}/>
        <p style={styles.p} onClick={this.onClick}>{store.name}</p>
      </Paper>
    );
  }
}

export default StoreTile;
