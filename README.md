# Getting Started

## Project Goal

This project was built to simulate a scenario where consumer extrapolates the maximum time to consume a message batch (max.poll.interval.ms).  
As described in Apache Kafka documentation, by default the Kafka consumer has a limit of 5 minutes to read each message batch polled, furthermore each batch has 500 messages.
When consumer polls a batch message, it also means to the broker that the consumer health is ok. However, when it extrapolates this times without to poll a new batch, 
the broker consider consumer as dead and trigger a rebalance. The consumer rebalance can cause an error at the moment to commit the read messages (CommitFailedException) and make it
with that the messages be read again.

# Project Scenario

The project uses an abstraction of food delivery store to simulate the problem. 
Through a POST request, a custom number of orders are generated and populate the project the database and topic. A consumer reads the messages from topic "delivery-orders-topic"
and executing some steps before to update the order status to DELIVERED. The intends is to simulate the work of delivery man for each order.

# Apache Documentation
- https://kafka.apache.org/documentation/#consumerapi

# Stack Overflow
- https://stackoverflow.com/questions/35658171/kafka-commitfailedexception-consumer-exception

If the consumer does not get to read all the batch into this interval, it will not does not send signals to broker fails and cause a commit exception. Hence the messages of this batch will be read again.


