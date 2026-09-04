# EDA Roadmap

To test all the dynamic scenarios in each of the setup scenarios

## Setup scenarios

### Scenario 1

- Single replica of single producer `spring-eda-src`
- Single replica of single consumer `spring-eda-tgt`

### Scenario 2

- Multiple (3) replicas of single producer `spring-eda-src`
- Mulitple (3) replicas of single consumer `spring-eda-tgt`
  - an event should only be processed by one replica of the consumer

### Scenario 3

- Multiple (3) replicas of single producer `spring-eda-src`
- Mulitple (3) replicas of two consumers A and B `spring-eda-tgt`
  - add some dynamic prop to override during image build which changes what schema it writes data to
  - consumers A and B act as distinct consumer groups
  - an event should only be processed by one replica of consumer A and one replica of consumer B

---

## Dynamic scenarios

### Scenario A

- Basic event passing from A to B
- Test consumers pulling one event at a time
- Test consumers pulling multiple events at a time

### Scenario B

- Duplicate and out-of-order events passed from A to B to test idempotency and ordered executions
- Test consumers pulling one event at a time
- Test consumers pulling multiple events at a time

### Scenario C

- Producer starts sending a large number of events with consumers processing them at normal speed
- Test consumers pulling one event at a time
- Test consumers pulling multiple events at a time

### Scenario D

- Producer starts sending a large number of events with consumers processing them at slower speed (add a Thread.sleep() in consumer) to test backpressure if any
- Test consumers pulling one event at a time
- Test consumers pulling multiple events at a time

### Scenario E

- Producer sends event which will fail the first 2 times to test retries, exponential backoffs and jitter if required
- Test consumers pulling one event at a time (the failure event can be somewhere in the middle)
- Test consumers pulling multiple events at a time

### Scenario F

- Producer sends event which will fail all the time and after 3 retries by any consumer group, must get routed to a dead-letter queue
- Test consumers pulling one event at a time (the failure event can be somewhere in the middle)
- Test consumers pulling multiple events at a time
- Test what happens if all consumers in one consumer group are down while others are up
- Test what happens if all consumers in all consumer groups are down
- Test what happens when one or more Kafka brokers are down

---

## Completion Matrix

Specifies how much of each scenario has been completed (number in bracket specifies how many bullets done)


|             |  Scenario 1   |   Scenario 2    |   Scenario 3    |
|-------------|---------------|-----------------|-----------------|
| Scenario A  |     Y(2)      |                 |                 |
| Scenario B  |               |                 |                 |
| Scenario C  |               |                 |                 |
| Scenario D  |               |                 |                 |
| Scenario E  |               |                 |                 |
| Scenario F  |               |                 |                 |
|

---
