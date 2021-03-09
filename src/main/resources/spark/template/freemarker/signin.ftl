<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Web Checkers | ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>Web Checkers | ${title}</h1>

    <#include "nav-bar.ftl">

    <h2>Sign In: </h2>

    <form action="/signin" method="POST">
        <#include "message.ftl">
        <br>Your name: <label><input type="text" name="username"></label>
        <button type="submit">Ok</button>
    </form>
    <br>
</div>
</body>