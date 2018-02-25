package samples.gatherscripts.groovy.news

import groovy.json.JsonBuilder

import java.text.SimpleDateFormat

static def gather(URL url) {

    def feedResponseXML = new XmlSlurper().parseText(url.text)

    def items = feedResponseXML.channel.item
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)

    def entries = []
    items[0..5].each { entry ->

        def e = [:]

        e.id = entry.link.toString()
        e.headline = entry.title.toString()
        e.content = entry.description.toString()

        //TODO: Feed returns Locale.English but should be German?
        e.date = sdf.parse(entry.pubDate.toString().replace("+0100", "+0000"))

        entries.add(e)
    }

    return new JsonBuilder(entries).toString()
}