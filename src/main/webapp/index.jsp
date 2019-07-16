<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:url value="/style.css" var="styleUrl"/>
        <c:url value="/index.js" var="indexScriptUrl"/>
        <c:url value="/login.js" var="loginScriptUrl"/>
        <c:url value="/register.js" var="registerScriptUrl"/>
        <c:url value="/profile.js" var="profileScriptUrl"/>
        <c:url value="/tasks.js" var="tasksScriptUrl"/>
        <c:url value="/schedules.js" var="schedulesScriptUrl"/>
        <c:url value="/schedule.js" var="scheduleScriptUrl"/>
        <c:url value="/back-to-profile.js" var="backToProfileScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <link rel="stylesheet" type="text/css" href="${styleUrl}">
        <link rel="stylesheet" href="tablecss.css">
        <script src="${indexScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${profileScriptUrl}"></script>
        <script src="${tasksScriptUrl}"></script>
        <script src="${schedulesScriptUrl}"></script>
        <script src="${scheduleScriptUrl}"></script>
        <script src="${backToProfileScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <title>App</title>
    </head>
<body>
<div id="login-content" class="content" style="text-align: center">
    <h1>Login</h1>
    <form id="login-form" onsubmit="return false;" class="login">
        <input type="text" name="email" placeholder="email">
        <br>
        <input type="password" name="password" placeholder="password">
        <br>
        <br>
        <button id="login-button">Login</button>
    </form>
    <br>
    <button id="toggle-trigger" onclick="togglediv('register-content')">Create an account</button>
</div>
<div id="register-content" class="hidden content">
    <h1>Register</h1>
    <form id="register-form" onsubmit="return false;" class="register">
        <input type="text" name="username" placeholder="username">
        <br>
        <input type="text" name="email" placeholder="email">
        <br>
        <input type="password" name="password" placeholder="password">
        <br>
        <button id="register-button" onclick="togglediv('register-content')">Register</button>
    </form>
</div>
<div id="profile-content" class="hidden content">
    <h1>Profile</h1>
    <p>Current user: <span id="user-email"></span></p>
    <ul>
        <li><a href="javascript:void(0);" onclick="onSchedulesClicked();">Schedules</a></li>
        <li><a href="javascript:void(0);" onclick="onTasksClicked();">Tasks</a></li>
    </ul>
</div>
<div id="schedules-content" class="hidden content">
    <h1>Schedules</h1>
    <table id="schedules" align="center">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Days</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <br>
    <h2>Add new schedule</h2>
    <form id="schedule-form" onsubmit="return false;">
        <input type="text" name="title" placeholder="Title">
        <input type="text" name="days" placeholder="Days">
        <button onclick="onScheduleAddClicked();">Add</button>
    </form>
    <h2>Modify schedule</h2>
    <form id="modify-schedule-form" onsubmit="return false;">
        <input type="text" name="title" placeholder="Title">
        <input type="text" name="newTitle" placeholder="New Title">
        <input type="text" name="days" placeholder="Days">
        <button onclick="onScheduleModifyClicked();">Modify</button>
    </form>
    <h2>Delete schedule</h2>
    <form id="schedule-delete-form" onsubmit="return false">
        <select name="scheduleTitles" multiple>
        </select>
        <button onclick="onScheduleDeleteClicked();">Delete</button>
    </form>
</div>
<div id="schedule-content" class="hidden content">
    <h1>Schedule</h1>
    <p>ID: <span id="schedule-id"></span></p>
    <p>Title: <span id="schedule-title"></span></p>
    <p>Days: <span id="schedule-days"></span></p>

</div>
<div id="tasks-content" class="hidden content">
    <h1>Tasks</h1>
    <table id="tasks" align="center">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <h2>Add new task</h2>
    <form id="task-form" onsubmit="return false;">
        <input type="text" name="title" placeholder="Title">
        <input type="text" name="description">
        <button onclick="onTaskAddClicked();">Add</button>
    </form>
    <h2>Modify task</h2>
    <form id=modify-task-form onsubmit="return false;">
        <input type="text" name="title" placeholder="Title">
        <input type="text" name="newTitle" placeholder="New Title">
        <input type="text" name="description" placeholder="Description">
        <button onclick="onTaskModifyClicked();">Modify</button>
    </form>
    <h2>Delete from tasks</h2>
    <form id="task-delete-form" onsubmit="return false">
        <select name="taskTitles" multiple>
        </select>
        <button onclick="onTaskDeleteClicked();">Delete</button>
    </form>
</div><div id="schedule" class="hidden content">
    <h1>Schedule</h1>
    <table class="bigtable" style="text-align:center;" align="center">
        <thead>
        <tr>
            <td>Hours</td>
            <td>Day1</td>
            <td>Day2</td>
            <td>Day3</td>
            <td>Day4</td>
            <td>Day5</td>
            <td>Day6</td>
            <td>Day7</td>
        </tr>
        </thead>
    </table>
</div>
<br>
<div id="back-to-profile-content" class="hidden content">
    <button onclick="onBackToProfileClicked();">Back to profile</button>
</div>
<br>
<div id="logout-content" class="hidden content">
    <button id="logout-button">Logout</button>
</div>
</body>
</html>
