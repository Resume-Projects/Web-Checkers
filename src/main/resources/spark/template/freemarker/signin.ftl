<!DOCTYPE html>
<form action="/signin" method="POST">
    <#if errorMessage??>
        <p>${errorMessage}</p>
    </#if>
    <label>
        <input type="text" name="username">
    </label>
    <button type="submit">Ok</button>
</form>