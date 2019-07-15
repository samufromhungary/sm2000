let tasksTableEl;
let tasksTableBodyEl;
let taskTitle;

function onTaskClicked() {
    const taskId = this.dataset.taskId;

    const params = new URLSearchParams();
    params.append('id', taskId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/task?' + params.toString());
    xhr.send();
}

function onTaskAddResponse() {
   clearMessages();
    if (this.status === OK) {
        appendTask(JSON.parse(this.responseText));
        onTasksClicked();
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

function onTaskAddClicked() {
    const taskFormEl = document.forms['task-form'];

    const titleInputEl = taskFormEl.querySelector('input[name="title"]');
    const descriptionInputEl = taskFormEl.querySelector('input[name="description"]');

    const title = titleInputEl.value;
    const description = descriptionInputEl.value;

    const params = new URLSearchParams();
    params.append('title', title);
    params.append('description', description);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskAddResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/tasks');
    xhr.send(params);
}


function onTasksLoad(tasks) {
    tasksTableEl = document.getElementById('tasks');
    tasksTableBodyEl = tasksTableEl.querySelector('tbody');

    const selectEl = document.querySelector('#task-delete-form > select');
    
    removeAllChildren(selectEl);

    for (let i= 0; i < tasks.length; i++) {
        const task = tasks[i];
        
        const optionEl = document.createElement('option');
        optionEl.value = task.title;
        optionEl.textContent = `${task.id} - ${task.title} - ${task.description}`;

        selectEl.appendChild(optionEl);
    }
    
    appendTasks(tasks);
}
function onTaskDeleteClicked() {
    const taskDeleteForm = document.forms['task-delete-form'];
    
    const taskTitlesSelectEl = taskDeleteForm.querySelector('select[name="taskTitles"]');
    
    const params = new URLSearchParams();
    for (let i = 0; i < taskTitlesSelectEl.selectedOptions.length; i++) {
        params.append('selectedTitle', taskTitlesSelectEl.selectedOptions[i].value);
    }
    
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/deleteTask');
    xhr.send(params);
}

function addAllTasks(tasks) {
    const selectEl = document.querySelector('#task-delete-form > select');
    
    removeAllChildren(selectEl);
    
    for (let i= 0; i < tasks.length; i++) {
        const task = tasks[i];
        
        const optionEl = document.createElement('option');
        optionEl.value = task.title;
        optionEl.textContent = `${task.id} - ${task.title} - ${task.description}`;
        
        selectEl.appendChild(optionEl);
    }
}
function appendTask(task) {
    const idTdEl = document.createElement('td');
    idTdEl.textContent = task.id;

    const aEl = document.createElement('a');
    aEl.textContent = task.title;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.taskId = task.id;
    aEl.addEventListener('click', onTasksClicked);

    const titleTdEl = document.createElement('td');
    titleTdEl.appendChild(aEl);

    const descriptionTdEl = document.createElement('td');
    descriptionTdEl.textContent = task.description;

    const trEl = document.createElement('tr');
    trEl.appendChild(idTdEl);
    trEl.appendChild(titleTdEl);
    trEl.appendChild(descriptionTdEl);
    tasksTableBodyEl.appendChild(trEl);
}

function appendTasks(tasks) {
    removeAllChildren(tasksTableBodyEl);

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        appendTask(task);
    }
}
function onTaskDeleteResponse() {
    clearMessages();
    if (this.status === OK) {
        onTasksLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

function onTasksResponse() {
    if (this.status === OK) {
        showContents(['tasks-content', 'back-to-profile-content', 'logout-content']);
        onTasksLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}