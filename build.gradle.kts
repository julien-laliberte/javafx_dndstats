plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dursahn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.10.2"
val javafxLibPath = "C:/Program Files/Java/javafx-sdk-21.0.7/lib"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("dursahn.dndstats")
    mainClass.set("dursahn.dndstats.Launcher")
}

javafx {
    version = "21.0.7"
    modules = listOf("javafx.controls", "javafx.fxml")
    sdk = "../javafx-sdk-21.0.7" // Chemin relatif vers le SDK
}

dependencies {
    implementation("io.github.palexdev:materialfx:11.17.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// ShadowJar pour créer un jar fat
tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveClassifier.set("fat")
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}

// Task pour lancer l'application avec JavaFX
tasks.register<JavaExec>("runWithJavaFX") {
    group = "application"
    description = "Run the JavaFX application with module-path and add-modules"

    mainClass.set(application.mainClass)
    classpath = sourceSets.main.get().runtimeClasspath

    jvmArgs = listOf(
        "-Dprism.order=sw",
        "--show-module-resolution",
        "--module-path", "${javafxLibPath}${File.pathSeparator}${sourceSets.main.get().runtimeClasspath.asPath}",
        "--add-modules", "javafx.controls,javafx.fxml,io.github.palexdev.materialfx"
    )
}

// JLink configuration
jlink {
    imageZip.set(layout.buildDirectory.file("distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
        jvmArgs = listOf("-Dprism.order=sw", "-Dprism.verbose=true") // ajoute verbose pour debug
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
