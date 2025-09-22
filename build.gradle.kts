plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "com.discord.siabot"
version = "3.2.0" // YouTube Music Integration

repositories {
    maven { url = uri("https://jitpack.io") } // JitPack을 가장 위에!
    mavenCentral()
    maven { url = uri("https://m2.dv8tion.net/releases") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("net.dv8tion:JDA:5.0.1")
    implementation("ch.qos.logback:logback-classic:1.5.6")

    // Latest stable community versions
    implementation("com.github.lavalink-devs:lavaplayer:2.2.4")
    implementation("com.github.lavalink-devs:youtube-source:1.13.5")
}

application {
    mainClass.set("com.discord.siabot.SiaBotKt")
}

kotlin {
    jvmToolchain(21)
}