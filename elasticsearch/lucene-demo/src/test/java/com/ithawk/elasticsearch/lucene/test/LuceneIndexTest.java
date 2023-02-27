package com.ithawk.elasticsearch.lucene.test;

import com.ithawk.demo.elasticsearch.lucene.FullTextSearchDempApplication;
import com.ithawk.demo.elasticsearch.lucene.bean.JobInfo;
import com.ithawk.demo.elasticsearch.lucene.service.JobInfoServiceImpl;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.ReactiveTransactionManager;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class) //springboot做junit测试固定的写法
@SpringBootTest(classes = FullTextSearchDempApplication.class)
public class LuceneIndexTest {

    @Autowired
    private JobInfoServiceImpl jobInfoService;

    /**
     * 创建索引
     */
    @Test
    public void create() throws Exception {

        JobInfo jobInf = new JobInfo();
        jobInf.setId(1L);
        jobInf.setJobInfo("mytest");
        jobInf.setJobAddr("ces");
        jobInf.setCompanyInfo("my");
        jobInf.setSalary(100);
        jobInf.setUrl("http");
        jobInf.setTime(new Date());
        jobInf.setCompanyName("合并");
        jobInf.setCompanyAddr("baoan");
        jobInf.setJobName("my");
        jobInfoService.delete();
        jobInfoService.insert(jobInf);
        //1.指定索引文件的存储位置，索引具体的表现形式就是一组有规则的文件
        Directory directory = FSDirectory.open(new File("c:/mydata/test/index"));
        //2.配置版本及其分词器  IK
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        //3.创建IndexWriter对象，作用就是创建索引
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //先删除已经存在的索引库
        indexWriter.deleteAll();
        //4.获得索引源/原始数据
        List<JobInfo> jobInfoList = jobInfoService.selectAll();
        //5. 遍历jobInfoList，每次遍历创建一个Document对象
        for (JobInfo jobInfo : jobInfoList) {
            //创建Document对象
            Document document = new Document();
            //创建Field对象，添加到document中
            document.add(new LongField("id", jobInfo.getId(), Field.Store.YES));
            //切分词、索引、存储
            document.add(new TextField("companyName", jobInfo.getCompanyName(), Field.Store.YES));
            document.add(new TextField("companyAddr", jobInfo.getCompanyAddr(), Field.Store.YES));
            document.add(new TextField("companyInfo", jobInfo.getCompanyInfo(), Field.Store.YES));
            document.add(new TextField("jobName", jobInfo.getJobName(), Field.Store.YES));
            document.add(new TextField("jobAddr", jobInfo.getJobAddr(), Field.Store.YES));
            document.add(new TextField("jobInfo", jobInfo.getJobInfo(), Field.Store.YES));
            document.add(new IntField("salary", jobInfo.getSalary(), Field.Store.YES));
            document.add(new StringField("url", jobInfo.getUrl(), Field.Store.YES));
            //将文档追加到索引库中
            indexWriter.addDocument(document);
        }
        //关闭资源
        indexWriter.close();
        System.out.println("create index success!");
    }


    @Test
    public void query() throws Exception {
        //1.指定索引文件的存储位置，索引具体的表现形式就是一组有规则的文件
        Directory directory = FSDirectory.open(new File("c:/mydata/test/index"));
        //2.IndexReader对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //3.创建查询对象，IndexSearcher
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //使用term,查询公司名称中包含"北京"的所有的文档对象
        Query query = new TermQuery(new Term("companyName", "测试"));
        TopDocs topDocs = indexSearcher.search(query, 100);
        //获得符合查询条件的文档数
        int totalHits = topDocs.totalHits;
        System.out.println("符合条件的文档数：" + totalHits);
        //获得命中的文档  ScoreDoc封装了文档id信息
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            //文档id
            int docId = scoreDoc.doc;
            //通过文档id获得文档对象
            Document doc = indexSearcher.doc(docId);
            System.out.println("id:" + doc.get("id"));
            System.out.println("companyName:" + doc.get("companyName"));
            System.out.println("companyAddr:" + doc.get("companyAddr"));
            System.out.println("companyInfo:" + doc.get("companyInfo"));
            System.out.println("jobName:" + doc.get("jobName"));
            System.out.println("jobInfo:" + doc.get("jobInfo"));
            System.out.println("*******************************************");
        }
        //资源释放
        indexReader.close();
    }

}

