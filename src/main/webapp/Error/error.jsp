<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="/cruise-company/Error/error.css">
        <meta charset="utf-8">
        <title></title>
    </head>
    <body>
    <img src="/cruise-company/Error/sinking.png" alt="ship" width="1200" height="1000" class="ship"/>
        <div class="error">
            <div class="code">
                <span>${requestScope['javax.servlet.error.status_code']}</span>
            </div>
            <div class="message">
                ${requestScope['javax.servlet.error.message']}
            </div>

        </div>
        <div class="button">
            <a href="/cruise-company/index" class="option">
                Home page
            </a>
        </div>
    </body>
</html>
