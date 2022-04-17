# Spring Boot AWS - Secret Manager (KMS)

Implantado exemplo que solicita às secrets de uma instância do AWS RDS e injeta em um Datasource customizado.

As credenciais para login no AWS Secret Manager podem ser cedidas por duas fontes, quando o 'spring.profiles.active'
está em 'dev' viram do arquivo /.aws/credentials, quando 'prod' viram via variáveis de ambientes.

