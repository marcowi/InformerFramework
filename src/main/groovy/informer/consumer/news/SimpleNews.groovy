package informer.consumer.news

import informer.Information
import informer.consumer.Consumer
import informer.sources.website.blog.BlogSource

class SimpleNews extends Consumer {

    List<BlogSource> sourceList

    SimpleNews(List<BlogSource> sourceList) {
        this.sourceList = sourceList
    }

    @Override
    List<Information> gather() {
        List<Information> informationList = []

        sourceList.each { source ->
            informationList += gatherInformation(source, [source.url])
        }

        return informationList
    }
}
