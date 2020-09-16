package com.emard.sigrhp;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.emard.sigrhp");

        noClasses()
            .that()
                .resideInAnyPackage("com.emard.sigrhp.service..")
            .or()
                .resideInAnyPackage("com.emard.sigrhp.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.emard.sigrhp.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
