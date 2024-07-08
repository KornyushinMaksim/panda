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
        console.log('проверка роли');
        const header = document.getElementById('admHead');
        console.log(header);
        header.innerHTML = `
            <div class="side-menu">
                <div class="u">
                    <div class="l"><a href="/tasks">Домой?</a></div>
                    <div class="l"><a href="/tasks">Задачи?</a></div>
                    <div class="l"><a href="/employees">Сотрудники</a></div>
                    <div class="l"><a href="#">Portfolio</a></div>
                    <div class="l"><a href="#">Contact</a></div>
                </div>
            </div>
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