//
// Generated from archetype; please customize.
//

package nl.iteye.maven.bpmntocamelmojo

import org.codehaus.groovy.maven.mojo.GroovyMojo
import groovy.util.slurpersupport.GPathResult

/**
 * Example Maven2 Groovy Mojo.
 *
 * @goal generate-routes
 */
class GenerateRoutesMojo
    extends GroovyMojo
{
    /**
     * The hello message to display.
     *
     * @parameter expression="${message}" default-value="Hello World"
     */
    String message
    
    void execute() {
        println "${message}"
        def File f = new File('')
        def File bpmnSrcDir = new File(f.absolutePath.toString() + "/src/main/bpmn")
        def File javaGenDir = new File(f.absolutePath.toString() + "/generated/src/main/java/bpmnroutes")
        javaGenDir.mkdirs()
        bpmnSrcDir.listFiles({dir, file-> file==~/.*?\.xml/} as FilenameFilter).each{
            println "it: ${it}"
            String src = convertBpmnXml(it)
            def File dest = new File(javaGenDir.absolutePath.toString() + "/BpmnRouteBuilder.java")
            dest.write("//BPMN file ${it}")
            dest << src
        }
        println "Current dir: ${f.absolutePath}"

    }

    String convertBpmnXml(bpmnXmlFile){
        println "Converting ${bpmnXmlFile}"
        def bpmn = new XmlSlurper().parse(bpmnXmlFile)
        println "Process: ${bpmn.process.@id}"
        def routes = ""
        bpmn.process.task.each{
            def fromEndpoint = mapEventIdToName(it.@id)
            def toEndpoint = "seda:2"
            routes += """
from("${fromEndpoint}")
    .process(new Processor(){ /* ${it.@name} */
         @Override
         public void process(Exchange exchange) throws Exception {
         }
    })
    .to("${toEndpoint}");
"""
        }
        def classCode = """
package bpmnroutes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        ${routes}
    }
}
"""
        println classCode
        return classCode
    }

    String mapEventIdToName(GPathResult bpmnModel, String id){
        eventNode = bpmnModel.findAll{ it.@id == id }
        println "Found event node: ${eventNode} for id: ${id}"
        return eventNode.@name
    }
}
