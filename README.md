# ms-casestudy
1.Microservice Case Study Unit testing docuemnts uploaded in git hub under this folder "UnitTestingDocuements".

2.To run this application we need to create tables in MQSQL and details are in properties file

Need to copy the could config files under this folder "D:\cloud-local", which is available in folder "cloudnativeconfigfiles"

Start the application in the below sequence 
Spring-cloud-config-server
ServiceDiscoveryServer
LibrarySubscriptionService
LibraryBookService
LibraryUserService

3.If we want o access service via APIGateway , start the below
APIGateway

4.For ELK , i wrote all my logs under this path "E:\arul\elk" which ia having logs for these service LibrarySubscriptionService,LibraryBookService,LibraryUserService.

5.Have the below configuration in logstash.conf and start, logstash, elasticsearch and Kibana. Able to view all the three services logs in kibana

# Sample Logstash configuration for receiving
# UDP syslog messages over port 514
input {
  file {
    path => "e:/arul/elk/Book-Subscription-Services.log"
    start_position => "beginning"
  }
  file {
    path => "e:/arul/elk/Library-Book-Services.log"
    start_position => "beginning"
  }
  file {
    path => "e:/arul/elk/Library-User-Services.log"
    start_position => "beginning"
  }
}

output {
  elasticsearch { hosts => ["localhost:9200"] }
  stdout { codec => rubydebug }
}
