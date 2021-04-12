<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="4">
    <title>Web Checkers | ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

    <h1>Web Checkers | ${title}</h1>

    <!-- Provide a navigation bar -->
    <#include "nav-bar.ftl" />

    <div class="body">

        <!-- Provide a message to the user, if supplied. -->
        <#include "message.ftl" />

        <!-- TODO: future content on the Home:
                to start games,
                spectating active games,
                or replay archived games
        -->
        <h2>Players online</h2>
        <#if activePlayers??>
            <#if numPlayers == 2>
                <p>There is 1 other player online</p>
            <#else>
                <p>There are ${numPlayers - 1} other players online</p>
            </#if>
            <ol>
                <#list activePlayers as player>
                    <#if currentUser != player>
                        <li><a href="/game?whitePlayer=${player.name}">${player.name}</a></li>
                    </#if>
                </#list>
            </ol>
        <#else>
            <#if numPlayers == 1>
                <p>There is 1 other player online. Sign in to see</p>
            <#else>
                <p>There are ${numPlayers} other players online. Sign in to see them</p>
            </#if>
        </#if>

        <h2>Active Games</h2>
        <#if activeGames??>
            <#if numGames == 1>
                <p>There is ${numGames} active game.</p>
            <#else>
                <p>There are ${numGames} active games.</p>
            </#if>
        <#else>
            <p>There are no active games.</p>
        </#if>


    </div>

</div>
</body>

</html>
