@echo off
if "%JAVA_HOME%"=="" if exist "C:\Program Files\Android\Android Studio\jbr" set "JAVA_HOME=C:\Program Files\Android\Android Studio\jbr"
if "%JAVA_HOME%"=="" for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-17*") do set "JAVA_HOME=%%i"
start "ms-usuarios" /d "%~dp0ms-usuarios" cmd /k mvnw spring-boot:run
start "ms-clientes" /d "%~dp0ms-clientes" cmd /k mvnw spring-boot:run
start "ms-equipos" /d "%~dp0ms-equipos" cmd /k mvnw spring-boot:run
start "ms-instrumentos" /d "%~dp0ms-instrumentos" cmd /k mvnw spring-boot:run
start "ms-protocolos" /d "%~dp0ms-protocolos" cmd /k mvnw spring-boot:run
start "ms-pruebas" /d "%~dp0ms-pruebas" cmd /k mvnw spring-boot:run