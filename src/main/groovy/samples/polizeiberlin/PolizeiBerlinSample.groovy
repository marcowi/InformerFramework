package samples.polizeiberlin

import informer.Information
import informer.consumer.news.SimpleNews
import informer.sources.website.blog.BlogSource
import informer.utils.GatherScript

class PolizeiBerlinSample {


    static void main(String[] args) {

        List<Information> gatheredInformationList = []

        BlogSource blogSource = new BlogSource()
        blogSource.name = "Polizeimeldungen Berlin"
        blogSource.url = new URL("https://www.berlin.de/polizei/polizeimeldungen/index.php/rss")
        blogSource.gatherScript = new GatherScript(engine: "groovy", script: "src/main/groovy/samples/gatherscripts/groovy/news/polizeiberlin.groovy")

        SimpleNews polizeiBerlinNews = new SimpleNews([blogSource])


        def runs = 0
        while (runs < 3) {
            println "########################### run $runs"
            List<Information> informationList = polizeiBerlinNews.gather()
            informationList.each { information ->
                if (!gatheredInformationList.find {it.id == information.id}) {
                    gatheredInformationList << information
                    println "$information.id | $information.content.headline"
                }
            }
            println ""

            sleep(2000)
            runs++
        }

    }

}
