package samples.gatherscripts.groovy.news

import groovy.json.JsonBuilder

import java.text.SimpleDateFormat

static def gather(URL url) {

    def feedResponseXML = new XmlSlurper().parseText(url.text)

    def items = feedResponseXML.entry

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)

    def entries = []
    items[0..5].each { entry ->

        def e = [:]

        e.id = entry.id.toString()
        e.headline = entry.title.toString()
        e.content = entry.summary.toString()
        //TODO: Feed returns Locale.English but should be German?
        e.date = Date.parse("yyyy-MM-dd'T'HH:mm:ss", entry.published.toString().replace("+01:00", ""))

        entries.add(e)
    }

    return new JsonBuilder(entries).toString()
}