# Registration of the [Emission](https://github.com/MoodMinds/emission) and [Reactive Streams Publishable](https://github.com/MoodMinds/reactive-streams-publishable) reactive interfaces in [Spring](https://spring.io)

Registration of the [Emission](https://github.com/MoodMinds/emission)'s `Emittable`
and [Reactive Streams Publishable](https://github.com/MoodMinds/reactive-streams-publishable)'s `Publishable`
in Spring's `ReactiveAdapterRegistry` for Reactive context.

## Usage

Include provided `ReactiveAdapterRegistration` in your Spring configuration.

## Maven configuration

Artifacts can be found on [Maven Central](https://search.maven.org/) after publication.

```xml

<dependency>
    <groupId>org.moodminds.emission</groupId>
    <artifactId>emission-spring</artifactId>
    <version>${version}</version>
</dependency>
```

## Building from Source

You may need to build from source to use **Emission Spring** (until it is in Maven Central) with Maven and JDK 1.8 at least.

## License
This project is going to be released under version 2.0 of the [Apache License][l].

[l]: https://www.apache.org/licenses/LICENSE-2.0