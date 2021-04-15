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
            <#if numGames == 0>
                <p>There are no active game.</p>
            <#elseif numGames == 1>
                <p>There is one active game. Click a name to start spectating that player</p>
            <#else>
                <p>There are ${numGames} active games. Click a name to start spectating that player</p>
            </#if>
            <ol>
                <#list activeGames as game>
                    <#if !game.isGameDone>
                        <li><a href="/spectator/game?playerSpectated=${game.redPlayerName}">Game ${game.gameID}: ${game.redPlayerName}</a></li>
                        <li><a href="/spectator/game?playerSpectated=${game.whitePlayerName}">Game ${game.gameID}: ${game.whitePlayerName}</a></li>
                    </#if>
                </#list>
            </ol>
        <#else>
            <#if numGames == 0>
                <p>There are no active games.<p>
            <#elseif numGames == 1>
                <p>There is ${numGames} active game. Sign in to see it</p>
            <#else>
                <p>There are ${numGames} active games. Sign in to see them</p>
            </#if>
        </#if>

        <h2>Replay Games</h2>
                <#if savedGames??>
                    <#if numSaved == 0>
                        <p>There are no saved games.</p>
                    <#elseif numSaved == 1>
                        <p>There is one saved game. Click to start viewing that replay</p>
                    <#else>
                        <p>There are ${numGames} saved games. Click a name to start spectating that player</p>
                    </#if>
                    <ol>
                        <#list savedGames as game>
                        <li><a href="/replay/game?whitePlayer=${game.game.whitePlayer.name}"> vs . ${game.game.whitePlayer.name}</a></li>
                        </#list>
                    </ol>
                <#else>
                    <#if numGames == 0>
                        <p>There are no saved games.<p>
                    <#elseif numGames == 1>
                        <p>There is ${numGames} saved game. Sign in to see it</p>
                    <#else>
                        <p>There are ${numGames} saved games. Sign in to see them</p>
                    </#if>
                </#if>


    </div>

</div>
</body>

</html>
