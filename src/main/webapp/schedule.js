
// function createScheduleDisplay(schedule) {
//     if (schedule.length === 0) {
//       removeAllChildren(scheduleContentDivEl);
//       const pEl = document.createElement('p');
//       pEl.setAttribute('id', 'schedule-info');
//       pEl.textContent = 'The schedule log is empty';
//       scheduleContentDivEl.appendChild(pEl);
//     } else {
//       removeAllChildren(scheduleContentDivEl);
//       const tableEl = document.createElement('table');
//       const theadEl = createScheduleTableHeader();
//       tableEl.appendChild(theadEl);
//       scheduleContentDivEl.appendChild(tableEl);
//     }
//   }
function createScheduleTableBody(schedule) {
    const tbodyEl = document.createElement('tbody');
  
    for (let i = 0; i < schedule.length; i++) {
      const schedulee = schedule[i];
  
      const scheduleTitleTdEl = document.createElement('td');
      scheduleTitleTdEl.classList.add('default-cell');
      scheduleTitleTdEl.textContent = schedulee.title;

      const trEl = document.createElement('tr');
      trEl.appendChild(scheduleTitleTdEl);
  
      tbodyEl.appendChild(trEl);
    }
    return tbodyEl;
  }

// function createScheduleTableHeader(){

//     const scheduleHoursThE1 = document.createElement('th');
//     scheduleHoursThE1.classList.add('default-th');
//     scheduleHoursThE1.textContent = 'Hours';

//     const scheduleDayThE1 = document.createElement('th');
//     scheduleDayThE1.classList.add('default-th');
//     scheduleDayThE1.textContent = 'Days';

//     const trEl = document.createElement('tr');
//     trEl.appendChild(scheduleHoursThE1);
//     trEl.appendChild(scheduleDayThE1);

//     const theadEl = document.createElement('thead');
//     theadEl.appendChild(trEl);  

//     return theadEl;
// }

function onScheduleLoad(schedule) {
    // const scheduleIdSpandEl = document.getElementById('schedule-id');
    // const scheduleTitleSpanEl = document.getElementById('schedule-title');
    // const scheduleDaysSpanEl = document.getElementById('schedule-days');

    // scheduleIdSpandEl.textContent = schedule.id;
    // scheduleTitleSpanEl.textContent = schedule.title;
    // scheduleDaysSpanEl.textContent = schedule.days;
    
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'schedule-table');
    const scheduleHoursThE1 = document.createElement('th');
    scheduleHoursThE1.classList.add('default-th');
    scheduleHoursThE1.textContent = 'Hours';
    for(let m = 0; m < 25; m++){
        const scheduleHourThEl = document.createElement('tr');
        scheduleHourThEl.classList.add('default-tr');
        scheduleHourThEl.textContent = m;
        scheduleHoursThE1.appendChild(scheduleHourThEl);
    }
    
    const tbodyEl = document.createElement('tbody');
    const trEl = document.createElement('tr');
    trEl.appendChild(scheduleHoursThE1);
    
    for(let i = 1; i <= schedule.days; i++) {
        
        const scheduleDayThE1 = document.createElement('th');
        scheduleDayThE1.classList.add('default-th');
        scheduleDayThE1.textContent = 'Day ' + i;
        const scheduleDHourThEl = document.createElement('tr');
        scheduleDHourThEl.classList.add('default-tr');
        for(let n = 0; n < 25; n++){
            const scheduleDHourThEl = document.createElement('tr');
            scheduleDHourThEl.classList.add('default-tr');
            scheduleDHourThEl.textContent = '....';
            scheduleDayThE1.appendChild(scheduleDHourThEl);
        }
        trEl.appendChild(scheduleDayThE1);
    }


    tbodyEl.appendChild(trEl);    
    tableEl.appendChild(tbodyEl);  


    tableEl.appendChild(createScheduleTableBody(schedule));
    removeAllChildren(scheduleContentDivEl);
    scheduleContentDivEl.appendChild(tableEl);
}

function onScheduleResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['schedule-content', 'back-to-profile-content', 'logout-content',]);
        onScheduleLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(scheduleContentDivEl, this);
    }
}