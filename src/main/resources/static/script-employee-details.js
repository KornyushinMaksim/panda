
let authEmployee;
let employeeData;

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

// async function loadDataComments(commentTaskId) {
//     fetch(`http://localhost:8080/api/v1/comments/get-comments-by-file-task-id/${commentTaskId}`)
//         .then(response => response.json())
//         .then(data => {
//             const commentBody = document.getElementById('commentList');

//             data.forEach(commit => {
//                 const row = document.createElement('div');
//                 const enter = document.createElement('br');

//                 // // Создаем ссылку для каждой задачи
//                 // const link = document.createElement('a');
//                 // link.href = `task?id=${task.id}`; // Перенаправляем на страницу с подробной информацией о задаче
//                 // link.textContent = task.id;

//                 // const cellId = document.createElement('td');
//                 // cellId.appendChild(link);
//                 // row.appendChild(cellId);

//                 // Добавляем остальные данные в ячейки таблицы
//                 const commentText = document.createElement('div');
//                 commentText.textContent = commit.comment;
//                 row.appendChild(commentText);

//                 const commentAuthor = document.createElement('p');
//                 commentAuthor.textContent = `${commit.authorComment.name} ${commit.authorComment.surname}`;
//                 row.appendChild(commentAuthor);

//                 const commentCreateDate = document.createElement('p');
//                 commentCreateDate.textContent = commit.dateComment;
//                 row.appendChild(commentCreateDate);

//                 // Добавляем строку в тело
//                 commentBody.appendChild(row);
//                 commentBody.appendChild(enter);
//             });
//         });
// }


// document.getElementById('commentForm').addEventListener('submit', function (event) {
//     event.preventDefault();

//     const textarea = document.getElementById('commentText').value;
//     // console.log(fileTaskData);
//     // console.log(authEmployee);
//     const commentData = {
//         fileTaskId: fileTaskData,
//         authorComment: authEmployee,
//         comment: textarea,
//         dateComment: ''
//     }
//     const urlCommentData = 'http://localhost:8080/api/v1/comments/add-comment';
//     fetch(urlCommentData, {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(commentData)
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
//     window.parent.location.reload();
// })

document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const emoloyeeId = urlParams.get('id');




    // Здесь можно выполнить запрос на сервер для получения подробной информации о задаче по её ID
    // Пример запроса
    fetch(`http://localhost:8080/api/v1/employees/get-employee-by-id/${emoloyeeId}`)
        .then(response => response.json())
        .then(employee => {
            console.log(employee);
            employeeData = employee;
            console.log(employeeData);

            // loadDataComments(data.id);


            // var execut = '';
            // var te = data.executors;
            // for (j = 0; j < te.length; j++) {
            //     execut += `${te[j].name} ${te[j].surname}<br>`
            // };

            const employeeDetailsDiv = document.getElementById('employeeDetails');
            // console.log(data.fileId);
            // console.log(authEmployee);
            employeeDetailsDiv.innerHTML = `
                <h1> ${employee.name} ${employee.surname}</h1><br><br>
                <td><p>ID:</p> ${employee.id}</td><br><br>
                <td><p>Имя:</p> ${employee.name}</td><br><br>
                <td><p>Фамилия:</p> ${employee.surname}</td><br><br>
                <td><p>Дата приема:</p> ${employee.employmentDate}</td><br><br>
                <td><p>Должность:</p> ${employee.jobTitle}</td><br><br>
                <td><p>Отдел:</p> ${employee.department}</td><br><br>
                <td><p>Роль:</p> ${employee.role}</td><br><br>
            `;
        })
        .catch(error => console.error('Ошибка получения данных о задаче:', error));
});
fetchData();
