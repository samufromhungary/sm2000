<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <c:url value="/index.js" var="indexScriptUrl"/>
    <c:url value="/login.js" var="loginScriptUrl"/>
    <c:url value="/register.js" var="registerScriptUrl"/>
    <c:url value="/profile.js" var="profileScriptUrl"/>
    <c:url value="/tasks.js" var="tasksScriptUrl"/>
    <c:url value="/schedules.js" var="schedulesScriptUrl"/>
    <c:url value="/schedule.js" var="scheduleScriptUrl"/>
    <c:url value="/back-to-profile.js" var="backToProfileScriptUrl"/>
    <c:url value="/logout.js" var="logoutScriptUrl"/>
    <c:url value="/googleAuth.js" var="googleAuthScriptUrl"/>
    <link rel="stylesheet" href="design.css">
    <script src="${indexScriptUrl}"></script>
    <script src="${loginScriptUrl}"></script>
    <script src="${registerScriptUrl}"></script>
    <script src="${profileScriptUrl}"></script>
    <script src="${tasksScriptUrl}"></script>
    <script src="${schedulesScriptUrl}"></script>
    <script src="${scheduleScriptUrl}"></script>
    <script src="${backToProfileScriptUrl}"></script>
    <script src="${logoutScriptUrl}"></script>
    <script src="${googleAuthScriptUrl}"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <title>Schedule Master 2000</title>
    <meta name="google-signin-client_id"
          content="354584955844-7q0r3acj2tjl0tp9o80o306rrikdtfmr.apps.googleusercontent.com">
</head>
<body>
<div class="g-signin2" data-onsuccess="onSignIn"></div>
<br><br>
<div>
    <br>
    <div id="logout-content" class="hidden content">
        <button id="logout-button" class="logoutbutton">Logout</button>
    </div>
    <br><br>
    <div id="back-to-profile-content" class="hidden content">
        <button onclick="onBackToProfileClicked();" class="backtoprofile">Back to profile</button>
    </div>
</div>
<div id="login-content" class="content" style="text-align: center">
    <h1>Welcome to Schedule Master 2000!</h1>
    <form id="login-form" onsubmit="return false;" class="login">
        <input type="text" name="email" placeholder=" email">
        <br><br>
        <input type="password" name="password" placeholder=" password">
        <br><br>
        <button id="login-button">Login</button>
    </form>
    <br>
    <button id="toggle-trigger" onclick="togglediv('register-content')">Sign up</button>
</div>
<br>
<div id="register-content" class="hidden content">
    <form id="register-form" onsubmit="return false;" class="register">
        <input type="text" name="username" placeholder=" username">
        <br><br>
        <input type="text" name="email" placeholder=" email">
        <br><br>
        <input type="password" name="password" placeholder=" password">
        <br><br>
        <button id="register-button" onclick="togglediv('register-content')">Create account</button>
    </form>
</div>
<br><br>
<div id="profile-content" class="hidden content">
    <h1>Profile</h1>
    <ul>
        <li class="sch"><a href="javascript:void(0);" onclick="onSchedulesClicked();">Schedules</a></li>
        <li class="tas"><a href="javascript:void(0);" onclick="onTasksClicked();">Tasks</a></li>
    </ul>
</div>
<br><br>
<div id="schedules-content" class="hidden content">
    <h1>Schedules</h1>
    <table id="schedules">
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
        <select name="scheduleTitles" class="selector">
        </select>
        <br>
        <br>
        <button onclick="onScheduleDeleteClicked();">Delete</button>
    </form>
</div>
<div id="schedule-content" class="hidden content">
    <h2>Task assigning</h2>
    <form id="schedule-task-form" onsubmit="return false;">
        <select name="tasks">
        </select>
        <input type="text" name="days" placeholder="Day">
        <input type="text" name="startDate" placeholder="Start Hour">
        <input type="text" name="endDate" placeholder="End Hour">
        <button onclick="onTaskAssignClicked();">Assign</button>
        <br><br>
    </form>
    <br>
</div>
<div id="tasks-content" class="hidden content">
    <h1>Tasks</h1>
    <table id="tasks">
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
        <input type="text" name="description" placeholder="Description">
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
        <select name="taskTitles" class="selector">
        </select>
        <br>
        <br>
        <button onclick="onTaskDeleteClicked();">Delete</button>
    </form>
</div>
<br>
<div class="hidden content" id="google-content">
    <p>Profile details</p>
    <img id="pic" class="img-circle" width="100" height="100"/>
    <p>Email address</p>
    <p id="email" class="alert alert-danger"></p>
    <button onclick="signOut()" class="btn btn-danger">Sign Out</button>
</div>
</body>
</html>
