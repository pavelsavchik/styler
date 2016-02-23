import React from 'react';

export default class ContextSetter extends React.Component {

    static propTypes = {
        productRestUrl: React.PropTypes.string,
        history: React.PropTypes.object,
    }

    static childContextTypes = {
        productRestUrl: React.PropTypes.string,
        history: React.PropTypes.object,
    }

    getChildContext(){
        return {
            productRestUrl: this.props.productRestUrl,
            history: this.props.history,
        }
    }

    render() {
        return this.props.children;
    }
}
