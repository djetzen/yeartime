package de.djetzen.yeartime

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.library.Architectures.onionArchitecture
import org.junit.jupiter.api.Test

class ArchUnitOnionArchitectureTest {

    var ignoreConfigurations = ImportOption { location ->
        !location.contains("/configuration/")
    }
    private val BASE_PACKAGE: String = "de.djetzen.yeartime";

    private val classes = ClassFileImporter().withImportOption(ignoreConfigurations).importPackages("$BASE_PACKAGE..")

    @Test
    fun onionArchitecturePackageStructureIsFine() {
        onionArchitecture()
            .domainModels("$BASE_PACKAGE.domain.models..")
            .domainServices("$BASE_PACKAGE.domain.services..")
            .applicationServices("$BASE_PACKAGE.application..")
            .adapter("persistence", "$BASE_PACKAGE.adapter.out.persistence..")
            .adapter("rest", "$BASE_PACKAGE.adapter.in.web..")
            .withOptionalLayers(true)
            .check(classes)
    }
}