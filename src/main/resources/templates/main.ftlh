<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>
        <@l.logout />
    </div>
    <div>
        <form method="post" action="/add">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="text" name="text" placeholder="Введите сообщение"/>
            <input type="text" name="tag" placeholder="Тэг"/>
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список сообщений</div>
    <form method="get" action="/main">
        <input type="text" name="filter" placeholder="${filter}"/>
        <button type="submit">Найти</button>
    </form>
     <#list messages as item>
    <div>
        <b>${item.id}</b>
        <span>${item.text}</span>
        <i>${item.tag}</i>
        <strong>${item.user}</strong>
    </div>
    <#else> No message
    </#list>
</@c.page>