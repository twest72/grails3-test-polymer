<%--
  Created by IntelliJ IDEA.
  User: westphal
  Date: 03.06.15
  Time: 11:28
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <asset:javascript src="application.js"/>
    <asset:javascript src="jquery"/>
    <asset:javascript src="spring-websocket"/>

    <title>Polymer Grails Test</title>
    <script>
        var csrfHeaderName = "${request._csrf.headerName}";
        var csrfToken = "${request._csrf.token}";
        var csrfHeader = {};
        csrfHeader[csrfHeaderName] = csrfToken;
        var siteUid = "${UUID.randomUUID().toString()}";
    </script>
    <!-- 1. Load webcomponents-lite.min.js for polyfill support. -->
    <script src="polymer/bower_components/webcomponentsjs/webcomponents-lite.min.js">
    </script>


    <!-- 2. Use an HTML Import to bring in some elements. -->
    <link rel="import" href="imports.html">

    <style>
    body {
        background: #fafafa;
        font-family: 'Roboto', 'Helvetica Neue', Helvetica, Arial, sans-serif;
        color: #333;
    }

    paper-material.panel {
        margin: 22px 18px;
        padding: 10px;
        background-color: #ffffff;
    }
    </style>
</head>

<body unresolved class="fullbleed">
<paper-header-panel mode="waterfall">
    <!-- Main Toolbar -->
    <paper-toolbar id="mainToolbar">

        <!-- Menu -->
        <paper-icon-button icon="menu" data-dialog="menu" onclick="Components.OverlayPanel.clickHandler(event.target.parentNode.parentNode);">
        </paper-icon-button>
        <overlay-menu id="menu">
        </overlay-menu>

        <div class="paper-font-display2">Polymer Grails Test</div>
        <span class="flex"></span>

        <!-- Toolbar icons -->
        <paper-icon-button icon="refresh"></paper-icon-button>
        <paper-icon-button icon="search"></paper-icon-button>
    </paper-toolbar>

    <paper-material class="panel" elevation="2">
        <my-greeting></my-greeting>
    </paper-material>

    <paper-material class="panel" elevation="2">
        <my-charts></my-charts>
    </paper-material>

    <paper-material class="panel" elevation="2">
        <contact-page></contact-page>
    </paper-material>

    <paper-material class="panel" elevation="2">
        <contact-socket-page></contact-socket-page>
    </paper-material>

    <paper-material class="panel" elevation="2">

        <paper-input id="nameField" label="Your name here"></paper-input>
        <paper-button tabindex="0" raised class="colorful">Say Hello</paper-button>

        <div id="greeting2"></div>
    </paper-material>

    <paper-material class="panel" elevation="2">
        <my-buttons></my-buttons>
    </paper-material>

    %{--<paper-material class="panel" elevation="2">--}%
        %{--<my-map></my-map>--}%
    %{--</paper-material>--}%
</paper-header-panel>

<script>
    // To ensure that elements are ready on polyfilled browsers,
    // wait for WebComponentsReady.
    document.addEventListener('WebComponentsReady', function () {

        moment.locale(window.navigator.language);

        var input = document.getElementById('nameField');
        var button = document.querySelector('paper-button');
        var greeting2 = document.getElementById("greeting2");
        button.addEventListener('click', function () {
            greeting2.textContent = 'Hello, ' + input.value;
        });

        var menu = document.getElementById("menu");
        menu.menuItems = [
            {'name': 'Inbox'},
            {'name': 'Starred'},
            {'name': 'Sent mail'},
            {'name': 'Drafts'},
            {'name': moment("2015-01-01")},
            {'name': 'Open Test'}
        ];
    });
</script>
</body>
</html>
