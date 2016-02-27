import React from 'react';
import StoreList from './stores/StoreList.jsx';

const styles = {
  container: {
    textAlign: 'center',
  },
};


export default class Stores extends React.Component {

  render() {
    return (
      <div style={styles.container}>
        <StoreList/>
      </div>
    );
  }
}