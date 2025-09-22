plugins {
    id("java")
    application
}
application{
    mainClass.set("org.example.Main")
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // Укажите вашу версию Java
    }
}
tasks.jar {
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get(),
            "Implementation-Version" to project.version
        )
    }
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
tasks.register<Jar>("fatJar") {
    archiveBaseName.set("${project.name}-fat")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }

    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}