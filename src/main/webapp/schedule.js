let scheduleTasksTableEl;
let scheduleTasksTableBodyEl;

function onScheduleResponse() {
    if (this.status === OK) {
        showContents(['schedule-content', 'back-to-profile-content', 'logout-content',]);
        const data = JSON.parse(this.responseText);
        const schedule = data[0];
        const tasks = data[1];
        const coordinated = data[2];
        createScheduleTableDisplay(schedule,coordinated,tasks);
        onScheduleTaskLoad(tasks);
    } else {
        onOtherResponse(scheduleContentDivEl, this);
    }
}
function displayTaskInSchedule() {

}

function createScheduleTableBody(schedule,coordinated,tasks) {
    const tbodyEl = document.createElement('tbody');
    tbodyEl.setAttribute('id', 'tableBody');

    for (let i = 1; i < 25; i++) {

        const eventNameTdEl = document.createElement('td');
        eventNameTdEl.classList.add('default-cell');
        eventNameTdEl.textContent = i;

        const trEl = document.createElement('tr');
        trEl.appendChild(eventNameTdEl);

        for (let m = 1; m <= schedule.days; m++) {

            const tableNameTdEl = document.createElement('td');
            tableNameTdEl.classList.add('default-cell');
            if(coordinated.day == m) {
                if(coordinated.start_date == i || coordinated.end_date == i){
                    const dateNum = coordinated.end_date - coordinated.start_date;
                    for(let j = 0; j < dateNum; j++ ){
                        tableNameTdEl.textContent = coordinated.taskId;
                    }
                }
            }
            else{
                tableNameTdEl.textContent = ' ';
            }
            trEl.appendChild(tableNameTdEl);
        }

        tbodyEl.appendChild(trEl);
    }

    return tbodyEl;
}

function createScheduleTableHeader(schedule) {
    const trEl = document.createElement('tr');
    
    const eventNameThEl = document.createElement('th');
    eventNameThEl.setAttribute('id', 'tableHead');
    eventNameThEl.classList.add('default-th'); ``
    eventNameThEl.textContent = 'Hours';

    trEl.appendChild(eventNameThEl);

    for (let i = 1; i <= schedule.days; i++) {
        const tableNameThEl = document.createElement('th');
        tableNameThEl.classList.add('default-th');
        tableNameThEl.textContent = 'Days ' + i;

        trEl.appendChild(tableNameThEl);
    }

    const theadEl = document.createElement('thead');
    theadEl.appendChild(trEl);
    return theadEl;
}
function createScheduleTableDisplay(schedule,coordinated,tasks) {
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'table');
    const theadEl = createScheduleTableHeader(schedule);
    const tbodyEl = createScheduleTableBody(schedule,coordinated,tasks);
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    scheduleContentDivEl.appendChild(tableEl);
}
function onTaskAssignClicked() {
    const scheduleTaskFormEl = document.forms['schedule-task-form']

    const taskTitlesSelectEl = scheduleTaskFormEl.querySelector('select[name="tasks"]');


    const daysInputEl = scheduleTaskFormEl.querySelector('input[name="days"]');
    const startDateInputEl = scheduleTaskFormEl.querySelector('input[name="startDate"]');
    const endDateInputEl = scheduleTaskFormEl.querySelector('input[name="endDate"]');

    const days = daysInputEl.value;
    const startDate = startDateInputEl.value;
    const endDate = endDateInputEl.value;

    const params = new URLSearchParams();

    for (let i = 0; i < taskTitlesSelectEl.selectedOptions.length; i++) {
        const tasksId = taskTitlesSelectEl.selectedOptions[i].value;
        params.append('tasksId', tasksId);
    }
    params.append('days', days);
    params.append('startDate', startDate);
    params.append('endDate', endDate);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskAssignResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/schedule');
    xhr.send(params);

    return tasksId;
}

function onTaskAssignResponse() {
    clearMessages();
    if (this.status === OK) {

        onScheduleClicked();
    } else {
        onOtherResponse(scheduleContentDivEl, this);
    }
}

function onScheduleTaskLoad(tasks) {
    scheduleTasksTableEl = document.getElementById('tasks');
    scheduleTasksTableBodyEl = scheduleTasksTableEl.querySelector('tbody');

    const selectEl = document.querySelector('#schedule-task-form > select');

    removeAllChildren(selectEl);

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];

        const optionEl = document.createElement('option');
        optionEl.value = task.id;
        optionEl.textContent = `${task.id} - ${task.title} - ${task.description}`;

        selectEl.appendChild(optionEl);
    }
    // appendTasks(tasks);
}