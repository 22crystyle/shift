# Тестовое задание. Курса Шифт
## Требования к запуску
1. Установите Oracle JDK 21.0.2, и переменную JAVA_HOME для вашей операцинной системы
2. Gradle >= 8.8, и путь к gradle-8.8/bin в переменную PATH вашей ОС
3. Перейдите в папку с проектом из консоли
4. Запустите gradle jar
5. Перейдите в папку shift/build/libs
6. Используйте команду java -jar shift-1.0-SNAPSHOT.jar
### Зависимости ипользуемые gradle
```groovy
implementation("org.apache.logging.log4j:log4j-core:2.24.3")
implementation("org.apache.logging.log4j:log4j-api:2.24.3")
implementation("info.picocli:picocli:4.7.6")
annotationProcessor("info.picocli:picocli-codegen:4.7.6")
compileOnly("org.projectlombok:lombok:1.18.36")
annotationProcessor("org.projectlombok:lombok:1.18.36")
```
