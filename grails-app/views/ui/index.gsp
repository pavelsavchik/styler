<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html class="no-js" lang="">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Satch</title>
  <meta name="description" content="Google's material design UI components built with React.">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <asset:stylesheet src="satch-react-ui.css"/>
</head>

<body style="margin: 0px;">
<div id="app"></div>

<!-- This script adds the Roboto font to our project. For more detail go to this site:  http://www.google.com/fonts#UsePlace:use/Collection:Roboto:400,300,500 -->
<script>
  var WebFontConfig = {
    google: { families: [ 'Roboto:400,300,500:latin' ] }
  };
  (function() {
    var wf = document.createElement('script');
    wf.src = ('https:' == document.location.protocol ? 'https' : 'http') +
        '://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js';
    wf.type = 'text/javascript';
    wf.async = 'true';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(wf, s);
  })();
</script>
<asset:javascript src="satch-react-ui.bundle.js"/>
<script>
  var user = null;
  <g:set var="user" value="${applicationContext.springSecurityService.currentUser}"/>
  <g:if test="${user}">
  user = {
    userName: '${user.username}'
  };
  </g:if>
  window.SatchUi.default.renderSatchUi(document.getElementById('app'), {
    productRestUrl: '${g.createLink(absolute: true, uri:'/products')}',
    storeRestUrl: '${g.createLink(absolute: true, uri:'/stores')}',
    classificationRestUrl: '${g.createLink(absolute: true, uri:'/classifications')}',
    user: user
  });
</script>
</body>

</html>