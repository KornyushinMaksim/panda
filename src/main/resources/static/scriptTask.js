// // добавление документа
// function toggleVisibility(id) {
//     let element = document.getElementById(id);
//     console.log(element);
//     if (element.style.display === 'none') {
//         element.style.display = 'block';
//     } else {
//         element.style.display = 'none';
//     }
// }

let authEmployee;
let fileTaskData;

// // Функция для выполнения запроса и получения данных
// async function fetchData() {
//     return fetch('http://localhost:8080/api/v1/currents/current-employee-id')
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Ошибка HTTP, статус ' + response.status);
//             }
//             return response.json();
//         })
//         .then(data => {
//             // В этом блоке вы получаете данные и можете сделать с ними что-то нужное
//             console.log('Данные получены:', data);
//             authEmployee = data; // сохранение данных в глобальную переменную
//             // checkRole(authEmployee);
//         })
//         .catch(error => {
//             // Обработка ошибок
//             console.error('Произошла ошибка:', error);
//         });
// }

async function loadDataComments(commentTaskId) {
    fetch(`http://localhost:8080/api/v1/comments/get-comments-by-file-task-id/${commentTaskId}`)
        .then(response => response.json())
        .then(data => {
            const commentBody = document.getElementById('commentList');

            data.forEach(commit => {
                const row = document.createElement('div');
                const enter = document.createElement('br');

                // // Создаем ссылку для каждой задачи
                // const link = document.createElement('a');
                // link.href = `task?id=${task.id}`; // Перенаправляем на страницу с подробной информацией о задаче
                // link.textContent = task.id;

                // const cellId = document.createElement('td');
                // cellId.appendChild(link);
                // row.appendChild(cellId);

                // Добавляем остальные данные в ячейки таблицы
                const commentText = document.createElement('div');
                commentText.textContent = commit.comment;
                row.appendChild(commentText);

                const commentAuthor = document.createElement('p');
                commentAuthor.textContent = `${commit.authorComment.name} ${commit.authorComment.surname}`;
                row.appendChild(commentAuthor);

                const commentCreateDate = document.createElement('p');
                commentCreateDate.textContent = commit.dateComment;
                row.appendChild(commentCreateDate);

                // Добавляем строку в тело
                commentBody.appendChild(row);
                commentBody.appendChild(enter);
            });
        });
}


document.getElementById('commentForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const textarea = document.getElementById('commentText').value;
    // console.log(fileTaskData);
    // console.log(authEmployee);
    const commentData = {
        fileTaskId: employeeData,
        authorComment: authEmployee,
        comment: textarea,
        dateComment: ''
    }
    const urlCommentData = 'http://localhost:8080/api/v1/comments/add-comment';
    fetch(urlCommentData, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(commentData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // Разбираем JSON ответ в JavaScript объект
        })
        .then(data => {
            console.log('Успешный ответ:', data);
        })
        .catch(error => {
            console.error('Ошибка при выполнении запроса:', error);
        });
    window.parent.location.reload();
})

document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const taskId = urlParams.get('id');



    // Здесь можно выполнить запрос на сервер для получения подробной информации о задаче по её ID
    // Пример запроса
    fetch(`http://localhost:8080/api/v1/tasks/get-task-by-id/${taskId}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            employeeData = data;
            console.log(employeeData);

            loadDataComments(data.id);


            var execut = '';
            var te = data.executors;
            for (j = 0; j < te.length; j++) {
                execut += `${te[j].name} ${te[j].surname}<br>`
            };


            // const taskData = getEmployee(executors, fileId, nameTask, authorTask, customerId, deadLine);
            // taskData.then(data => {
            //     console.log(data)
            //     const urlTaskData = 'http://localhost:8080/api/v1/tasks/add-task';

            //     // Формирование POST запроса с использованием Fetch API
            //     fetch(urlTaskData, {
            //         method: 'POST',
            //         headers: {
            //             'Content-Type': 'application/json', // Указываем тип содержимого (JSON)
            //         },
            //         body: JSON.stringify(data) // Преобразуем объект в JSON строку для отправки
            //     })
            //         .then(response => {
            //             if (!response.ok) {
            //                 throw new Error('Network response was not ok');
            //             }
            //             return response.json(); // Разбираем JSON ответ в JavaScript объект
            //         })
            //         .then(data => {
            //             console.log('Успешный ответ:', data);
            //         })
            //         .catch(error => {
            //             console.error('Ошибка при выполнении запроса:', error);
            //         });
            // });

            let loadBtn = document.getElementById('load');
            console.log('auhtemployee')
            console.log(data.fileId);
            if(data.fileId.active === false) {
                loadBtn.innerHTML = `
                <a href=http://localhost:8080/api/v1/files/download-file?id=${data.fileId.id}&employeeId=${authEmployee.id} download >
                    <button >Скачать файл</button>
                </a>
                `;
            } else {
                loadBtn.innerHTML = `
                <div>
                    <p>Файл скачал: </p>${data.fileId.employee.name} ${data.fileId.employee.surname}
                </div><br>
                `;
            };


            const taskDetailsDiv = document.getElementById('taskDetails');
            console.log(data.fileId);
            console.log(authEmployee);
            taskDetailsDiv.innerHTML = `
        <h1> ${data.nameTask}</h1><br><br>
            <div class="propertiesFileTitleBox">

                <div class="propertiesFileInfoImage">
                    <div class="logoFile" style="background:url(/images/typeFile/${data.fileId.typeFile}.png);
                        background-repeat: no-repeat;
                        background-size: cover;'">
                    </div><br>
                    <div>
                        ${data.fileId.nameFile}
                    </div>
                </div>

                <div class="propertiesFileInfoText">
                    <div class="propertiesFileInfoTextAuthor">
                        <p>Исполнитель:</p>
                        <div>${execut}</div>
                    </div><br>
                    <div class="propertiesFileInfoTextDate">
                        <p>Заказчик: </p>
                        <div>${data.customerId.nameOrg}</div>
                    </div><br>
                    <div class="propertiesFileInfoAutor">
                        <p>Заявку создал:</p>
                        <div>${data.authorTask}</div>
                    </div>
                </div>

                <div>
                    здесь будет примечание
                </div>

                <div class="propertiesFileDateInfo">
                    <p>дедлайн</p>
                    <div>${data.deadLine}</div>
                </div>

            </div>
        `;
        })
        .catch(error => console.error('Ошибка получения данных о задаче:', error));
});
fetchData();
