## How to use

First build the project:

```shell
./mvnw clean package
```

Then launch the project through the utility Shell script:

```shell
./cache-proxy.sh --proxy.port=3000 --proxy.location=https://dummyjson.com
```

Then invoke any call:

```shell
curl -i http://localhost:3000/quotes/1
```

Do it another time to see the `X-Cache` header turn from MISS to HIT:

```shell
curl -i http://localhost:3000/quotes/1
```

Clear the cache using the utility Shell script:

```shell
./cache-proxy.sh --spring.profiles.active=cache-clear --proxy.port=3000
```

Fire another proxy request to see the `X-Cache` header turn to MISS again:

```shell
curl -i http://localhost:3000/quotes/1
```