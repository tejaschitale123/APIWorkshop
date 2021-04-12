# APIWorkshop

## Pre-requisite

- JDK 15
- Lombok plugin

## Usage

### Setting Tokens

#### Set tokens as Environment variables

- `client_id`: Set your Client ID
- `client_secret`: Set your Client secret
- `refresh_token`: Set your refresh token
- `access_token`: Set access token

#### Set tokens in `config.properties` file

The config file is located in folder `src/test/resources`. If any value in this config file is empty, the framework will
automatically look in Environment variables with same key. If there is no environment variables declared for the key, an
Error will be thrown.
