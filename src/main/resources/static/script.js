// добавление документа
function toggleVisibility(id) {
    let element = document.getElementById(id);
    console.log(element);
    if (element.style.display === 'none') {
        element.style.display = 'block';
    } else {
        element.style.display = 'none';
    }
}

function cancelMyForm(idForm) {
    document.getElementById(idForm).reset();
    window.location.href = "/tasks";    
}

//Проверка роли
function checkRole(dataempl) {
    const role = dataempl.role;
    console.log(role);

    if (role === 'ROLE_ADM') {
        console.log('проверка роли администратор');
        const header = document.getElementById('headerMenu');
        console.log(header);
        header.innerHTML = `
                    <div><a href="/tasks">Список задач</a></div>
                    <div><a href="/my-tasks">Мои задачи</a></div>
                    <div><a href="/employees">Сотрудники</a></div>
                    <div><a href="#">Заказчики</a></div>
                    <div><a href="#">something</a></div>
            `;

    } else if (role === 'ROLE_MGR') {
        console.log('проверка роли менеджер');
        const header = document.getElementById('headerMenu');
        console.log(header);
        header.innerHTML = `
                    <div><a href="#">Договора</a></div>
                    <div><a href="#">Заказчики</a></div>
                    <div><a href="#">Соискатели</a></div>
                    <div><a href="#">Сотрудники</a></div>
            `;

    } else if (role === 'ROLE_USR') {
        console.log('проверка роли пользователя');
        const header = document.getElementById('headerMenu');
        console.log(header);
        header.innerHTML = `
                    <div><a href="/tasks">Список задач</a></div>
                    <div><a href="/my-tasks">Мои задачи</a></div>
                    <div><a href="/employees">Сотрудники</a></div>
            `;

    }
}

// Функция для выполнения запроса и получения данных
async function fetchData() {
    return fetch('http://localhost:8080/api/v1/currents/current-employee-id')
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка HTTP, статус ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            // В этом блоке вы получаете данные и можете сделать с ними что-то нужное
            console.log('Данные получены:', data);
            authEmployee = data; // сохранение данных в глобальную переменную
            checkRole(authEmployee);
        })
        .catch(error => {
            // Обработка ошибок
            console.error('Произошла ошибка:', error);
        });
}

fetchData();