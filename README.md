># Команды для неавторизованных пользователей


### Авторизация
````
/api/signin 

Тип запроса: Post 
Тело:

{
    "username" : "Ник", 
    "password" : "Ну ты понял"
      
}

Ответ:
{
    "token": "Супер огромный токен",
    "type": "Тип токена(обычно Bearer)",
    "id": айди пользователя в виде Integer,
    "username": "Логин пользователя",
    "email": "Его почта",
    "roles": [Роли чувака в виде массива строк]
}

Ответ при ошибке:
{
    message: "Причина ошибки"
}

````
### Регистрация
````
/api/register 

Тип запроса: Post 
Тело: {
    "username": "Ник", 
    "password":"Пароль", 
    "email": "Почта лол" 
}

Ответ:
{
    "message": "User Created"
}

````
### Получить всех пользователей

````
/api/users

Тип запроса: Get 

Ответ:
[ Массив объектов User
    Пример одного User'а
    {
        "id": 0,
        "username": "user",
        "password": "password",
        "email": "mail@mail.com",
        "roles": [
            {
                "name": "ROLE_USER"
                "name": "ROLE_TEACHER"
                "name": "VIP"
            }
        ]
    }
]  

````
### Получить модуль по айди
````
/api/cardSet/{id}
Тип запроса: Get

Ответ:
{
    "id": айди карточки в виде Integer,
    "creator": {
        "id": айди создателя(пользователя) в виде Integer,
        "username": "Ник создателя",
        "password": "Его пароль ?",
        "email": "почта пользователя",
        "roles": [Массив ролей пользователя]
    },
    "cardList": [ Массив объекта Card
        Пример:
        {
            "id": 1,
            "ru": "яблоко",
            "ruTranscription": "й’аблака",
            "en": "apple",
            "enTranscription": "æpl"
        }, ...
    ]
}
````





