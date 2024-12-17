# LevExpress - Gestão de Entregas 🚚

O **LevExpress** é um sistema desenvolvido para a gestão eficiente de entregas, conectando clientes, entregadores e o sistema central. O projeto utiliza **Spring Boot**, **Camunda BPMN** e outras tecnologias modernas para orquestrar tarefas e processos de forma estruturada e escalável.

---

## 📋 Funcionalidades Principais

1. **Gestão de Encomendas**
   - Criação, registro e acompanhamento de pedidos de entrega.
   - Notificação automática de status da encomenda ("em trânsito", "entregue").

2. **Seleção de Entregadores**
   - Notificação aos entregadores disponíveis para realização da entrega.
   - Possibilidade dos entregadores responderem com propostas.

3. **Gerenciamento de Feedback**
   - Solicitação e armazenamento de feedback de clientes.

4. **Processos BPMN com Camunda**
   - Modelagem e execução de fluxos de trabalho utilizando **Camunda Modeler**.
   - Integração dos workflows com tarefas manuais, serviços e notificações.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal.
- **Spring Boot**: Framework para desenvolvimento de aplicações back-end.
- **Camunda BPM**: Plataforma de modelagem e execução de processos BPMN.
- **Maven**: Gerenciamento de dependências.
- **GitHub**: Controle de versões e colaboração.
- **Postman**: Teste de APIs.

---

## 📦 Estrutura do Projeto

```bash
levExpress/
├── src/
│   ├── main/
│   │   ├── java/com/example/levExpress/
│   │   │   ├── config/        # Configurações do projeto
│   │   │   ├── controllers/   # Endpoints REST
│   │   │   ├── services/      # Lógica de negócio
│   │   │   ├── models/        # Classes modelo (Cliente, Entregador, Encomenda, etc.)
│   │   │   ├── bpmn/          # Processos BPMN Camunda
│   │   │   └── LevExpressApplication.java
│   ├── test/
│   │   └── java/              # Testes unitários e de integração
├── pom.xml                    # Configuração Maven
└── README.md                  # Documentação
```

---

## 🛠️ Configuração do Ambiente

### Pré-requisitos
- **Java 17**
- **Maven**
- **Camunda Modeler** (opcional para editar BPMN)
- **Banco de Dados** (ex.: PostgreSQL ou MySQL)

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/SEU_USUARIO/levExpress.git
   cd levExpress
   ```

2. Compile e construa o projeto com Maven:
   ```
   mvn clean install
   ```

3. Configure as variáveis de ambiente no `application.properties`:
   ```properties
   server.port=8080
   spring.datasource.url=jdbc:mysql://localhost:3306/levexpress_db
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

4. Execute a aplicação:
   ```
   mvn spring-boot:run
   ```

5. Acesse a aplicação em:
   ```
   http://localhost:8080
   ```

---

## 📄 Exemplos de BPMN

Os processos BPMN modelados podem ser encontrados na pasta `src/main/resources/bpmn/`. Alguns exemplos:
- **Notificação de Entregadores**: Notificar entregadores disponíveis sobre uma nova encomenda.
- **Atualização de Status**: Gerenciar estados como "em trânsito" e "entregue".

---

## 🌐 Endpoints Principais

- **POST** `/api/encomenda/criar` - Criação de uma nova encomenda.
- **POST** `/api/notificar/entregadores` - Notificação de entregadores disponíveis.
- **GET** `/api/status/{id}` - Consultar status da entrega.
- **POST** `/api/feedback` - Enviar feedback.

---

## 🧪 Testes

Para executar os testes, use o comando:
```
mvn test
```

---

## 🤝 Contribuições
Made with ☕ and 💜:

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/heyliceeee">
        <img src="https://github.com/heyliceeee.png" width="100px;" alt="Foto da Alice Dias no GitHub"/><br>
        <sub>
          <b>Alice Dias</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Pazregina19">
        <img src="https://github.com/Pazregina19.png" width="100px;" alt="Foto da Regina Paz no GitHub"/><br>
        <sub>
          <b>Regina Paz</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
