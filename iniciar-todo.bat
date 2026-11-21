@echo off
if "%JAVA_HOME%"=="" for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-17*") do set "JAVA_HOME=%%i"
start "ms-usuarios" cmd /k "cd /d C:\TerraLog-microservicios\ms-usuarios && mvnw spring-boot:run"
start "ms-clientes" cmd /k "cd /d C:\TerraLog-microservicios\ms-clientes && mvnw spring-boot:run"
start "ms-equipos" cmd /k "cd /d C:\TerraLog-microservicios\ms-equipos && mvnw spring-boot:run"
start "ms-instrumentos" cmd /k "cd /d C:\TerraLog-microservicios\ms-instrumentos && mvnw spring-boot:run"
start "ms-protocolos" cmd /k "cd /d C:\TerraLog-microservicios\ms-protocolos && mvnw spring-boot:run"
start "ms-pruebas" cmd /k "cd /d C:\TerraLog-microservicios\ms-pruebas && mvnw spring-boot:run"