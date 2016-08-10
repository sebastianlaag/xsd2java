package no.nils.xsd2java

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class Xsd2JavaPlugin implements Plugin<Project> {
    public static final String XSD2JAVA = "xsd2java"
    public static final String CLEAN = "deleteGeneratedXsds"

    public static final DEFAULT_DESTINATION_DIR = "build/generatedsources/src/main/java"

    void apply(Project project) {
        // make sure the project has the java plugin
        project.apply(plugin: 'java')

        Configuration xsd2javaConfiguration = project.configurations.maybeCreate(XSD2JAVA)

        // add xsd2java task with group and a description
        project.task(XSD2JAVA,
                type: Xsd2JavaTask,
                group: 'Xsd2Java',
                description: 'Generate java source code from XSD files.') {
            classpath = xsd2javaConfiguration
        }

        // add cleanXsd task with group and a description
        project.task(CLEAN,
                type: CleanTask,
                group: 'Xsd2Java',
                description: 'Delete java source code generated from XSD files.')

        project.afterEvaluate {

            // add jaxb-xjc
            project.dependencies {
                xsd2java 'org.glassfish.jaxb:jaxb-xjc:2.2.11'
				xsd2java 'org.glassfish.jaxb:jaxb-runtime:2.2.11'
            }

            // hook xsd2java into build cycle only if used
            if(project.xsd2java.xsdsToGenerate != null && project.xsd2java.xsdsToGenerate.size() > 0){
                project.sourceSets.main.java.srcDirs += project.xsd2java.generatedXsdDir
                project.compileJava.dependsOn project.xsd2java
            }

            project.clean.dependsOn project.deleteGeneratedXsds
        }
    }
}