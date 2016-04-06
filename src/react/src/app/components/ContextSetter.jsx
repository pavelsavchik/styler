import React from 'react';

export default class ContextSetter extends React.Component {

    static propTypes = {
        productRestUrl: React.PropTypes.string,
        storeRestUrl: React.PropTypes.string,
        classificationRestUrl: React.PropTypes.string,
        history: React.PropTypes.object,
        user: React.PropTypes.object,
    }

    static childContextTypes = {
        productRestUrl: React.PropTypes.string,
        storeRestUrl: React.PropTypes.string,
        classificationRestUrl: React.PropTypes.string,
        history: React.PropTypes.object,
        user: React.PropTypes.object,
    }

    getChildContext(){
        return {
            productRestUrl: this.props.productRestUrl,
            storeRestUrl: this.props.storeRestUrl,
            classificationRestUrl: this.props.classificationRestUrl,
            history: this.props.history,
            user: this.props.user,
        }
    }

    render() {
        return this.props.children;
    }
}
