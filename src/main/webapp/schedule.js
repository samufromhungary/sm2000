function onScheduleResponse() {
    if (this.status === OK) {
        const schedule = JSON.parse(this.responseText);
        createScheduleTableDisplay(schedule);
        showContents(['schedule-content', 'back-to-profile-content', 'logout-content',]);

    } else {
        onOtherResponse(scheduleContentDivEl, this);
    }
  }
  
  function createScheduleTableDisplay(schedule) {
    if (schedule.length === 0) {
        removeAllChildren(myActivitiesDivEl);
        const pEl = document.createElement('p');
        pEl.setAttribute('id', 'schedule-info');
        pEl.textContent = 'The activity log is empty';
        scheduleContentDivEl.appendChild(pEl);
    } else {
        removeAllChildren(scheduleContentDivEl);
        const tableEl = document.createElement('table');
        const theadEl = createScheduleTableHeader(schedule);
        const tbodyEl = createScheduleTableBody(schedule);
        tableEl.appendChild(theadEl);
        tableEl.appendChild(tbodyEl);
        scheduleContentDivEl.appendChild(tableEl);
    }
  }
  
function createScheduleTableBody(schedule) {
    const tbodyEl = document.createElement('tbody');
  
    for(let i = 0; i < 25; i++){

        const eventNameTdEl = document.createElement('td');
        eventNameTdEl.classList.add('default-cell');
        eventNameTdEl.textContent = i;
        
        const trEl = document.createElement('tr');
        trEl.appendChild(eventNameTdEl);

        for(let m = 0; m < schedule.days; m++){
        
            const tableNameTdEl = document.createElement('td');
            tableNameTdEl.classList.add('default-cell');
            tableNameTdEl.textContent = ' ';
            trEl.appendChild(tableNameTdEl);
        }
        
        tbodyEl.appendChild(trEl);
    }
        
    return tbodyEl;
  }
  
  function createScheduleTableHeader(schedule) {
    const trEl = document.createElement('tr');

    const eventNameThEl = document.createElement('th');
    eventNameThEl.classList.add('default-th');``
    eventNameThEl.textContent = 'Hours';
    
    trEl.appendChild(eventNameThEl);
    
    for(let i = 1; i < schedule.days + 1; i++){
        const tableNameThEl = document.createElement('th');
        tableNameThEl.classList.add('default-th');
        tableNameThEl.textContent = 'Days ' + i;

        trEl.appendChild(tableNameThEl);
    }

    const theadEl = document.createElement('thead');
    theadEl.appendChild(trEl);
    return theadEl;
}
  
  
function onScheduleLoad() {
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'schedule-table');
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    removeAllChildren(scheduleContentDivEl);
    scheduleContentDivEl.appendChild(tableEl);
}