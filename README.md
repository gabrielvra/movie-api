# API de Intervalo de Prêmios em Spring Boot
Golden Raspberry Awards

Esta é uma aplicação Spring Boot que calcula o maior e menor intervalo (em anos) entre prêmios consecutivos armazenados em um banco de dados H2 em memória. A aplicação expõe um endpoint REST em `/api/movies/intervalos-premio` e trata exceções com um formato de resposta de erro consistente.

## Pré-requisitos

Antes de executar a aplicação, certifique-se de ter instalado:
- **Java 17 ou superior** (JDK)
- **Maven 3.6 ou superior**
- **curl** (para testar a API)
- **Git** (opcional, para clonar o repositório)

## Instruções de Configuração

### 1. Configurar a Aplicação
A aplicação usa um banco H2 em memória e carrega dados de um arquivo csv. A configuração está em `src/main/resources/application.properties`:

```properties
spring.application.name=movie-api
spring.datasource.url=jdbc:h2:mem:moviedb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
csv.file.path=data/movielist.csv
```

- **Banco H2**: Configurado para uso em memória com criação automática de schema.
- **Arquivo CSV**: Dados pré-definidos, localizado em `data/movielist.csv`.

### 2. Compilar a Aplicação
Navegue até o diretório raiz do projeto e compile a aplicação com Maven:

```bash
mvn clean install
```

Este comando compila o código, executa testes e empacota a aplicação em um arquivo JAR.

### 4. Executar a Aplicação
Inicie a aplicação Spring Boot:

```bash
mvn spring-boot:run
```

Alternativamente, execute o arquivo JAR gerado:
```bash
java -jar target/<nome-do-projeto>-0.0.1-SNAPSHOT.jar
```

A aplicação será iniciada em `http://localhost:8080` por padrão.

Na inicialização, a base de dados será automaticamente populada pelo serviço `MovieService.loadMoviesFromFile`

### 5. Testar a API
A aplicação expõe o endpoint `GET /api/movies/intervalos-premio` para calcular o maior intervalo entre anos de prêmios consecutivos.

#### Usando `curl`
Execute o seguinte comando para testar o endpoint:

```bash
curl http://localhost:8080/api/movies/intervalos-premio
```

**Resposta Esperada** (HTTP 200 OK):
```json
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    }
  ]
}

```
#### Teste de URL Inválida
Para testar uma resposta 404 Not Found para uma URL inválida:
```bash
curl http://localhost:8080/api/invalid
```

**Resposta Esperada** (HTTP 404 Not Found):
```json
{
  "timestamp":"2025-09-28T16:48:59.341+00:00",
  "status":404,
  "error":"Not Found",
  "path":"/api/movies/intervalos-premo"
}
```

### Teste com novo registro que possui menor intervalo
Acessar o banco h2 pelo endereço `http://localhost:8080/h2-console/`
- JDBC URL: `jdbc:h2:mem:moviedb`
- User Name:	`sa`
- Password: ``

Executar o comando:
```sql
INSERT INTO MOVIE (ID, WINNER, YEAR_RELEASED, PRODUCERS, STUDIOS, TITLE)
VALUES (999, TRUE, 1981, 'Allan Carr', 'Associated Film Distribution', 'Can''t Stop the Music');
```

#### Usando `curl`
Execute o seguinte comando para testar o endpoint:

```bash
curl http://localhost:8080/api/movies/intervalos-premio
```

**Resposta Esperada** (HTTP 200 OK):
```json
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    },
    {
      "producer": "Allan Carr",
      "interval": 1,
      "previousWin": 1980,
      "followingWin": 1981
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    }
  ]
}
```
