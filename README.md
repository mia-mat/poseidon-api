# Poseidon API
[![Latest](https://img.shields.io/nexus/r/ws.mia/poseidon-api?server=https://parthenon.mia.ws&label=latest)](https://parthenon.mia.ws/#browse/browse:maven-releases:ws%2Fmia%2Fposeidon-api)  
Models and HTTP client for [Poseidon](https://gh.mia.ws/poseidon-core).

## Download

**Maven** - add to your `pom.xml`:
```xml
<repositories>
    <repository>
        <id>nexus-public</id>
        <url>https://parthenon.mia.ws/repository/maven-public/</url>
    </repository>
</repositories>

<dependencies>
<dependency>
    <groupId>ws.mia</groupId>
    <artifactId>poseidon-api</artifactId>
    <version>1.2.0</version>
</dependency>
</dependencies>
```

**Gradle** - add to your `build.gradle`:
```groovy
repositories {
    maven { url 'https://parthenon.mia.ws/repository/maven-public/' }
}

dependencies {
    implementation 'ws.mia:poseidon-api:1.2.0'
}
```

## Usage

```java
PoseidonClient client = new PoseidonHttpClient("https://poseidon.example.com");
List<PoseidonContainer> containers = client.getContainers();
```