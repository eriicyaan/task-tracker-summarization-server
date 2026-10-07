# Task Tracker Summarization

Микросервис для генерации отчётов по задачам пользователей с использованием LLM.

Сервис получает данные о задачах через Kafka, формирует запрос к LLM и возвращает готовый отчёт.

## Возможности

* Получение данных о задачах через Kafka
* Формирование контекста для LLM
* Генерация ежедневных отчётов
* Взаимодействие с LLM API
* Возврат результата через Kafka RPC
* Формирование отчёта в PDF

## Технологии

**Backend:**

* Java
* Spring Boot
* Maven

**Messaging:**

* Apache Kafka
* Spring Kafka

**AI:**

* LLM API

**Documents:**

* Apache PDFBox

**DevOps:**

* Docker
* Docker Compose

## Клонирование репозитория

```bash

git clone https://github.com/eriicyaan/task-tracker-summarization.git
```

## Запуск

Для запуска всего проекта используйте инфраструктурный репозиторий:

[task-tracker-infrastructure](https://github.com/eriicyaan/task-tracker-infrastructure)

