package informer.consumer

import groovy.json.JsonSlurper
import informer.Information
import informer.sources.Source

import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

abstract class Consumer {

    abstract List<Information> gather()

    static List<Information> gatherInformation(Source source, Object... params) {
        String gatherScriptResponse = callGatherScript(source, params)
        def jsonSlurper = new JsonSlurper()
        def parsedJson = jsonSlurper.parseText(gatherScriptResponse)

        List<Information> informationList = []
        parsedJson.each { sourceContent ->
            Information information = new Information()
            information.id = sourceContent.id
            information.date = new Date()
            information.source = source
            information.content = source.fields.collectEntries{[(it): sourceContent."$it"]}
            informationList << information
        }

        return informationList
    }

    static String callGatherScript(Source source, Object... params) {
        ScriptEngineManager factory = new ScriptEngineManager()
        ScriptEngine engine = factory.getEngineByName(source.gatherScript.engine)
        FileReader scriptFile = new FileReader(source.gatherScript.script)
        engine.eval(scriptFile)
        Invocable invocableEngine = (Invocable) engine
        return invocableEngine.invokeFunction("gather", params)
    }

}
