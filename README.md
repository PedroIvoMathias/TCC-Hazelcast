# TCC-Hazelcast
Trabalho de conclusão de curso referente a Análise de Hazelcast para Aplicações Web
Será necessário ter o docker instalado no computador caso seja necessário executar os códigos.

-------Comandos para preparar e executar o ambiente-------

- Sem o hazelcast</br>
mvn clean package -DskipTests = monta o maven pra rodar o docker</br>
docker build -t backend . = monta o dockerfile</br>
docker compose up -d --build = sobe os containers </br>

- Com o Hazelcast e 1 cluster</br>
mvn clean package -DskipTests = monta o maven pra rodar o docker</br>
docker build -t backend . = monta o dockerfile</br>
docker compose up -d --build = sobe os containers </br>

- Com o Hazelcast e 2 clusters</br>
mvn clean package -DskipTests = monta o maven pra rodar o docker</br>
docker build -t backend . = monta o dockerfile</br>
docker compose up -d --scale hazelcast=2 = sobe os containers </br>

- Porta de acesso ao menagement center: http://localhost:8081/ </br>
- Porta de acesso a aplicação http://localhost:8080/teste-api/safar/</br>
