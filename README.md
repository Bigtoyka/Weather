# Weather App

Это приложение позволяет пользователю отслеживать погоду 
в различных городах. Пользователь может создавать аккаунт, 
добавлять и удалять локации городов, 
искать погоду по названию города и отображать её на главной странице. 
Каждая локация отображает актуальное значение погоды с наглядной 
картинкой.

## Основные возможности

- **Регистрация и авторизация**: Пользователи могут создать аккаунт и авторизоваться.
- **Добавление и удаление локаций**: Пользователи могут добавлять города в список своих локаций и удалять их по мере необходимости.
- **Поиск погоды по городу**: Пользователь может искать погоду по названию города и добавить его на главную страницу.
- **Главная страница**: На главной странице отображаются текущие данные о погоде для каждого добавленного города, включая картинку, соответствующую погоде.


## Технологии

- **Java 17** — основной язык программирования.
- **Spring Boot** — для создания бэкенда.
- **Spring Security** — для защиты приложения.
- **JPA/Hibernate** — для работы с базой данных.
- **PostgreSQL** — для хранения данных.
- **Thymeleaf** — для рендеринга страниц.
- **OpenWeather API** — для получения данных о погоде.


## Установка (локальный запуск проекта)

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/Bigtoyka/Weather.git

2. В дериктории /resources представлен пример application.properties, 
укажи свои значения в данных строках 
   ```
   pring.datasource.url=url  
   spring.datasource.username=username  
   spring.datasource.password=password  
   weather.api.key=apiKey

3. Запусти приложени


# Weather App

This app allows users to track the weather in various cities. Users can create an account, add and remove city locations, search for weather by city name, and display it on the main page. Each location shows the current weather value with a representative image.

## Key Features

- **Registration and Authentication**: Users can create an account and log in.
- **Adding and Removing Locations**: Users can add cities to their list of locations and remove them as needed.
- **Search Weather by City**: Users can search for weather by city name and add it to the main page.
- **Main Page**: The main page displays the current weather data for each added city, including an image representing the weather.

## Technologies

- **Java 17** — primary programming language.
- **Spring Boot** — for backend development.
- **Spring Security** — for securing the application.
- **JPA/Hibernate** — for database interaction.
- **PostgreSQL** — for data storage.
- **Thymeleaf** — for rendering pages.
- **OpenWeather API** — for fetching weather data.

## Installation (Local Project Setup)

1. Clone the repository:
   ```bash
   git clone https://github.com/Bigtoyka/Weather.git

2. In the /resources directory, there is an example 
of application.properties. 
Update the following lines with your own values:
   ```
   pring.datasource.url=url  
   spring.datasource.username=username  
   spring.datasource.password=password  
   weather.api.key=apiKey

3. Run the application.