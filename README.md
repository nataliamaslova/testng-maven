# Настройка TestNG, Maven, Параллельного Запуска и Allure

Этот README объясняет, как настроить и использовать TestNG с параллельным запуском тестов, интегрировать его с Maven, а также генерировать отчеты Allure.

## 1. Добавление зависимостей в `pom.xml`

Для начала нужно добавить необходимые зависимости и плагины в ваш `pom.xml` файл.

### Зависимости для TestNG и Allure:

```xml
<dependencies>
    <!-- Зависимость для TestNG: https://mvnrepository.com/artifact/org.testng/testng/ -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.11.0</version>
        <scope>test</scope>
    </dependency>

    <!-- Зависимость для Allure TestNG: https://mvnrepository.com/artifact/io.qameta.allure/allure-testng/ -->
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-testng</artifactId>
        <version>2.29.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 2. Плагин для запуска тестов с использованием maven-surefire-plugin и генерации отчетов Allure:
```xml
<build>
    <plugins>
        <!-- Плагин для выполнения тестов TestNG -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M5</version>
            <configuration>
                <suiteXmlFiles>
                    <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
                </suiteXmlFiles>
            </configuration>
        </plugin>

        <!-- Плагин для генерации отчетов Allure -->
        <plugin>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-maven</artifactId>
            <version>2.15.2</version> <!-- Версия Allure плагина: https://mvnrepository.com/artifact/io.qameta.allure/allure-maven/ -->
        </plugin>
    </plugins>
</build>
```
## 3. Создание testng.xml:
Файл testng.xml используется для конфигурации тестов, таких как выбор тестовых классов, параллельный запуск и другие настройки.

Пример testng.xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<suite name="All Test Suite" parallel="tests" thread-count="5">
    <test name="Smoke Tests">
        <groups>
            <run>
                <include name="smoke"/>
                <exclude name="regress"/>
            </run>
        </groups>
        <classes>
            <class name="SimpleTestNGTests"/>
        </classes>
    </test>
    <test name="Parametrized Tests">
        <classes>
            <class name="ParametrizedTests"/>
        </classes>
    </test>
</suite>
```
В этом примере:

include name="smoke" — тесты будут включаться в набор по тегу smoke
exclude name="regress" — из набора убираются тесты с тегом regress

Чтобы настроить параллельный запуск тестов, используйте атрибут parallel в файле testng.xml. Вы можете выбрать, чтобы выполнялись тесты, классы или методы параллельно:

parallel="tests" — тесты выполняются параллельно.
parallel="classes" — классы тестов выполняются параллельно.
parallel="methods" — методы в одном классе выполняются параллельно.

thread-count="5" — будет использоваться 5 потока для параллельного выполнения тестов

## 4. Запуск тестов через Maven
После того как вы добавили все необходимые зависимости и настроили файл testng.xml, вы можете запустить тесты с помощью команды Maven:
```bash
mvn clean test
```
Эта команда запустит тесты, определенные в testng.xml, используя maven-surefire-plugin.

Если у вас несколько наборов тестов, то можно добавить параметр 'suite' со значением по умолчанию:
```xml
<properties>
    <suite>testng</suite>
</properties>

<build>
    <plugins>
        <!-- Плагин для выполнения тестов TestNG -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M5</version>
            <configuration>
                <suiteXmlFiles>
                    <suiteXmlFile>src/test/resources/suites/${suite}.xml</suiteXmlFile>
                </suiteXmlFiles>
            </configuration>
        </plugin>
    </plugins>
</build>
```
Тогда запуститить, например, тестовый набор regression.xml можно будет с помощью команды:
```bash
mvn test -Dsuite=regression
```

## 5. Генерация отчетов Allure
Allure по умолчанию сохраняет результаты тестов в корневой директории проекта. Однако рекомендуется хранить результаты тестов в директории вывода сборки.
Чтобы настроить это, создайте файл allure.properties и поместите его в директорию ресурсов тестов вашего проекта, которая обычно находится по пути src/test/resources.
Добавьте туда следующий код:
```text
allure.results.directory=target/allure-results
```

Запустите команду для генерации отчета:
```bash
allure serve target/allure-results
```
Эта команда откроет отчет Allure в вашем браузере. Нужно находиться в папке, которая содержит allure-results.

## 6. Настройка AspectJ
Allure использует AspectJ для работы аннотаций @Step и @Attachment.
Кроме того, некоторые интеграции с фреймворками (например, allure-assertj) зависят от интеграции с AspectJ для корректной работы.

Как добавить:
```xml
<!-- Define the version of AspectJ -->
<properties>
    <aspectj.version>1.9.20.1</aspectj.version>
</properties>

<!-- Add the following options to your maven-surefire-plugin -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <argLine>
            -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar"
        </argLine>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>${aspectj.version}</version>
        </dependency>
    </dependencies>
</plugin>
```

## 7. Добавление системных переменных
С помощью блока systemPropertyVariables можно добавлять системные переменные.
Пример добавленного блока systemPropertyVariables:
```xml
<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.1.2</version>
                <configuration>
                    <systemPropertyVariables>
                        <allure.results.directory>target/allure-results</allure.results.directory>
                    </systemPropertyVariables>
                </configuration>
            </plugin>
        </plugins>
</build>
```
В данном случае, это аналог создания property file allure.properties.

Кроме того, задавать переменные можно и при выполнении через командную строку:
```bash
mvn clean test -Dallure.results.directory='target/allure-results'
```

## Полезные ссылки
https://allurereport.org/docs/testng/