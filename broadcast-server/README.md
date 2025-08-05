## How to use

First build the project:

```shell
./mvnw clean package
```

Then run the server by using:

```shell
./broadcast-server.sh --spring.profiles.active=server
```

Then run a client by using:

```shell
./broadcast-server.sh --spring.profiles.active=client --username=Alice
```

And run another client by using:

```shell
./broadcast-server.sh --spring.profiles.active=client --username=Bob
```

Both clients will now allow messages to be sent through `System.in` and will be broadcasted to all other clients.