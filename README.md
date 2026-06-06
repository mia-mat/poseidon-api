# Poseidon API
Models and HTTP client for [Poseidon](https://gh.mia.ws/poseidon-core).

## Usage

```java
PoseidonClient client = new PoseidonHttpClient("https://poseidon.example.com");
List<PoseidonContainer> containers = client.getContainers();
```