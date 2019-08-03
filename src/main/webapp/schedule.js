let scheduleTasksTableEl;
let scheduleTasksTableBodyEl;

function onScheduleResponse() {
    if (this.status === OK) {
        showContents(['schedule-content', 'back-to-profile-content', 'logout-content',]);
        const data = JSON.parse(this.responseText);
        const schedule = data[0];
        const tasks = data[1];
        const coordinated = data[2];
        createScheduleTableDisplay(schedule, coordinated);
        onScheduleTaskLoad(tasks);
    } else {
        onOtherResponse(scheduleContentDivEl, this);
    }
}

function createScheduleTableBody(schedule, coordinated) {
    const tbodyEl = document.createElement('tbody');

    for (let i = 1; i < 25; i++) {

        const hoursTdEl = document.createElement('td');
        hoursTdEl.classList.add('default-cell');
        hoursTdEl.textContent = i;

        const trEl = document.createElement('tr');
        trEl.setAttribute('id', 'trEl');
        trEl.appendChild(hoursTdEl);

        for (let m = 0; m < schedule.days; m++) {

            const daysTdEl = document.createElement('td');
            daysTdEl.classList.add('default-cell');
            daysTdEl.setAttribute('id', 'coordinatedTd');
            trEl.appendChild(daysTdEl);
        }
        tbodyEl.appendChild(trEl);
    }
    return tbodyEl;
}


function createScheduleTableHeader(schedule) {
    const trEl = document.createElement('tr');

    const hoursThEl = document.createElement('th');
    hoursThEl.classList.add('default-th');
    ``
    hoursThEl.textContent = 'Hours';

    trEl.appendChild(hoursThEl);

    for (let i = 1; i <= schedule.days; i++) {
        const daysThEl = document.createElement('th');
        daysThEl.classList.add('default-th');
        daysThEl.textContent = 'Days ' + i;

        trEl.appendChild(daysThEl);
    }
    const theadEl = document.createElement('thead');
    theadEl.appendChild(trEl);
    return theadEl;
}

function createScheduleTableDisplay(schedule, coordinated) {
    const tableEl = document.createElement('table');
    const theadEl = createScheduleTableHeader(schedule);
    tableEl.appendChild(theadEl);

    try {
        if (coordinated.scheduleId === schedule.id) {
            const tbodyEl = document.createElement('tbody');

            for (let i = 1; i < 25; i++) {

                const hoursTdEl = document.createElement('td');
                hoursTdEl.classList.add('default-cell');
                hoursTdEl.textContent = i;

                const trEl = document.createElement('tr');
                trEl.setAttribute('id', 'trEl');
                trEl.appendChild(hoursTdEl);

                for (let m = 1; m <= schedule.days; m++) {

                    const daysTdEl = document.createElement('td');
                    daysTdEl.classList.add('default-cell');
                    daysTdEl.setAttribute('id', 'coordinatedTd');

                    if (coordinated.day === m) {
                        if (!(coordinated.start_date > i || coordinated.end_date < i)) {
                            daysTdEl.textContent = coordinated.taskId;
                        }
                    } else {
                        daysTdEl.textContent = ' ';
                    }
                    trEl.appendChild(daysTdEl);
                }
                tbodyEl.appendChild(trEl);
            }
            tableEl.appendChild(tbodyEl);

        }
    } catch (TypeError) {
        const tbodyEl = createScheduleTableBody(schedule, coordinated);
        tableEl.appendChild(tbodyEl);
    }
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
        onSchedulesClicked()
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
}