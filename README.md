
# Work in progress, not uploaded to bintray/jcenter yet

xsd2java gradle plugin
=========

Gradle plugin for generating java from xsd schemas

### Issues
If you have any issues with the plugin, please file an issue at github, https://github.com/nilsmagnus/wsdl2java/issues

### Contribution
Contributions are welcome as long as they are sane.

#### Contributors
- Nils Larsgård , https://github.com/nilsmagnus
- Mats Faugli, https://github.com/fowlie
- Thorben Schiller, https://github.com/thorbolo
- Stefan Kloe, https://github.com/Pentadrago
- Mattias Rundgren, https://github.com/matrun
- "s-doering", https://github.com/s-doering

### CXF
This plugin uses the apache-cxf tools to do the actual work.

### Tasks

| Name | Description | Dependecy |
| ---- | ----------- | --------- |
| wsdl2java | Generate java source from wsdl-files | CompileJava depends on wsdl2java |
| ~~xsd2java~~ | ~~Generate java source from xsd-files~~ Removed in version 0.8 | ~~CompileJava depends on xsd2java~~ |
| deleteGeneratedSources | Delete all generated sources | Clean depends on deleteGeneratedSources |

## Usage

To use this plugin, you must
- modify your buildscript to have dependencies to the plugin
- apply the plugin
- set the properties of the plugin
- add the generated sources to your sourceset

### Applying the plugin

    buildscript{
        repositories{
            jcenter()
            mavenCentral()
        }
        dependencies {
            classpath 'no.nils:xsd2java:0.8'
        }
    }
    apply plugin: 'no.nils.xsd2java'



 

### Options for xsd2java (deprecated, separate plugin coming soon)
This will not work for version 0.8+!
| Option | Default value | Description |
| ------ | ------------- | ----------- |
| generatedXsdDir | "generatedsources/src/main/java" | Destination directory for generated sources |
| xsdsToGenerate | null | 2-d array consisting of 2 or 3 values in each array: 1. xsd-file(input), 2. package for the generated sources, 3. (optional) a map containing additional options for the xjc task |
| encoding | platform default encoding | Set the encoding name for generated sources, such as EUC-JP or UTF-8. |

Example setting of options:

    xsd2java{
        encoding = 'utf-8'
        xsdsToGenerate = [
            ["src/main/resources/xsd/CustomersAndOrders.xsd", 'no.nils.xsd2java.sample', [header: false] /* optional map */]
        ]
        generatedXsdDir = file("generatedsources/xsd2java")
    }


## Complete example usage
This is a an example of a working build.gradle for a java project. You can also take a look at this projects submodule "consumer" which has a working wsdl compiling.

    buildscript{
        repositories{
            jcenter()
            mavenCentral()
        }
        dependencies {
            classpath 'no.nils:wsdl2java:0.8'
        }
    }

    apply plugin :'java'
    apply plugin :'no.nils.xsd2java'

    repositories{
        mavenCentral()
    }

    dependencies(){
        testCompile 'junit:junit:+'
    }

    wsdl2java{
        wsdlsToGenerate = [
                ["$projectDir/src/main/resources/wsdl/stockqoute.wsdl"]
        ]
        generatedWsdlDir = file("$projectDir/generatedsources")
        wsdlDir = file("$projectDir/src/main/resources/wsdl")
        cxfVersion = "3.0.1"
    }
    xsd2java{
        xsdsToGenerate = [
            ["$projectDir/src/main/resources/xsd/CustomersAndOrders.xsd", 'no.nils.xsd2java.sample']
        ]
        generatedXsdDir = file("$projectDir/generatedsources/xsd2java")
    }

### A notice on multi-module projects

Instead of referring to absolute paths in your build-file, try using $projectDir as a prefix to your files and directories. As shown in the "Complete example usage".
