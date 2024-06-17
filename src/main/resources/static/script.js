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

//// Функция получения сотрудников
//document.querySelector('employeesTableBody').innerHTML = ` `;
//for(key el in employees) {
//    const row = document.createElement('tr');
//    row
//}


// Функция для отображения списка сотрудников в таблице
function displayTasks(tasks) {

    const tableBody = document.getElementById('tasksTableBody');
    tableBody.innerHTML = ''; // Очищаем содержимое таблицы перед добавлением новых данных
    console.log('array');
    console.log(tasks);
    arrExecut = tasks[0].executors;
    console.log(arrExecut);
    execut = '';
    for(elExec in tasks) {
        console.log(elExec[0].executors + '1')
        for(el in elExec) {
            console.log(el + '2')
            for(e in el) {
                console.log(e ='3')
                execut += `${e.name} ${e.surname},<br>`
            }
        }    
    }
    tasks.forEach(task => {
        const row = document.createElement('tr');
        row.innerHTML = `
      <td>${task.id}</td>
      <td>${task.fileId.nameFile}</td>
      <td>${task.customerId.nameOrg}</td>
      <td>${execut}</td>
      <td>${task.deadLine}</td>
      <td>${task.authorTask.name} ${task.authorTask.surname}</td>
    `;
//    console.log('stroka ');
//    console.log(row);
//    console.log(employee.deadLine);
//        const editIcon = createEditIcon(employee.id);
//        row.querySelector('td:last-child').appendChild(editIcon);
        tableBody.appendChild(row);
    });
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
