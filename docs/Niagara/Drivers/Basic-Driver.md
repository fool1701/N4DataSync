# Basic Driver

**API Reference**: `Basic Driver API`

## Overview

This package provides some basic classes that may be useful to developers building a new driver (i.e. field bus driver). These classes can be used (or subclassed) to provide some basic driver functionality, such as worker (queue) management, basic poll schedule handling, basic messages and management of these basic messages through request/response transactions (as well as unsolicited message handling), etc. It also provides a serial implementation (`com.tridium.basicdriver.serial`) which can be subclassed by drivers that use a serial port for communication.

Here is an overview of basicDriver's structure:

```
                BBasicNetwork
               /   /    |    \
              /   /     |     \
  BBasicDevices  /      |   Worker Queues (BBasicWorkers)
                /       |    - dispatcher (used for
               /        |      synchronizing access to Comm)
BBasicPollScheduler     |    - worker (for posting async
                        |      operations, such as learns)
                        |    - write worker (for posting
                        |      async coalescing operations,
                        |      such as writes)
                        |
                      Comm
                     /  |  \
                    /   |   \
       CommReceiver     |   CommTransactionManager
       CommTransmitter  |    - CommTransactions
                        |
                        |
             UnsolicitedMessageListeners
          (registered if needed by network)
```

The abstract class `BBasicNetwork` is the root component of `basicDriver`. It is the base container for `BBasicDevice` objects, and it provides a basic poll scheduler where objects implementing the `BIBasicPollable` interface can register to be polled (i.e. points, devices). It also provides three worker threads (queues) for handling asynchonous operations and synchronization of request messages to the `Comm` for transmission to the output stream (the following outlines the INTENDED use of these worker queues):

Asynchronous operations should be posted onto either the worker queue or write worker queue (coalescing). Write operations should always go to the write worker queue so they will be coalesced. Most other asynchronous operations, such as learns, should be posted to the worker queue to keep the write worker queue free for write operations. As these async operations are processed (dequeued), they should post any necessary message requests to the dispatcher queue, which synchronizes access to the `Comm` (`Comm` is ultimately responsible for sending the request message to the output stream via the `CommTransmitter` and receiving the response message from the input stream via the `CommReceiver`). Other threads may also post directly to the dispatcher queue (for example, the poll thread can post poll message requests directly to the dispatcher queue).

```
    worker queue           write worker queue
        ___
       |___|                     |___|
       |___|                     |___|
       |___|                     |___|
       |___|                     |___|
       |___|                     |___|
         |                         |
         |    dispatcher queue     |
         |          ___            |
         --------+ |___| +----------
   --------------+ |___|
   |               |___| (The dispatcher queue
  poll             |___|  is intended to handle
  requests         |___|  message requests only)
                     |
                     |
                     |
                     ---------+ send a request message
                                to Comm to transmit to
                                the output stream, and
                                wait for and return
                                any response from the
                                input stream.
```

### Supporting Classes

`BBasicNetwork` also handles initialization, starting, and stopping the `Comm`, or communication handler. `Comm` is used to manage request/response message transactions for the network, handles the interaction between the low-level transmitter and receiver, and routes any unsolicited received messages to the appropriate listener. `Comm` uses the following supporting classes to accomplish its tasks:

-   **CommTransactionManager**: provides a pool of `CommTransaction` objects that are used for request/response message matching. Matching a request message to a response message is determined through an `Object` tag on the `Message` (discussed below).
-   **CommReceiver**: an abstract class implementing `Runnable` which handles receiving and forming `ReceivedMessages` from the input stream. Subclasses must override the `receive()` abstract method to read and return a complete `ReceivedMessage`. `CommReceiver` will loop and continuously call `receive()` in order to receive messages. Once a complete `ReceivedMessage` is received, this class routes the `ReceivedMessage` back up to the `Comm` for further processing. The returned `ReceivedMessage` may also need to contain data for request/response message matching (tag data) and unsolicited message listener processing (unsolicited listener code).
-   **CommTransmitter**: provides access and synchronization for writing `Messages` (and/or bytes) to the output stream.
-   **UnsolicitedMessageListener**: `Comm` can store a list of objects implementing this interface in order to process unsolicited received messages. `UnsolicitedMessageListener` objects can be registered to the `Comm` with an unsolicited listener code key. Then when a `ReceivedMessage` is received and determined to be unsolicited, it can match the unsolicited listener code to determine which `UnsolicitedMessageListener` instance should handle the `ReceivedMessage`.
-   **MessageListener**: This is a helper interface that should be implemented by objects that wish to receive a response `Message`. When using the `sendAsync()` or `sendAsyncWrite()` convenience methods of `BBasicNetwork`, they require a parameter of type `MessageListener` in order to determine where to route the response `Message`.

### Messages

The `com.tridium.basicdriver.message` package contains classes useful for building driver messages (using the `Message` abstract class), allowing these `Messages` to be written to the output stream, and formatting a response received (`ReceivedMessage`) into a proper `Message`.

-   **Message**: an abstract class for wrapping a driver message and providing some methods necessary for handling a response to this message. At a minimum, subclasses will need to provide the implementation for writing the message to the output stream and determine how a response (`ReceivedMessage`) should be interpreted and formed into a `Message`.
-   **ReceivedMessage**: an abstract class for wrapping a received driver message and providing some methods for determining if it is unsolicited and/or the unsolicited listener code to use for finding the correct `UnsolicitedMessageListener` if the message is determined to be unsolicited. Subclasses should provide a means to serve the appropriate data to form a complete `Message`.

### Utility Classes

The `com.tridium.basicdriver.util` package contains utility classes useful to most drivers.

-   **BasicException**: an extension of `BajaRuntimeException`, a `BasicException` can be thrown when an error occurs in the driver.
-   **BBasicWorker**: an extension of `BWorker`, it manages a basic worker thread for a queue. Used by the `BBasicNetwork` for the asynchronous worker.
-   **BBasicCoalescingWorker**: an extension of `BBasicWorker`, it manages a basic worker thread for a coalescing queue. Used by the `BBasicNetwork` for the asynchronous write worker.
-   **BBasicPollScheduler**: an extension of `BPollScheduler`, it handles subscribing, unsubscribing, and polling of `BIBasicPollable` objects.
-   **BIBasicPollable**: an extension of `BIPollable`, this interface should be implemented by any objects that wish to register to receive poll requests from the `BBasicPollScheduler`. Subclasses of `basicDriver` can use this to poll any devices, points, etc. as needed.

## Serial Driver

The `com.tridium.basicdriver.serial` package contains classes useful to most serial drivers (with the communication handler, `Comm`, at the network level).

-   **BSerialNetwork**: an extension of `BBasicNetwork` that supports serial communication on a single configurable serial port. This abstract class can be subclassed to provide a frozen property of type `BSerialHelper`. This property, called 'Serial Port Config', provides an end user the ability to configure a serial port and its settings (i.e. baud rate, data bits, etc.) to use for communication with devices on the serial network.
-   **SerialComm**: an extension of `Comm` that handles opening the user selected serial port as well as the input and output streams to that port. It is used by the `BSerialNetwork` to handle synchronization of the serial communication.
