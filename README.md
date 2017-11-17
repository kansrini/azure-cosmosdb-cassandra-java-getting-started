---
services: Cassandra API On Cosmos-DB
platforms: java
author: kansrini
---

# azure-cosmosdb-cassandra-java-getting-started
Azure Cosmos DB is Microsoft’s globally distributed multi-model database service. You can quickly create and query document, key/value, graph databases, and cassandra databases, all of which benefit from the global distribution and horizontal scale capabilities at the core of Azure Cosmos DB.

One of the supported APIs on Azure Cosmos DB is the Cassandra API, which provides a document model and support for client drivers in many platforms like spark, java, c#, python, nodejs etc. This sample shows you how to use the Azure Cosmos DB with the Cassandra DB API to store and access data through datastax java spark-cassandra-connector. It is transparent to the application that the data is stored in Azure Cosmos DB.

## Pre-requesits
- JDK (>= 1.7)
- maven
- Git

## Running this sample

* Before you can run this sample, you must have the following prerequisites:

   * An active Azure account. Create a CosmosDB with Cassandra endpoint API (https://aka.ms/cassapijavaqs)
   * Then, clone this repository using `git clone https://github.com/kansrini/azure-cosmosdb-cassandra-java-getting-started.git`.
   * Run azure-cosmosdb-cassandra-java-getting-started/java-examples/src/main/resources/setup-cassandra.cql to setup initial keyspace on Cassandra DB. you can use CQLSH to run queries as mentioned in the below section
   * Provide the following configuration in azure-cosmosdb-cassandra-java-getting-started/java-examples/src/main/resources/config.properties
   ```
       cassandra_host=<Cassandra endpoint connection host>
       cassandra_port=<Cassandra endpoint connection port>
       cassandra_username=<Cassandra endpoint username>
       cassandra_password=<Cassandra endpoint username>
       ssl_keystore_file_path=<SSL Keystore file path>
       ssl_keystore_password=<SSL Keystore password>
   ```

* Run the Samples under azure-cosmosdb-cassandra-java-getting-started/java-examples/src/main/java/com/azure/cosmosdb/cassandra/examples. 

## About the code
The code included in this sample is intended to get you quickly started with java that connects to Azure Cosmos DB with the Cassandra API.


## Running queries using CQLSH 

These steps needs be used to setup the keyspace and tables to run the examples given &/ to validate the Cosmos-DB APIs via CQLSH shell

1.	Install Python 2.7 on your box.

2.	Download Cassandra binary & install from http://cassandra.apache.org/download/.

3.	Navigate to the bin\ directory of your cassandra install directory (extracted above) 

4.	Run the following to open a CQLSH prompt to run any cassandra queries 
    ```
	set CQLSH_PORT=10350
        set SSL_VERSION=TLSv1_2
        set SSL_VALIDATE=false
        set SSL_CERTFILE=[path-to-ssl-cer]
        cqlsh -u [nameOfCosmosDBAccount] -p [accountKeyOfCosmosDBAccount] -ssl
    ```
    On Windows, run the following:
    ```
    $python\python.exe "{Path to} cassandra\bin\cqlsh.py" <IPADDRESS> <PORT> --ssl --connect-timeout=600 --request-timeout=600 -u <accountName> -p <password> 
    ```
5. To setup keyspace & tables to run the examples use the following command
	```	
    cqlsh -u [nameOfCosmosDBAccount] -p [accountKeyOfCosmosDBAccount] --ssl < <ProjectLocation>/azure-cosmosdb-cassandra-java-getting-started/java-examples/src/main/resources/setup-cassandra.cql
	```
    On Windows, run the following:
	```
    $python\python.exe "{Path to} cassandra\bin\cqlsh.py" <IPADDRESS> <PORT> --ssl --connect-timeout=600 --request-timeout=600 -u <accountName> -p <password> < <ProjectLocation>/azure-cosmosdb-cassandra-java-getting-started/java-examples/src/main/resources/setup-cassandra.cql
    ```

## More information

- [Azure Cosmos DB](https://docs.microsoft.com/azure/cosmos-db/introduction)
- [DataStax spark-cassandra-connector Documentation](https://docs.datastax.com/en/developer/java-driver/)
- [DataStax spark-cassandra-connector Source](https://github.com/datastax/java-driver)
