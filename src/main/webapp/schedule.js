function onScheduleLoad(schedule) {
    const scheduleIdSpandEl = document.getElementById('schedule-id');
    const scheduleTitleSpanEl = document.getElementById('schedule-title');
    const scheduleDaysSpanEl = document.getElementById('schedule-days');

    scheduleIdSpandEl.textContent = schedule.id;
    scheduleTitleSpanEl.textContent = schedule.title;
    scheduleDaysSpanEl.textContent = schedule.days;
}

function onScheduleResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['schedule-content', 'back-to-profile-content', 'logout-content']);
        onScheduleLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}