import React from 'react';
import ProductList from './catalogue/ProductList.jsx';

const styles = {
  container: {
    textAlign: 'center',
  },
};


export default class Catalogue extends React.Component {

  render() {
    return (
      <div style={styles.container}>
        <ProductList/>
      </div>
    );
  }
}
