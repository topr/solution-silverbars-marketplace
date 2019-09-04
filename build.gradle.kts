plugins {
    java
    groovy
}

repositories {
    jcenter()
}

dependencies {
    testImplementation("org.codehaus.groovy:groovy-all:2.5.4")
    testImplementation("org.spockframework:spock-core:1.2-groovy-2.5")
    testImplementation("junit:junit:4.12")
    testImplementation("nl.jqno.equalsverifier:equalsverifier:3.1.9")
    testImplementation("org.mutabilitydetector:MutabilityDetector:0.10.2")
}