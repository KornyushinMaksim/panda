// добавление документа
function toggleVisibility() {
    let element = document.getElementById('docAdd');
    if (element.style.display === 'none') {
        element.style.display = 'block';
    } else {
        element.style.display = 'none';
    }
}

// Функция для получения сотрудников и отображения их в таблице
async function fetchTasks() {
    try {
        const response = await fetch('http://localhost:8080/tasks/all-tasks');
        if (!response.ok) {
            throw new Error('Ошибка!!! при загрузке данных с сервера');
        }
        const tasks = await response.json();
        displayTasks(tasks);
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

// Функция для отображения списка сотрудников в таблице
function displayTasks(tasks) {

    const tableBody = document.getElementById('tasksTableBody');
    tableBody.innerHTML = ''; // Очищаем содержимое таблицы перед добавлением новых данных

    for (i = 0; i < tasks.length; i++) {
        var execut = '';

        var te = tasks[i].executors;
        for (j = 0; j < te.length; j++) {
            execut += `${te[j].name} ${te[j].surname}<br>`
        };

        const row = document.createElement('tr');

        row.innerHTML = `
      <td>${tasks[i].id}</td>
      <td>${tasks[i].fileId.nameFile}</td>
      <td>${tasks[i].customerId.nameOrg}</td>
      <td>${execut}</td>
      <td>${tasks[i].deadLine}</td>
      <td>${tasks[i].authorTask.name} ${tasks[i].authorTask.surname}</td>
        `;

        tableBody.appendChild(row);
    };
}
fetchTasks();


function open() {
    let el = document.getElementById('docAdd');
    el.classList.add('open');
}
function close() {
    let el = document.getElementById('docAdd');
    el.classList.remove('open');
}
