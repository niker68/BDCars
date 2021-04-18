# Загрузка авто в базу

### Требуемое ПО

Для сборки и запуска проекта требуются:
- [Java 11 (Open JDK)](https://openjdk.java.net/projects/jdk/11/)
- [Apache Maven 3.3+](https://maven.apache.org/)

Для развертывания приложения используется механизм контейнеризации:
- [Docker](https://www.docker.com/)

### Запуск приложения

Для запуска приложения необходимо:
1. Скомпилировать и собрать проект: mvn clean package
2. Запустить создание образов и контейнеров приложения: docker-compose up -d

### API

Сервис предоставляет методы:
1. GET localhost:8888\car?=id (вместо id подставить id искомого авто). Возвращает искомое авто в формате JSON.
2. POST localhost:8888\car. Принимает и добавляет в базу данных авто в формате JSON вида 
{"id":"1",
"gosnomer":"1657",
"model":"Moskvich",
"color":"Red",
"year":"1975"}
3. GET localhost:8888\carall. Возвращает в формате JSON все авто в базе данных.
  
## Контакты
  Created by [@niker68](mailto:niker68@yandex.ru)
