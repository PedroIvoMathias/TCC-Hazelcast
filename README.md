# TCC-Hazelcast
Trabalho de conclusão de curso referente a Análise de Hazelcast para Aplicações Web
Será necessário ter o docker instalado no computador caso seja necessário executar os códigos.

-------Comandos para preparar e executar o ambiente-------

- Sem o hazelcast
mvn clean package -DskipTests = monta o maven pra rodar o docker
docker build -t backend . = monta o dockerfile
docker compose up -d --build = sobe os containers 

- Com o Hazelcast e 1 cluster
mvn clean package -DskipTests = monta o maven pra rodar o docker
docker build -t backend . = monta o dockerfile
docker compose up -d --build = sobe os containers 

- Com o Hazelcast e 2 clusters
mvn clean package -DskipTests = monta o maven pra rodar o docker
docker build -t backend . = monta o dockerfile
docker compose up -d --scale hazelcast=2 = sobe os containers 

- Porta de acesso ao menagement center: [localhost](http://localhost:8081/)
- Porta de acesso a aplicação http://localhost:8080/teste-api/safar/