import React from 'react';

import List from 'material-ui/lib/lists/list';
import ListItem from 'material-ui/lib/lists/list-item';
import Divider from 'material-ui/lib/divider';
import Avatar from 'material-ui/lib/avatar';
import Colors from 'material-ui/lib/styles/colors';
import IconButton from 'material-ui/lib/icon-button';
import MoreVertIcon from 'material-ui/lib/svg-icons/navigation/more-vert';
import IconMenu from 'material-ui/lib/menus/icon-menu';
import MenuItem from 'material-ui/lib/menus/menu-item';

const iconButtonElement = (
  <IconButton
    touch={true}
    tooltipPosition="bottom-left"
  >
    <MoreVertIcon color={Colors.grey400} />
  </IconButton>
);

const rightIconMenu = (
  <IconMenu iconButtonElement={iconButtonElement}>
    <MenuItem>Ответить</MenuItem>
    <MenuItem>Переслать</MenuItem>
    <MenuItem>Удалить</MenuItem>
  </IconMenu>
);

const ListExampleMessages = () => (
  <div>

      <List subheader="Сегодня">
        <ListItem
          leftAvatar={<Avatar src="http://lorempixel.com/36/36/people/1" />}
          rightIconButton={rightIconMenu}
          primaryText="Brendan Lim"
          secondaryText={
            <p>
              <span style={{color: Colors.darkBlack}}>Brunch this weekend?</span><br/>
              I&apos;ll be in your neighborhood doing errands this weekend. Do you want to grab brunch?
            </p>
          }
          secondaryTextLines={2}
        />
        <Divider inset={true} />
        <ListItem
          leftAvatar={<Avatar src="http://lorempixel.com/36/36/people/2" />}
          rightIconButton={rightIconMenu}
          primaryText="me, Scott, Jennifer"
          secondaryText={
            <p>
              <span style={{color: Colors.darkBlack}}>Summer BBQ</span><br/>
              Wish I could come, but I&apos;m out of town this weekend.
            </p>
          }
          secondaryTextLines={2}
        />
        <Divider inset={true} />
        <ListItem
          leftAvatar={<Avatar src="http://lorempixel.com/36/36/people/3" />}
          rightIconButton={rightIconMenu}
          primaryText="Grace Ng"
          secondaryText={
            <p>
              <span style={{color: Colors.darkBlack}}>Oui oui</span><br/>
              Do you have any Paris recs? Have you ever been?
            </p>
          }
          secondaryTextLines={2}
        />
        <Divider inset={true} />
        <ListItem
          leftAvatar={<Avatar src="http://lorempixel.com/36/36/people/4" />}
          rightIconButton={rightIconMenu}
          primaryText="Kerem Suer"
          secondaryText={
            <p>
              <span style={{color: Colors.darkBlack}}>Birthday gift</span><br/>
              Do you have any ideas what we can get Heidi for her birthday? How about a pony?
            </p>
          }
          secondaryTextLines={2}
        />
        <Divider inset={true} />
        <ListItem
          leftAvatar={<Avatar src="http://lorempixel.com/36/36/people/5" />}
          rightIconButton={rightIconMenu}
          primaryText="Raquel Parrado"
          secondaryText={
            <p>
              <span style={{color: Colors.darkBlack}}>Recipe to try</span><br/>
              We should eat this: grated squash. Corn and tomatillo tacos.
            </p>
          }
          secondaryTextLines={2}
        />
      </List>

  </div>
);

export default ListExampleMessages;
