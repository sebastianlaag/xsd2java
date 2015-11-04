package no.nils.xsd2java

import org.junit.Test
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

import static org.junit.Assert.assertTrue

class Wsdl2JavaPluginTest{

	@Test
    public void canAddWsdlTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('xsd2java', type: Wsdl2JavaTask)
        assertTrue(task instanceof Wsdl2JavaTask)
    }

    @Test
    public void wsdl2javaPluginAddsWsdl2javaTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'no.nils.xsd2java'

        assertTrue(project.tasks.wsdl2java instanceof Wsdl2JavaTask)
    }
}