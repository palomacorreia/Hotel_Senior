
# 🏨: Senior Hotel

# Regras de negócio
- Uma diária no hotel de segunda à sexta custa R$120,00;
- Uma diária no hotel em finais de semana custa R$150,00;
- Caso a pessoa precise de uma vaga na garagem do hotel há um acréscimo diário,
  sendo R$15,00 de segunda à sexta e R$20,00 nos finais de semana;
- Caso o horário da saída seja após às 16:30h deve ser cobrada uma diária extra;

# Requisitos Funcionais
- Padrão REST para comunicação;
- Permitir cadastrar pessoas;
- Permitir realizar o checkin e checkout;
- Permitir consultar ex-hóspedes;
- Postman para testar sua aplicação(Collection no projeto.

# Rotas da Aplicação

- [GET] ```/hotel/HospedesForaHotel``` para retornar ex-hóspedes;
- [GET] ```/hotel/HospedesNoHotel``` para retornar os hóspedes atuais
- [POST] ```/hotel/FazerCheckIn ``` para realizar o check-in e o check-out da reserva identificada pelo id
- [GET] ```/hotel/GravaHospede ``` para cadastrar hospedes.
- [GET] ```/hotel/BuscaHospedes ``` Retorna todos os hospedes cadastrados.
- [GET] ```/hotel/BuscaHospedeFiltro ``` Busca hospede por nome,telefone(numero),documento.

# ⚙: Tecnologias Utilizadas
- Java 17, SpringBoot, Hibernate, JPA,Postgres, Maven,Docker
# 🔖 Intruções para Start do Projeto
- Para Configurar o Banco de dados Postgres,entrar na pasta docker e rodar o comando
``` docker-compose -f postgres.yaml up ```

- Executar scripts de banco de dados na pasta na ordem 01 e 02
```src/main/resources/sql```
- Start Projeto Spring ```ProjetoHotelApplication```
- Chamadas aos endpoints

```Postman/CheckIn.postman_collection.json```

```Postman/Hospedes.postman_collection.json```