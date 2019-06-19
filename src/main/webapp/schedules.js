let schedulesTableEl;
let schedulesTableBodyEl;

function onScheduleClicked() {
    const scheduleId = this.dataset.scheduleId;

    const params = new URLSearchParams();
    params.append('id', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule?' + params.toString());
    xhr.send();
}

function onScheduleAddResponse() {
   clearMessages();
    if (this.status === OK) {
        appendSchedule(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function onScheduleAddClicked() {
    const scheduleFormEl = document.forms['schedule-form'];

    const titleInputEl = scheduleFormEl.querySelector('input[name="title"]');
    const daysInputEl = scheduleFormEl.querySelector('input[name="days"]');

    const title = titleInputEl.value;
    const days = daysInputEl.value;

    const params = new URLSearchParams();
    params.append('title', title);
    params.append('days', days);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleAddResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/schedules');
    xhr.send(params);
}

function appendSchedule(schedule) {
    const idTdEl = document.createElement('td');
    idTdEl.textContent = schedule.id;

    const aEl = document.createElement('a');
    aEl.textContent = schedule.title;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.scheduleId = schedule.id;
    aEl.addEventListener('click', onScheduleClicked);

    const titleTdEl = document.createElement('td');
    titleTdEl.appendChild(aEl);

    const daysTdEl = document.createElement('td');
    daysTdEl.textContent = schedule.days;

    const trEl = document.createElement('tr');
    trEl.appendChild(idTdEl);
    trEl.appendChild(titleTdEl);
    trEl.appendChild(daysTdEl);
    schedulesTableBodyEl.appendChild(trEl);
}

function appendSchedules(schedules) {
    removeAllChildren(schedulesTableBodyEl);

    for (let i = 0; i < schedules.length; i++) {
        const schedule = schedules[i];
        appendSchedules(schedule);
    }
}

function onSchedulesLoad(schedules) {
    schedulesTableEl = document.getElementById('schedules');
    schedulesTableBodyEl = schedulesTableEl.querySelector('tbody');

    appendSchedules(schedules);

    // const scheduleTitleSpandEl = document.getElementById('schedule-title');
    // const scheduleDaysSpanEl = document.getElementById('schedule-days');

    // scheduleTitleSpandEl.textContent = schedule.title;
    // scheduleDaysSpanEl.textContent = schedule.days;
}

function onSchedulesResponse() {
    if (this.status === OK) {
        showContents(['schedules-content', 'back-to-profile-content', 'logout-content']);
        onSchedulesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}