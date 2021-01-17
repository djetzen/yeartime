package de.djetzen.yeartime

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.core.domain.JavaAnnotation
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import com.tngtech.archunit.library.Architectures.onionArchitecture
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.RestController

class ArchUnitTests {

    var ignoreConfigurations = ImportOption { location ->
        !location.contains("/configuration/")
    }
    private val BASE_PACKAGE: String = "de.djetzen.yeartime";

    private val classesWithoutConfigurationPackage =
        ClassFileImporter()
            .withImportOption(ignoreConfigurations)
            .withImportOption(DoNotIncludeTests())
            .importPackages("$BASE_PACKAGE..")

    @Test
    fun onionArchitecturePackageStructureIsFine() {
        onionArchitecture()
            .domainModels("$BASE_PACKAGE.domain.models..")
            .domainServices("$BASE_PACKAGE.domain.services..")
            .applicationServices("$BASE_PACKAGE.application..")
            .adapter("persistence", "$BASE_PACKAGE.adapter.out.persistence..")
            .adapter("rest", "$BASE_PACKAGE.adapter.in.web..")
            .withOptionalLayers(true)
            .check(classesWithoutConfigurationPackage)
    }

    @Test
    fun classesInPortsPackageShouldHavePortOrUseCaseInName() {
        ArchRuleDefinition.classes().that().resideInAPackage("..port..")
            .should().haveNameMatching(".*Port")
            .orShould().haveNameMatching(".*UseCase").check(classesWithoutConfigurationPackage)
    }

    @Test
    fun classesAnnotatedWithRestControllerAnnotationShouldHaveControllerInName() {
        ArchRuleDefinition.classes().that().areAnnotatedWith(RestController::class.java)
            .should().haveNameMatching(".*Controller").check(classesWithoutConfigurationPackage)
    }

    @Test
    fun domainPackageShouldHaveNoDependenciesToSpring() {
        val springAnnotationPredicate: DescribedPredicate<JavaAnnotation<*>?> =
            object : DescribedPredicate<JavaAnnotation<*>?>("Filter for org.spring package") {

                override fun apply(input: JavaAnnotation<*>?): Boolean {
                    return input!!.type.name.startsWith("org.spring")
                }
            }
        ArchRuleDefinition.classes().that().resideInAPackage("..domain..").should()
            .notBeAnnotatedWith(springAnnotationPredicate).check(classesWithoutConfigurationPackage)
    }
}