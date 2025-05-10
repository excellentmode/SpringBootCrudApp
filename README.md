# Crud App (Spring Boot)

Проект базового Crud App сервиса с возможностью:

- Авторизации по email и паролю
- Поиска пользователей с фильтрацией
- Денежных переводов между пользователями
- Кэширования пользовательских данных в Redis
- Проведения интеграционного и unit-тестирования с использованием Testcontainers

---

## Технологии

- Java 21
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA + Hibernate (Criteria Api)
- PostgreSQL
- Redis
- Liquibase
- MapStruct
- Swagger (OpenAPI)
- JUnit + Mockito + Testcontainers

---

# Запуск проекта для разработки и локального тестирования.

Считаем, что сам сервис Spring Boot собираем и запускаем с использованием среды разработки.
Сервисы (PostgreSQL и Redis) - будем запускать используя docker-compose.

### 1. Предварительные условия

Установить:

- Docker + Docker Compose

Если запуск приложения планируется без среды разработки, то понадобится установить:

- Java 21
- Maven

### 2. Запуск инфраструктуры (PostgreSQL + Redis)

```
docker-compose up -d
```

Эта команда выкачает образы и поднимет контейнеры:

- PostgreSQL на `localhost:5432`
- Redis на `localhost:6379`

---

### 3. Конфиг приложения

Приложение читает параметры из `application.yml`:

- `spring.datasource.url`, `username`, `password` — подключение к БД
- `jwt.secret` — секрет для генерации токена (произвольная строка)

По умолчанию эти значения устанавливаются автоматически.

---

### 4. Запуск приложения

```
./mvnw spring-boot:run
```

Или через RUN в своей среде разработки.

Доступные endpotint-ы:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Авторизация: `POST /api/v1/auth/login`
- Поиск пользователей: `GET /api/v1/user/search`
- Перевод средств: `POST /api/v1/transfer`

---

## Запуск тестов

```
./mvnw test
```
Или через RUN-TESTS своей среде разработки.

Выполнятся:

- Unit-тесты для `TransferService`
- Интеграционные тесты с использованием Testcontainers для PostgreSQL и Redis

---

## Тестовые данные

Миграции Liquibase автоматически создают несколько пользователей с аккаунтами, email и телефонами.  
Пароль единый для всех пользователей: `password1231231235245`

Пример запроса для получения токена:

```json
{
  "email": "ivan@example.com",
  "password": "password1231231235245"
}
```

---

## Структура директорий проекта

```
src
├── main
│   ├── controller         # REST-контроллеры
│   ├── entity             # JPA-сущности
│   ├── model              # Модели бизнес-логики
│   ├── dto                # DTO для API
│   ├── service            # Бизнес-логика и дата-сервисы
│   ├── mapper             # MapStruct мапперы
│   ├── repository         # Spring-Data репозитории
│   ├── security           # JWT, фильтры, конфиги
│   ├── config             # Конфигурации
│   ├── scheduler          # Планировщики
│   ├── exception          # Обратотчики ошибок
│   └── utils              # Утильные классы
└── test
    ├── config             # Конфигурации для тестов
    ├── unit               # Unit-тесты
    └── integration        # Интеграционные тесты
```