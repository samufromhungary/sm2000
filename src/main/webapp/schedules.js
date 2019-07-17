let schedulesTableEl;
let schedulesTableBodyEl;
let scheduleTitle;

function onScheduleClicked() {
    const scheduleTitle = this.dataset.scheduleTitle;
    
    const params = new URLSearchParams();
    params.append('title', scheduleTitle);
    
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/task?' + params.toString());
    xhr.send();
}

function addAllSchedules(schedules) {
    const selectEl = document.querySelector('#schedule-delete-form > select');
    
    removeAllChildren(selectEl);
    
    for (let i= 0; i < schedules.length; i++) {
        const schedule = schedules[i];
        
        const optionEl = document.createElement('option');
        optionEl.value = schedule.title;
        optionEl.textContent = `${schedule.id} - ${schedule.title} - ${schedule.days}`;
        
        selectEl.appendChild(optionEl);
    }
}
function onSchedulesLoad(schedules) {
    schedulesTableEl = document.getElementById('schedules');
    schedulesTableBodyEl = schedulesTableEl.querySelector('tbody');
    
    const selectEl = document.querySelector('#schedule-delete-form > select');
    
    removeAllChildren(selectEl);
    
    for (let i= 0; i < schedules.length; i++) {
        const schedule = schedules[i];
        
        const optionEl = document.createElement('option');
        optionEl.value = schedule.title;
        optionEl.textContent = `${schedule.id} - ${schedule.title} - ${schedule.days}`;
        
        selectEl.appendChild(optionEl);
    }
    
    appendSchedules(schedules);
}
function appendSchedule(schedule) {
    const idTdEl = document.createElement('td');
    idTdEl.textContent = schedule.id;

    const aEl = document.createElement('a');
    aEl.textContent = schedule.title;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.scheduleTitle = schedule.title;
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
        appendSchedule(schedule);
    }
}
function onScheduleAddResponse() {
   clearMessages();
    if (this.status === OK) {
        appendSchedule(JSON.parse(this.responseText));
        onSchedulesClicked();
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

function onScheduleDeleteClicked() {
    const scheduleDeleteForm = document.forms['schedule-delete-form'];
    
    const scheduleTitlesSelectEl = scheduleDeleteForm.querySelector('select[name="scheduleTitles"]');
    
    const params = new URLSearchParams();
    for (let i = 0; i < scheduleTitlesSelectEl.selectedOptions.length; i++) {
        params.append('selectedTitle', scheduleTitlesSelectEl.selectedOptions[i].value);
    }
    
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/deleteSchedule');
    xhr.send(params);
}

function onScheduleDeleteResponse() {
    clearMessages();
    if (this.status === OK) {
        onSchedulesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}
function onScheduleModifyClicked(){
    const scheduleFormEl = document.forms['modify-schedule-form']
    
    const modifyTitleInputEl = scheduleFormEl.querySelector('input[name="title"]');
    const newTitleInputEl = scheduleFormEl.querySelector('input[name="newTitle"]');
    const modifyDaysInputEl = scheduleFormEl.querySelector('input[name="days"]');

    const modifyTitle = modifyTitleInputEl.value;
    const newTitle = newTitleInputEl.value;
    const modifyDays = modifyDaysInputEl.value;

    const params = new URLSearchParams();
    params.append('title',modifyTitle);
    params.append('newTitle',newTitle);
    params.append('days',modifyDays);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/modifySchedule');
    xhr.send(params);
}

function onSchedulesResponse() {
    if (this.status === OK) {
        showContents(['schedules-content', 'back-to-profile-content', 'logout-content', 'schedule']);
        onSchedulesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}