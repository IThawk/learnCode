package cn.wanghaomiao.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import org.jsoup.nodes.Element;
import org.seimicrawler.xpath.JXDocument;

import java.util.List;

/**
 * @author 爬虫古诗词
 */
@Crawler(name = "basic_b")
public class BasicOld extends BaseSeimiCrawler {
    @Override
    public String[] startUrls() {
        //两个是测试去重的
        return new String[]{"https://m.gushici.net/","https://m.gushici.net//index_2.html","https://m.gushici.net/index_3.html"};
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//*[@class=\"gushici-box\"]");
//            Object urls = doc.selOne("/html/body/div[3]");
            logger.info("{}", urls.size());
            for (Object s:urls){
                org.jsoup.nodes.Element doci= (org.jsoup.nodes.Element) s;
                String gushiciName = doci.getElementsByTag("p").first().getElementsByTag("a").first()
                        .getElementsByTag("b").text();

                String source1 = doci.getElementsByClass("source").last().getElementsByTag("a").first()
                        .text();

                String source2 = doci.getElementsByClass("source").last().getElementsByTag("a").last()
                       .text();
                String centext = doci.getElementsByClass("gushici-box-text").first().text();

//                push(Request.build(s.toString(), BasicOld::getTitle));
                logger.info("诗词名:{} ",gushiciName);
                logger.info("朝代:{} ",source1);
                logger.info("作者:{} ",source2);
                logger.info("内容:{} ",centext);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTitle(Response response){
        JXDocument doc = response.document();
        try {
            logger.info("url:{} {}", response.getUrl(), doc.sel("/html/body/div[4]/div[2]/div/p[1]/a/b"));
            logger.info("url:{} {}", response.getUrl(), doc.sel("/html/body/div[4]/div[2]/div/div"));
            //do something
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
