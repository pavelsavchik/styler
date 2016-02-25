import React from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import { pushState } from 'redux-router';
import SwipeableViews from 'react-swipeable-views';
import CircularProgress from 'material-ui/lib/circular-progress';
import ListItem from 'material-ui/lib/lists/list-item';
import List from 'material-ui/lib/lists/list';
import {SelectableContainerEnhance} from 'material-ui/lib/hoc/selectable-enhance';
import _ from "underscore";

const SelectableList = SelectableContainerEnhance(List);

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
  },
};

class ClassificationFilter extends React.Component {

  static contextTypes = {
    classificationRestUrl: React.PropTypes.string,
    history: React.PropTypes.object,
  }

  static propTypes = {
    classificationTree: React.PropTypes.array,
    params: React.PropTypes.object,
  }

  state = {
    classificationTree: [],
    isInfiniteLoading: false,
    slideIndex: 0,
  }

  componentDidMount() {
    this.loadClassifications();
  }

  loadClassifications = () => {
    let that = this;
    this.setState({
      isInfiniteLoading: true,
    });
    axios.get(this.context.classificationRestUrl)
      .then(response => {
        console.log(response);
        that.setState({
          isInfiniteLoading: false,
          classificationTree: response.data,
        });
      })
      .catch(response => {
        console.log(response);
      });
  }

  onRequestChange = (event, params) => {
    let {query, pushState} = this.props;
    pushState(null, '/catalogue', Object.assign({}, query, params));
  }

  handleBack = () => {
    this.context.history.goBack();
  }

  renderSexItems = () => {
    return(
      <SelectableList
        valueLink={{value: this.props.query, requestChange: this.onRequestChange}}
      >
        <ListItem primaryText="Men" value={{sex: 'men'}} />
        <ListItem primaryText="Women" value={{sex: 'women'}} />
      </SelectableList>
    )
  }

  renderClassificationItems = () => {
    let {classificationTree} = this.state;
    let { query : { sex }} = this.props;
    return(
      <SelectableList
        valueLink={{value: this.props.query, requestChange: this.onRequestChange}}
      >
        <ListItem primaryText="Back" value={{sex: null, classification: null}} />
        {classificationTree && classificationTree.map( cls => {
          return <ListItem
            primaryText={cls.description}
            value={{classification: cls.classificationId}}
          />
        })}
      </SelectableList>
    )
  }

  renderClassificationGroupItems = () => {
    let {classificationTree} = this.state;
    let { query : {
      sex,
      classification,
      classificationGroup = null,
      }} = this.props;

    let cls = _.findWhere(classificationTree, {classificationId: classification});
    let clsGrps = cls.classificationGroups;
    if(clsGrps.length === 0) this.renderClassificationItems();
    return(
      <SelectableList
        valueLink={{value: this.props.query, requestChange: this.onRequestChange}}
      >
        <ListItem primaryText="Back" value={{sex: sex, classification: null, classificationGroup: null}} />
        {clsGrps.map( clsGrp => {
          return <ListItem
            primaryText={clsGrp.description}
            value={{classificationGroup: clsGrp.classificationGroupId}}
          />
        })}
      </SelectableList>
    )
  }

  render() {
    if(this.state.isInfiniteLoading){
      return (
        <div style={styles.container}>
          <CircularProgress />;
        </div>
      )
    }
    let { query : {
        sex,
        classification,
        classificationGroup,
      }} = this.props;
    if(classificationGroup || classification) return this.renderClassificationGroupItems()
    else if(sex) return this.renderClassificationItems()
    else return this.renderSexItems()
  }
}

export default connect(
  state => ({ query: state.router.location.query }),
  { pushState }
)(ClassificationFilter);