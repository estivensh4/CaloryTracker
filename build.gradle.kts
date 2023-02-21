import com.android.build.gradle.internal.tasks.factory.dependsOn
import kotlinx.kover.api.KoverMergedConfig
import kotlinx.kover.api.KoverMergedHtmlConfig
import kotlinx.kover.api.KoverTaskExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.hiltAndroidGradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath("org.jacoco:org.jacoco.core:0.8.7")
        classpath("org.jetbrains.kotlinx:kover:0.6.1")
        classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:4.0.0.2929")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
apply(plugin = "org.sonarqube")
apply(plugin = "kover")

koverMerged {
    enable()  // create Kover merged reports
    xmlReport {
        onCheck.set(true)
    }

    htmlReport {
        onCheck.set(true)
    }

    verify {
        onCheck.set(true)
    }
    filters {
        classes {
            excludes += listOf(
                "dagger.hilt.internal.aggregatedroot.codegen.*",
                "hilt_aggregated_deps.*",
                "*ComposableSingletons*",
                "*_HiltModules*",
                "*Hilt_*",
                "*BuildConfig",
                ".*_Factory.*",
                "*model.*",
                "*di.*",
                "*presentation.*",
                "*navigation.*",
                "*theme.*",
                "*core_ui.*",
                "*util.*",
                "*dto.*",
                "*entity.*",
                "*calorytracker.*",
            )
        }
    }
}


plugins {
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
    id("org.sonarqube") version "4.0.0.2929"
}

sonarqube {
    properties {
        property("sonar.projectKey", "test2")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.organization", "sistecredito")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.login", "sqp_47cced29d1635c164f1bf1e1e62054dd484929bb")
        //property "sonar.login", "admin"
        //property "sonar.junit.reportsPath", "${project.buildDir}/test-results/testDebugUnitTest"
        property("sonar.java.coveragePlugin", "kover")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.sources", "src/main/java")
        property("sonar.tests", "src/test/java")
        //property "sonar.android.lint.report", "${project.buildDir}/reports/lint-results.xml"
        //property "sonar.jacoco.reportPaths", "${project.buildDir}/jacoco/*.exec"
        //property "sonar.jacoco.itReportPath", fileTree(dir: project.projectDir, includes: ["**/*.ec"])
        property("sonar.coverage.jacoco.xmlReportPaths", "${project.buildDir}/reports/cover/merged/xml/report.xml")

    }
}

tasks.sonarqube.dependsOn(":koverMergedReport")


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}