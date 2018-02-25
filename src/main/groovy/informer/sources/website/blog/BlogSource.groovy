package informer.sources.website.blog

import informer.sources.Source

class BlogSource extends Source {

    URL url
    List<String> fields = ['headline', 'content', 'date']

}
