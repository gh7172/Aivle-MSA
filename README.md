# 

## Model
www.msaez.io/#/213892863/storming/fb65ba0f34bba325f3ffdbc549f9bee4

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- author
- publish
- books
- subscription
- user
- point
- generation
- purchase
- usercenter
- read


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- author
```
 http :8088/작가 userId="userId"penName="penName"portfolio="portfolio"selfIntroduction="selfIntroduction"
```
- publish
```
 http :8088/집필 bookId="bookId"userId="userId"title="title"publishDate="publishDate"summary="	summary"state="state"
```
- books
```
 http :8088/도서 bookId="bookId"userId="userId"title="title"publishDate="publishDate"plot="plot"summary="summary"coverImage="coverImage"
```
- subscription
```
 http :8088/subscribes userId="userId"subscriptionExpiryDate="subscriptionExpiryDate"
```
- user
```
 http :8088/유저 userId="userId"loginId="loginId"password="password"isAuthor="isAuthor"
```
- point
```
 http :8088/points userId="userId"points="points"
```
- generation
```
 http :8088/집필요청 bookId="bookId"summary="summary"imageUrl="imageUrl"
```
- purchase
```
 http :8088/pays id="id"userId="userId"bookId="bookId"
```
- usercenter
```
```
- read
```
 http :8088/ 
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
