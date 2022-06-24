# Weakness

Kotlin weak properties

### Introduction

One of the unwanted features of classes is that you cannot
add additional data in an object without declaring a field
in its class.

This feature is unwanted when making a library since a
library might need to tag user define objects to process
them automagically without the user knowing how or implement
extra interfaces.

This feature is inescapable. Or, is it?

This library achieves this feature by storing the data in
a global instance. A `Map` or more specifically a
`WeakHashMap` to deal with garbage collection.

Additionally, this library adds more utilities to take
more advantage from this library in kotlin.

### Install

The main way of installing this library is
using `jitpack.io`

```kts
repositories {
    // ...
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Replace TAG with the desired version
    implementation("org.cufy:weakness:TAG")
}
```

### Setup and Usage

The following is basic usage example:

```kotlin
class Entity

var Entity.name: String by WeakProperty()
```

Or you can define your closed-scoped properties

```kotlin
class Entity

val MyWeakness = Weakness()

var Entity.closedName: String by MyWeakness.WeakProperty("name")
```
