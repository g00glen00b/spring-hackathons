# Emoji translator API

REST API written for a hackathon project.
It provides a REST API to translate text to emojis.

The project runs an initial import of various emoji data files.
By default, it uses the following sources:

- [`classpath:manual-mappings.txt`](./src/main/resources/manual-mappings.txt)
- [Unicode Emoji Data](https://unicode.org/Public/emoji/16.0/emoji-test.txt)

This initial import is written with Spring Batch.

## Local development

Run the application with the following configuration:

```shell
./mvnw spring-boot:run
```

Now you can access the API like this:

```shell
curl \ 
  -X GET \
  --location "http://localhost:8080/api/emoji/translation?text=let%27s+get+a+coffee+before+our+meeting"
```

The project also comes with a full integration test, which can be launches with the following command:

```shell
./mvnw test
```

This integration test will verify that the following translations exist:

| Source                                                   | Translation                                      |
|----------------------------------------------------------|--------------------------------------------------|
| `let's get a coffee before our meeting`                  | `let's get a ☕ before our 🤝🗓️`                 |
| `let's order a taxi with our credit card`                | `let's order a 🚕 with our 💳`                   |
| `you need to wear sunglasses because the sun is shining` | `you need to wear 🕶️ because the ☀️ is shining` |

