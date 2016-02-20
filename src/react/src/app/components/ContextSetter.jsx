import React from 'react';

export default class ContextSetter extends React.Component {

    static propTypes = {
        productRestUrl: React.PropTypes.string,
    }

    static childContextTypes = {
        productRestUrl: React.PropTypes.string,
    }

    getChildContext(){
        return {
            productRestUrl: this.props.productRestUrl,
        }
    }

    render() {
        return this.props.children;
    }
}
