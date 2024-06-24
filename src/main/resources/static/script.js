// добавление документа
function toggleVisibility() {
    let element = document.getElementById('docAdd');
    if (element.style.display === 'none') {
        element.style.display = 'block';
    } else {
        element.style.display = 'none';
    }
}
function toggleVisibility1() {
    let element = document.getElementById('docAdd1');
    if (element.style.display === 'none') {
        element.style.display = 'block';
    } else {
        element.style.display = 'none';
    }
}

function open() {
    let el = document.getElementById('docAdd');
    el.classList.add('open');
}
function close() {
    let el = document.getElementById('docAdd');
    el.classList.remove('open');
}

document.addEventListener("DOMContentLoaded", function() {
    fetch('http://localhost:8080/tasks/all-tasks')
        .then(response => response.json())
        .then(data => {
            const tasksTableBody = document.getElementById('tasksTableBody');

            data.forEach(task => {
                const row = document.createElement('tr');
        
                // Создаем ссылку для каждой задачи
                const link = document.createElement('a');
                link.href = `task?id=${task.id}`; // Перенаправляем на страницу с подробной информацией о задаче
                link.textContent = task.id;
        
                const cellId = document.createElement('td');
                cellId.appendChild(link);
                row.appendChild(cellId);
        
                // Добавляем остальные данные в ячейки таблицы
                const cellName = document.createElement('td');
                cellName.textContent = task.nameTask;
                row.appendChild(cellName);
        
                const cellCustomer = document.createElement('td');
                cellCustomer.textContent = task.customerId.nameOrg;
                row.appendChild(cellCustomer);
        
                const cellDeadline = document.createElement('td');
                cellDeadline.textContent = task.deadLine;
                row.appendChild(cellDeadline);
        
                // Добавляем строку в тело таблицы
                tasksTableBody.appendChild(row);
            });
        });
    });


// // Функция для получения задач и отображения их в таблице
// async function fetchTasks() {
//     try {
//         const response = await fetch('http://localhost:8080/tasks/all-tasks');
//         if (!response.ok) {
//             throw new Error('Ошибка!!! при загрузке данных с сервера');
//         }
//         const tasks = await response.json();
//         displayTasks(tasks);
//     } catch (error) {
//         console.error('Ошибка:', error);
//     }
// }

// // Функция для отображения списка сотрудников в таблице
// function displayTasks(tasks) {

//     const tableBody = document.getElementById('tasksTableBody');
//     // tableBody.innerHTML = ''; // Очищаем содержимое таблицы перед добавлением новых данных

//     console.log(tasks);

//     for (i = 0; i < tasks.length; i++) {
//         var execut = '';

//         var te = tasks[i].executors;
//         for (j = 0; j < te.length; j++) {
//             execut += `${te[j].name} ${te[j].surname}<br>`
//         };

//         const row = document.createElement('tr');

//         row.innerHTML = `
//       <td>${tasks[i].id}</td>
//       <td >
//       <a th:onclick="sendRequest('${tasks[i].id}')" href="/task" >
//         ${tasks[i].nameTask}
//       </a>
//       </td>
//       <td>${tasks[i].customerId.nameOrg}</td>
//       <td>${execut}</td>
//       <td>${tasks[i].deadLine}</td>
//       <td>${tasks[i].authorTask.name} ${tasks[i].authorTask.surname}</td>

//       <form action="/task" th:action="@{tasks/get-task-by-id}" method="get">
//       <input type="text" name="id" value=${tasks[i].id}><br>
//       <div class="bottons">
//           <button onclick="say()" type="submit" name="select" value="add">Добавить</button>
//       </div>
//   </form>
//         `;

//         tableBody.appendChild(row);
//     };
// }

//отправляет запрос
// function sendRequest(id) {
//     console.log(id)
//     // Создаем объект XMLHttpRequest для отправки запроса
//     var xhr = new XMLHttpRequest();

//     // Формируем URL с параметром id
//     var url = 'http://localhost:8080/tasks/get-task-by-id/data?id=' + encodeURIComponent(id);

//     // Открываем асинхронное соединение с сервером
//     xhr.open('GET', url, true);

//     // Устанавливаем обработчик события загрузки
//     xhr.onload = function () {
//         if (xhr.status >= 200 && xhr.status < 400) {
//             // Успешно получили ответ от сервера
//             console.log(xhr.responseText);
//         } else {
//             // В случае ошибки выводим сообщение
//             console.error('Ошибка при запросе: ' + xhr.status);
//         }
//     };

//     // Обрабатываем событие ошибки
//     xhr.onerror = function () {
//         console.error('Сетевая ошибка');
//     };

//     // Отправляем запрос
//     xhr.send();

// }

function sendRequest(id) {
    console.log('sadfasd')
    var url = 'http://localhost:8080/tasks/get-task-by-id?id=' + encodeURIComponent(id);

    // console.log(id)

    fetch(url)
        .then(response => response.json())
        .then(data => {
            var execut = '';
            var te = data.executors;
            for (j = 0; j < te.length; j++) {
                execut += `${te[j].name} ${te[j].surname}<br>`
            };

            const fileData = document.getElementById('propertiesFile');

            fileData.innerHTML = `
            <h1> ${data.nameTask}</h1><br><br>
<div class="propertiesFileTitleBox">
    <div class="logoFile" th:style="'background:url(' + @{/images/typeFile/{type}.png(type = ${data.fileId.typeFile})} +');
     background-repeat: no-repeat;
     background-size: cover;'">
    </div>
    <div class="propertiesFileInfoText">
        <div class="propertiesFileInfoTextAuthor">
            <p>Исполнитель:</p>
            <div>${execut}</div>
        </div><br>
        <div class="propertiesFileInfoTextDate">
            <p>Заказчик: </p>
            <div>${data.customerId.nameOrg}</div>
        </div>
    </div>
    <div>
        здесь будет примечание
    </div>
    <div class="propertiesFileDateInfo">
        <div>дедлайн</div>
        <div>${data.deadLine}</div>
    </div>
</div>


            `;
        })
        .catch(error => {
            console.error("Ошибка при получении данных:", error);
            fileData.innerHTML = '<p>Произошла ошибка при получении данных.</p>';
        });

}

// function mybtn(id) {
//     // myButton.addEventListener('click', () => {
//         const myButton = document.getElementById('myButton');
//         const url = `http://localhost:8080/tasks/get-task-by-id?id=${id}`; // Сформируйте URL с ID
//         window.location.href = url; // Перенаправьте на URL
//     //   });
// }
// fetchTasks();
// const id = "b197a1dd-9638-402a-aca3-58e95d14bb23";
// sendRequest(id);