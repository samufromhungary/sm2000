let taskId;

function onTaskLoad(taskDto) {
    taskId = taskDto.task.id;

    const taskIdSpandEl = document.getElementById('task-id');
    const taskNameSpanEl = document.getElementById('task-title');
    const taskContentSpanEl = document.getElementById('task-content');

    taskIdSpandEl.textContent = taskDto.task.id;
    taskNameSpanEl.textContent = taskDto.task.title;
    taskContentSpanEl.textContent = taskDto.task.content;

}

function onTaskResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['task-content', 'back-to-profile-content', 'logout-content']);
        onTaskLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(TasksContentDivEl, this);
    }
}