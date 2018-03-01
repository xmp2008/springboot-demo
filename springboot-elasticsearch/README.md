  Springboot+Elasticsearch整合实例
  ================================

  这篇文章主要介绍Springboot与Elasticsearch整合在一起的简单例子，作为自己学习的一个记录
  #### 使用到的工具：
  1. Spring Boot 1.5.10.RELEASE
  2. Spring Boot Starter Data Elasticsearch 1.5.10.RELEASE
  3. Spring Data Elasticsearch 2.1.10.RELEASE
  4. Elasticsearch 2.4.0
  5. Maven
  6. Java 8
  
  ``注意 ``：Spring Boot 1.5.10.RELEASE和Spring Data Elasticsearch 2.1.10.RELEASE只支持Elasticsearch 2.4.0，不支持最新的ElasticSearch 5.x版本
  详见：[Spring Data Elasticsearch Spring Boot version matrix](https://github.com/spring-projects/spring-data-elasticsearch/wiki/Spring-Data-Elasticsearch---Spring-Boot---version-matrix)
  
  ## 1.项目依赖：
  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  	<modelVersion>4.0.0</modelVersion>
  
  	<groupId>com.xmp</groupId>
  	<artifactId>elasticsearch</artifactId>
  	<version>0.0.1-SNAPSHOT</version>
  	<packaging>jar</packaging>
  
  	<name>elasticsearch</name>
  	<description>ElasticsearchDemo project for Spring Boot</description>
  
  	<parent>
  		<groupId>org.springframework.boot</groupId>
  		<artifactId>spring-boot-starter-parent</artifactId>
  		<version>1.5.10.RELEASE</version>
  		<relativePath/> <!-- lookup parent from repository -->
  	</parent>
  
  	<properties>
  		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
  		<java.version>1.8</java.version>
  	</properties>
  
  	<dependencies>
  		<dependency>
  			<groupId>org.springframework.boot</groupId>
  			<artifactId>spring-boot-starter-data-elasticsearch</artifactId>
  		</dependency>
  		<dependency>
  			<groupId>org.springframework.boot</groupId>
  			<artifactId>spring-boot-starter-web</artifactId>
  		</dependency>
  
  		<dependency>
  			<groupId>org.projectlombok</groupId>
  			<artifactId>lombok</artifactId>
  			<optional>true</optional>
  		</dependency>
  		<dependency>
  			<groupId>org.springframework.boot</groupId>
  			<artifactId>spring-boot-starter-test</artifactId>
  			<scope>test</scope>
  		</dependency>
  	</dependencies>
  
  	<build>
  		<plugins>
  			<plugin>
  				<groupId>org.springframework.boot</groupId>
  				<artifactId>spring-boot-maven-plugin</artifactId>
  			</plugin>
  		</plugins>
  	</build>
  
  </project>
  ```
  ```
  D:\springboot-learning\springboot-elasticsearch>mvn dependency:tree
  [INFO] Scanning for projects...
  [INFO]
  [INFO] ------------------------------------------------------------------------
  [INFO] Building elasticsearch 0.0.1-SNAPSHOT
  [INFO] ------------------------------------------------------------------------
  [INFO]
  [INFO] --- maven-dependency-plugin:2.10:tree (default-cli) @ elasticsearch ---
  [INFO] com.xmp:elasticsearch:jar:0.0.1-SNAPSHOT
  [INFO] +- org.springframework.boot:spring-boot-starter-data-elasticsearch:jar:1.5.10.RELEASE:compile
  [INFO] |  +- org.springframework.boot:spring-boot-starter:jar:1.5.10.RELEASE:compile
  [INFO] |  |  +- org.springframework.boot:spring-boot:jar:1.5.10.RELEASE:compile
  [INFO] |  |  +- org.springframework.boot:spring-boot-autoconfigure:jar:1.5.10.RELEASE:compile
  [INFO] |  |  +- org.springframework.boot:spring-boot-starter-logging:jar:1.5.10.RELEASE:compile
  [INFO] |  |  |  +- ch.qos.logback:logback-classic:jar:1.1.11:compile
  [INFO] |  |  |  |  \- ch.qos.logback:logback-core:jar:1.1.11:compile
  [INFO] |  |  |  +- org.slf4j:jul-to-slf4j:jar:1.7.25:compile
  [INFO] |  |  |  \- org.slf4j:log4j-over-slf4j:jar:1.7.25:compile
  [INFO] |  |  \- org.yaml:snakeyaml:jar:1.17:compile
  [INFO] |  \- org.springframework.data:spring-data-elasticsearch:jar:2.1.10.RELEASE:compile
  [INFO] |     +- org.springframework:spring-context:jar:4.3.14.RELEASE:compile
  [INFO] |     +- org.springframework:spring-tx:jar:4.3.14.RELEASE:compile
  [INFO] |     +- org.springframework.data:spring-data-commons:jar:1.13.10.RELEASE:compile
  [INFO] |     +- commons-lang:commons-lang:jar:2.6:compile
  [INFO] |     +- joda-time:joda-time:jar:2.9.9:compile
  [INFO] |     +- org.elasticsearch:elasticsearch:jar:2.4.6:compile
  [INFO] |     |  +- org.apache.lucene:lucene-core:jar:5.5.4:compile
  [INFO] |     |  +- org.apache.lucene:lucene-backward-codecs:jar:5.5.4:compile
  [INFO] |     |  +- org.apache.lucene:lucene-analyzers-common:jar:5.5.4:compile
  [INFO] |     |  +- org.apache.lucene:lucene-queries:jar:5.5.4:compile
  [INFO] |     |  +- org.apache.lucene:lucene-memory:jar:5.5.4:compile
  [INFO] |     |  +- org.apache.lucene:lucene-highlighter:jar:5.5.4:compile
  [INFO] |     |  +- org.apache.lucene:lucene-queryparser:jar:5.5.4:compile
  [INFO] |     |  |  \- org.apache.lucene:lucene-sandbox:jar:5.5.4:compile
  [INFO] |     |  +- org.apache.lucene:lucene-suggest:jar:5.5.4:compile
  [INFO] |     |  |  \- org.apache.lucene:lucene-misc:jar:5.5.4:compile
  [INFO] |     |  +- org.apache.lucene:lucene-join:jar:5.5.4:compile
  [INFO] |     |  |  \- org.apache.lucene:lucene-grouping:jar:5.5.4:compile
  [INFO] |     |  +- org.apache.lucene:lucene-spatial:jar:5.5.4:compile
  [INFO] |     |  |  +- org.apache.lucene:lucene-spatial3d:jar:5.5.4:compile
  [INFO] |     |  |  \- com.spatial4j:spatial4j:jar:0.5:compile
  [INFO] |     |  +- com.google.guava:guava:jar:18.0:compile
  [INFO] |     |  +- org.elasticsearch:securesm:jar:1.0:compile
  [INFO] |     |  +- com.carrotsearch:hppc:jar:0.7.1:compile
  [INFO] |     |  +- com.fasterxml.jackson.dataformat:jackson-dataformat-smile:jar:2.8.10:compile
  [INFO] |     |  +- com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:jar:2.8.10:compile
  [INFO] |     |  +- com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:jar:2.8.10:compile
  [INFO] |     |  +- io.netty:netty:jar:3.10.6.Final:compile
  [INFO] |     |  +- com.ning:compress-lzf:jar:1.0.2:compile
  [INFO] |     |  +- com.tdunning:t-digest:jar:3.0:compile
  [INFO] |     |  +- org.hdrhistogram:HdrHistogram:jar:2.1.6:compile
  [INFO] |     |  +- commons-cli:commons-cli:jar:1.3.1:compile
  [INFO] |     |  \- com.twitter:jsr166e:jar:1.1.0:compile
  [INFO] |     +- com.fasterxml.jackson.core:jackson-core:jar:2.8.10:compile
  [INFO] |     +- org.slf4j:slf4j-api:jar:1.7.25:compile
  [INFO] |     \- org.slf4j:jcl-over-slf4j:jar:1.7.25:compile
  [INFO] +- org.springframework.boot:spring-boot-starter-web:jar:1.5.10.RELEASE:compile
  [INFO] |  +- org.springframework.boot:spring-boot-starter-tomcat:jar:1.5.10.RELEASE:compile
  [INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-core:jar:8.5.27:compile
  [INFO] |  |  |  \- org.apache.tomcat:tomcat-annotations-api:jar:8.5.27:compile
  [INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-el:jar:8.5.27:compile
  [INFO] |  |  \- org.apache.tomcat.embed:tomcat-embed-websocket:jar:8.5.27:compile
  [INFO] |  +- org.hibernate:hibernate-validator:jar:5.3.6.Final:compile
  [INFO] |  |  +- javax.validation:validation-api:jar:1.1.0.Final:compile
  [INFO] |  |  +- org.jboss.logging:jboss-logging:jar:3.3.1.Final:compile
  [INFO] |  |  \- com.fasterxml:classmate:jar:1.3.4:compile
  [INFO] |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.8.10:compile
  [INFO] |  |  \- com.fasterxml.jackson.core:jackson-annotations:jar:2.8.0:compile
  [INFO] |  +- org.springframework:spring-web:jar:4.3.14.RELEASE:compile
  [INFO] |  |  +- org.springframework:spring-aop:jar:4.3.14.RELEASE:compile
  [INFO] |  |  \- org.springframework:spring-beans:jar:4.3.14.RELEASE:compile
  [INFO] |  \- org.springframework:spring-webmvc:jar:4.3.14.RELEASE:compile
  [INFO] |     \- org.springframework:spring-expression:jar:4.3.14.RELEASE:compile
  [INFO] +- org.projectlombok:lombok:jar:1.16.20:compile
  [INFO] \- org.springframework.boot:spring-boot-starter-test:jar:1.5.10.RELEASE:test
  [INFO]    +- org.springframework.boot:spring-boot-test:jar:1.5.10.RELEASE:test
  [INFO]    +- org.springframework.boot:spring-boot-test-autoconfigure:jar:1.5.10.RELEASE:test
  [INFO]    +- com.jayway.jsonpath:json-path:jar:2.2.0:test
  [INFO]    |  \- net.minidev:json-smart:jar:2.2.1:test
  [INFO]    |     \- net.minidev:accessors-smart:jar:1.1:test
  [INFO]    |        \- org.ow2.asm:asm:jar:5.0.3:test
  [INFO]    +- junit:junit:jar:4.12:test
  [INFO]    +- org.assertj:assertj-core:jar:2.6.0:test
  [INFO]    +- org.mockito:mockito-core:jar:1.10.19:test
  [INFO]    |  \- org.objenesis:objenesis:jar:2.1:test
  [INFO]    +- org.hamcrest:hamcrest-core:jar:1.3:test
  [INFO]    +- org.hamcrest:hamcrest-library:jar:1.3:test
  [INFO]    +- org.skyscreamer:jsonassert:jar:1.4.0:test
  [INFO]    |  \- com.vaadin.external.google:android-json:jar:0.0.20131108.vaadin1:test
  [INFO]    +- org.springframework:spring-core:jar:4.3.14.RELEASE:compile
  [INFO]    \- org.springframework:spring-test:jar:4.3.14.RELEASE:test
  [INFO] ------------------------------------------------------------------------
  [INFO] BUILD SUCCESS
  [INFO] ------------------------------------------------------------------------
  [INFO] Total time: 3.010 s
  [INFO] Finished at: 2018-03-01T10:12:42+08:00
  [INFO] Final Memory: 26M/228M
  [INFO] ------------------------------------------------------------------------
```
  ## 2. Spring Data ElasticSearch Application
  <div>现在开始Spring Boot + Spring Data + Elasticsearch<div>
  2.1 编写我们的实体类：
  
  ```
  package com.xmp.entity;
  
  import lombok.*;
  import org.springframework.data.annotation.Id;
  import org.springframework.data.elasticsearch.annotations.Document;
  
  import java.io.Serializable;
  import java.util.Date;
  
  /**
   * <p>
   * <p>
   *
   * @author xiemopeng
   * @since 2018/2/27
   */
  @ToString
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @Document(indexName = "xmp", type = "news")
  public class Article implements Serializable {
      @Id
      private Long id;
      /**
       * 标题
       */
      private String title;
      /**
       * 摘要
       */
      private String abstracts;
      /**
       * 内容
       */
      private String content;
      /**
       * 发表时间
       */
      private Date postTime;
      /**
       * 点击率
       */
      private Long clickCount;
      /**
       * 作者
       */
      private Author author;
      /**
       * 所属教程
       */
      private Tutorial tutorial;
  }
  ```
  ```
    package com.xmp.entity;
    
    import lombok.*;
    
    import java.io.Serializable;
    
    /**
     * <p>
     * <p>
     *
     * @author xiemopeng
     * @since 2018/2/27
     */
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public class Author implements Serializable {
        /**
         * 作者id
         */
        private Long id;
        /**
         * 作者姓名
         */
        private String name;
        /**
         * 作者简介
         */
        private String remark;
    }
```

```
package com.xmp.entity;

import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/2/27
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Tutorial implements Serializable {
    private Long id;
    private String name;//教程名称
}

```
2.2 编写Elasticsearch Repository(dao)

```
package com.xmp.repository;

import com.xmp.entity.Article;
import com.xmp.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/2/27
 */
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {

    Page<Article> findByAuthor(Author author, Pageable pageable);

    List<Article> findByTitle(String title);

    Page<Article> findByTitle(String title, Pageable pageable);
}

```
2.3 编写service

老套路，先写需要的接口
```
package com.xmp.service;

import com.xmp.entity.Article;
import com.xmp.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/2/27
 */
public interface ArticleService {

    Article save(Article article);

    void delete(Article article);

    Article findOne(Long id);

    Iterable<Article> findAll();

    Page<Article> findByAuthor(Author author, PageRequest pageRequest);

    Page<Article> findByTitle(String title, PageRequest pageRequest);

    List<Article> findByTitle(String title);

    Iterable<Article> search(String queryString);
}

```
再写实现类

```
package com.xmp.service;

import com.xmp.entity.Article;
import com.xmp.entity.Author;
import com.xmp.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/2/27
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public Article findOne(Long id) {
        return articleRepository.findOne(id);
    }

    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Page<Article> findByAuthor(Author author, PageRequest pageRequest) {
        return articleRepository.findByAuthor(author, pageRequest);
    }

    @Override
    public Page<Article> findByTitle(String title, PageRequest pageRequest) {
        return articleRepository.findByTitle(title, pageRequest);
    }

    @Override
    public List<Article> findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    @Override
    public Iterable<Article> search(String queryString) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        return articleRepository.search(builder);
    }

}

```
2.4 配置Elasticsearch ==application.yml==

```
elasticsearch:
 clustername: xmp-cluster
 host: localhost
 port: 9300
```
2.5 编写springboot配置，连接Elasticsearch集群

```
package com.xmp.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

//http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-nosql.html#boot-features-connecting-to-elasticsearch-spring-data
//https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-data-elasticsearch/src/main/java/sample/data/elasticsearch
//http://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.repositories
//http://geekabyte.blogspot.my/2015/08/embedding-elasticsearch-in-spring.html
//https://github.com/spring-projects/spring-data-elasticsearch/wiki/Spring-Data-Elasticsearch---Spring-Boot---version-matrix
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.xmp.repository")
public class EsConfig {

    @Value("${elasticsearch.host}")
    private String EsHost;

    @Value("${elasticsearch.port}")
    private int EsPort;

    @Value("${elasticsearch.clustername}")
    private String EsClusterName;

    @Bean
    public Client client() throws Exception {

        Settings esSettings = Settings.settingsBuilder()
                .put("cluster.name", EsClusterName)
                .build();

        //https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
        return TransportClient.builder()
                .settings(esSettings)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }

    //Embedded Elasticsearch Server
    /*@Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }*/

}
```
## 3. Run Spring Boot Application
 <p>3.1 运行这个demo，需要按照以下几步</p>
<ul>
<li><strong>1:</strong> 安装 Java 设置 JAVA_HOME 和 PATH variables.</li>
<li><strong>2:</strong> 安装 Maven.</li>
<li><strong>3:</strong> 安装 <a href="https://www.elastic.co/downloads/past-releases/elasticsearch-2-4-0" target="_blank">Elasticsearch 2.4.0</a><br />
配置 ELASTICSEARCH_HOME = C:\elasticsearch-2.4.0
</li>
<li><strong>4:</strong> 配置 ElasticSearch Cluster<br />
打开 ${ELASTICSEARCH_HOME}\config\elasticsearch.yml 添加如下参数</p>
<div class="filename">${ELASTICSEARCH_HOME}\config\elasticsearch.yml</div>
<pre><code class="language-bash">
cluster.name: xmp-cluster
</code></pre>

</li>
<li><strong>5:</strong> 运行项目</li>
</ul>
<p>5.2 运行项目，会插入10条信息到elasticsearch服务器</p>
<div class="filename">Application.java</div>
<pre><code class="language-java">
package com.xmp;

import com.xmp.entity.Article;
import com.xmp.entity.Author;
import com.xmp.entity.Tutorial;
import com.xmp.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class ElasticsearchApplication implements CommandLineRunner {
    @Autowired
    private ElasticsearchOperations es;

    @Autowired
    private ArticleService articleService;

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        printElasticSearchInfo();
        //插入10条信息
        for (long i = 0; i < 10; i++) {
            Author author = new Author(null, "xmp", null);
//            System.out.println(author);
            Tutorial tutorial = new Tutorial(i, "test" + String.valueOf(i));
            Article article = new Article(i, "title", "abs", "content", new Date(), i, author, tutorial);
            articleService.save(article);
        }
        //fuzzey search
        Page<Article> articles = articleService.findByTitle("title", new PageRequest(0, 5));

        List<Article> articleList = articleService.findByTitle("title");

//        articles.forEach(x -> System.out.println(x));
//        articleList.forEach(article -> {
//            System.out.println(article);
//        });

        String queryString = "test1";//搜索关键字
        Iterable<Article> searchResult = articleService.search(queryString);
        Iterator<Article> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private void printElasticSearchInfo() {
        log.info("--ElasticSearch-->");
        Client client = es.getClient();
        Map<String, String> asMap = client.settings().getAsMap();
        asMap.forEach((k, v) -> {
            log.info(k + "=" + v);
        });

    }
}

</code></pre>
<p>Output</p>
<pre><code class="language-bash">
"C:\Program Files\Java\jdk1.8.0_121\bin\java" -Dspring.output.ansi.enabled=always -Didea.launcher.port=7537 "-Didea.launcher.bin.path=C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.3.5\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_121\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\rt.jar;D:\springboot-learning\springboot-elasticsearch\target\classes;D:\repository\org\springframework\boot\spring-boot-starter-data-elasticsearch\1.5.10.RELEASE\spring-boot-starter-data-elasticsearch-1.5.10.RELEASE.jar;D:\repository\org\springframework\boot\spring-boot-starter\1.5.10.RELEASE\spring-boot-starter-1.5.10.RELEASE.jar;D:\repository\org\springframework\boot\spring-boot\1.5.10.RELEASE\spring-boot-1.5.10.RELEASE.jar;D:\repository\org\springframework\boot\spring-boot-autoconfigure\1.5.10.RELEASE\spring-boot-autoconfigure-1.5.10.RELEASE.jar;D:\repository\org\springframework\boot\spring-boot-starter-logging\1.5.10.RELEASE\spring-boot-starter-logging-1.5.10.RELEASE.jar;D:\repository\ch\qos\logback\logback-classic\1.1.11\logback-classic-1.1.11.jar;D:\repository\ch\qos\logback\logback-core\1.1.11\logback-core-1.1.11.jar;D:\repository\org\slf4j\jul-to-slf4j\1.7.25\jul-to-slf4j-1.7.25.jar;D:\repository\org\slf4j\log4j-over-slf4j\1.7.25\log4j-over-slf4j-1.7.25.jar;D:\repository\org\yaml\snakeyaml\1.17\snakeyaml-1.17.jar;D:\repository\org\springframework\data\spring-data-elasticsearch\2.1.10.RELEASE\spring-data-elasticsearch-2.1.10.RELEASE.jar;D:\repository\org\springframework\spring-context\4.3.14.RELEASE\spring-context-4.3.14.RELEASE.jar;D:\repository\org\springframework\spring-tx\4.3.14.RELEASE\spring-tx-4.3.14.RELEASE.jar;D:\repository\org\springframework\data\spring-data-commons\1.13.10.RELEASE\spring-data-commons-1.13.10.RELEASE.jar;D:\repository\commons-lang\commons-lang\2.6\commons-lang-2.6.jar;D:\repository\joda-time\joda-time\2.9.9\joda-time-2.9.9.jar;D:\repository\org\elasticsearch\elasticsearch\2.4.6\elasticsearch-2.4.6.jar;D:\repository\org\apache\lucene\lucene-core\5.5.4\lucene-core-5.5.4.jar;D:\repository\org\apache\lucene\lucene-backward-codecs\5.5.4\lucene-backward-codecs-5.5.4.jar;D:\repository\org\apache\lucene\lucene-analyzers-common\5.5.4\lucene-analyzers-common-5.5.4.jar;D:\repository\org\apache\lucene\lucene-queries\5.5.4\lucene-queries-5.5.4.jar;D:\repository\org\apache\lucene\lucene-memory\5.5.4\lucene-memory-5.5.4.jar;D:\repository\org\apache\lucene\lucene-highlighter\5.5.4\lucene-highlighter-5.5.4.jar;D:\repository\org\apache\lucene\lucene-queryparser\5.5.4\lucene-queryparser-5.5.4.jar;D:\repository\org\apache\lucene\lucene-sandbox\5.5.4\lucene-sandbox-5.5.4.jar;D:\repository\org\apache\lucene\lucene-suggest\5.5.4\lucene-suggest-5.5.4.jar;D:\repository\org\apache\lucene\lucene-misc\5.5.4\lucene-misc-5.5.4.jar;D:\repository\org\apache\lucene\lucene-join\5.5.4\lucene-join-5.5.4.jar;D:\repository\org\apache\lucene\lucene-grouping\5.5.4\lucene-grouping-5.5.4.jar;D:\repository\org\apache\lucene\lucene-spatial\5.5.4\lucene-spatial-5.5.4.jar;D:\repository\org\apache\lucene\lucene-spatial3d\5.5.4\lucene-spatial3d-5.5.4.jar;D:\repository\com\spatial4j\spatial4j\0.5\spatial4j-0.5.jar;D:\repository\com\google\guava\guava\18.0\guava-18.0.jar;D:\repository\org\elasticsearch\securesm\1.0\securesm-1.0.jar;D:\repository\com\carrotsearch\hppc\0.7.1\hppc-0.7.1.jar;D:\repository\com\fasterxml\jackson\dataformat\jackson-dataformat-smile\2.8.10\jackson-dataformat-smile-2.8.10.jar;D:\repository\com\fasterxml\jackson\dataformat\jackson-dataformat-yaml\2.8.10\jackson-dataformat-yaml-2.8.10.jar;D:\repository\com\fasterxml\jackson\dataformat\jackson-dataformat-cbor\2.8.10\jackson-dataformat-cbor-2.8.10.jar;D:\repository\io\netty\netty\3.10.6.Final\netty-3.10.6.Final.jar;D:\repository\com\ning\compress-lzf\1.0.2\compress-lzf-1.0.2.jar;D:\repository\com\tdunning\t-digest\3.0\t-digest-3.0.jar;D:\repository\org\hdrhistogram\HdrHistogram\2.1.6\HdrHistogram-2.1.6.jar;D:\repository\commons-cli\commons-cli\1.3.1\commons-cli-1.3.1.jar;D:\repository\com\twitter\jsr166e\1.1.0\jsr166e-1.1.0.jar;D:\repository\com\fasterxml\jackson\core\jackson-core\2.8.10\jackson-core-2.8.10.jar;D:\repository\org\slf4j\slf4j-api\1.7.25\slf4j-api-1.7.25.jar;D:\repository\org\slf4j\jcl-over-slf4j\1.7.25\jcl-over-slf4j-1.7.25.jar;D:\repository\org\springframework\boot\spring-boot-starter-web\1.5.10.RELEASE\spring-boot-starter-web-1.5.10.RELEASE.jar;D:\repository\org\springframework\boot\spring-boot-starter-tomcat\1.5.10.RELEASE\spring-boot-starter-tomcat-1.5.10.RELEASE.jar;D:\repository\org\apache\tomcat\embed\tomcat-embed-core\8.5.27\tomcat-embed-core-8.5.27.jar;D:\repository\org\apache\tomcat\tomcat-annotations-api\8.5.27\tomcat-annotations-api-8.5.27.jar;D:\repository\org\apache\tomcat\embed\tomcat-embed-el\8.5.27\tomcat-embed-el-8.5.27.jar;D:\repository\org\apache\tomcat\embed\tomcat-embed-websocket\8.5.27\tomcat-embed-websocket-8.5.27.jar;D:\repository\org\hibernate\hibernate-validator\5.3.6.Final\hibernate-validator-5.3.6.Final.jar;D:\repository\javax\validation\validation-api\1.1.0.Final\validation-api-1.1.0.Final.jar;D:\repository\org\jboss\logging\jboss-logging\3.3.1.Final\jboss-logging-3.3.1.Final.jar;D:\repository\com\fasterxml\classmate\1.3.4\classmate-1.3.4.jar;D:\repository\com\fasterxml\jackson\core\jackson-databind\2.8.10\jackson-databind-2.8.10.jar;D:\repository\com\fasterxml\jackson\core\jackson-annotations\2.8.0\jackson-annotations-2.8.0.jar;D:\repository\org\springframework\spring-web\4.3.14.RELEASE\spring-web-4.3.14.RELEASE.jar;D:\repository\org\springframework\spring-aop\4.3.14.RELEASE\spring-aop-4.3.14.RELEASE.jar;D:\repository\org\springframework\spring-beans\4.3.14.RELEASE\spring-beans-4.3.14.RELEASE.jar;D:\repository\org\springframework\spring-webmvc\4.3.14.RELEASE\spring-webmvc-4.3.14.RELEASE.jar;D:\repository\org\springframework\spring-expression\4.3.14.RELEASE\spring-expression-4.3.14.RELEASE.jar;D:\repository\org\projectlombok\lombok\1.16.20\lombok-1.16.20.jar;D:\repository\org\springframework\spring-core\4.3.14.RELEASE\spring-core-4.3.14.RELEASE.jar;C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.3.5\lib\idea_rt.jar" com.intellij.rt.execution.application.AppMain com.xmp.ElasticsearchApplication

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::       (v1.5.10.RELEASE)

2018-03-01 11:31:42.443  INFO 19000 --- [           main] com.xmp.ElasticsearchApplication         : Starting ElasticsearchApplication on DESKTOP-AC6RJ04 with PID 19000 (D:\springboot-learning\springboot-elasticsearch\target\classes started by asus in D:\springboot-learning\springboot-elasticsearch)
2018-03-01 11:31:42.448  INFO 19000 --- [           main] com.xmp.ElasticsearchApplication         : The following profiles are active: local
2018-03-01 11:31:42.580  INFO 19000 --- [           main] ationConfigEmbeddedWebApplicationContext : Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@7f552bd3: startup date [Thu Mar 01 11:31:42 CST 2018]; root of context hierarchy
2018-03-01 11:31:44.167  INFO 19000 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Overriding bean definition for bean 'elasticsearchTemplate' with a different definition: replacing [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=esConfig; factoryMethodName=elasticsearchTemplate; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [com/xmp/config/EsConfig.class]] with [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration; factoryMethodName=elasticsearchTemplate; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [org/springframework/boot/autoconfigure/data/elasticsearch/ElasticsearchDataAutoConfiguration.class]]
2018-03-01 11:31:47.184  INFO 19000 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat initialized with port(s): 8080 (http)
2018-03-01 11:31:47.211  INFO 19000 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2018-03-01 11:31:47.213  INFO 19000 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/8.5.27
2018-03-01 11:31:47.460  INFO 19000 --- [ost-startStop-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2018-03-01 11:31:47.461  INFO 19000 --- [ost-startStop-1] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 4901 ms
2018-03-01 11:31:47.721  INFO 19000 --- [ost-startStop-1] o.s.b.w.servlet.ServletRegistrationBean  : Mapping servlet: 'dispatcherServlet' to [/]
2018-03-01 11:31:47.729  INFO 19000 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'characterEncodingFilter' to: [/*]
2018-03-01 11:31:47.729  INFO 19000 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
2018-03-01 11:31:47.729  INFO 19000 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'httpPutFormContentFilter' to: [/*]
2018-03-01 11:31:47.729  INFO 19000 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'requestContextFilter' to: [/*]
2018-03-01 11:31:48.334  INFO 19000 --- [           main] org.elasticsearch.plugins                : [Death's Head II] modules [], plugins [], sites []
2018-03-01 11:31:50.385  INFO 19000 --- [           main] s.w.s.m.m.a.RequestMappingHandlerAdapter : Looking for @ControllerAdvice: org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@7f552bd3: startup date [Thu Mar 01 11:31:42 CST 2018]; root of context hierarchy
2018-03-01 11:31:50.498  INFO 19000 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2018-03-01 11:31:50.501  INFO 19000 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2018-03-01 11:31:50.548  INFO 19000 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-03-01 11:31:50.549  INFO 19000 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-03-01 11:31:50.629  INFO 19000 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-03-01 11:31:50.982  INFO 19000 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-03-01 11:31:51.074  INFO 19000 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2018-03-01 11:31:51.080  INFO 19000 --- [           main] com.xmp.ElasticsearchApplication         : --ElasticSearch-->
2018-03-01 11:31:51.080  INFO 19000 --- [           main] com.xmp.ElasticsearchApplication         : client.type=transport
2018-03-01 11:31:51.081  INFO 19000 --- [           main] com.xmp.ElasticsearchApplication         : cluster.name=xmp-cluster
2018-03-01 11:31:51.081  INFO 19000 --- [           main] com.xmp.ElasticsearchApplication         : name=Death's Head II
2018-03-01 11:31:51.081  INFO 19000 --- [           main] com.xmp.ElasticsearchApplication         : network.server=false
2018-03-01 11:31:51.081  INFO 19000 --- [           main] com.xmp.ElasticsearchApplication         : node.client=true
2018-03-01 11:31:51.081  INFO 19000 --- [           main] com.xmp.ElasticsearchApplication         : transport.ping_schedule=5s
Article(id=0, title=title, abstracts=abs, content=content, postTime=Thu Mar 01 11:31:51 CST 2018, clickCount=0, author=Author(id=null, name=xmp, remark=null), tutorial=Tutorial(id=0, name=test0))
Article(id=5, title=title, abstracts=abs, content=content, postTime=Thu Mar 01 11:31:52 CST 2018, clickCount=5, author=Author(id=null, name=xmp, remark=null), tutorial=Tutorial(id=5, name=test5))
Article(id=8, title=title, abstracts=abs, content=content, postTime=Thu Mar 01 11:31:52 CST 2018, clickCount=8, author=Author(id=null, name=xmp, remark=null), tutorial=Tutorial(id=8, name=test8))
Article(id=9, title=title, abstracts=abs, content=content, postTime=Thu Mar 01 11:31:53 CST 2018, clickCount=9, author=Author(id=null, name=xmp, remark=null), tutorial=Tutorial(id=9, name=test9))
Article(id=2, title=title, abstracts=abs, content=content, postTime=Thu Mar 01 11:31:52 CST 2018, clickCount=2, author=Author(id=null, name=xmp, remark=null), tutorial=Tutorial(id=2, name=test2))
Article(id=4, title=title, abstracts=abs, content=content, postTime=Thu Mar 01 11:31:52 CST 2018, clickCount=4, author=Author(id=null, name=xmp, remark=null), tutorial=Tutorial(id=4, name=test4))
Article(id=6, title=title, abstracts=abs, content=content, postTime=Thu Mar 01 11:31:52 CST 2018, clickCount=6, author=Author(id=null, name=xmp, remark=null), tutorial=Tutorial(id=6, name=test6))
Article(id=1, title=title, abstracts=abs, content=content, postTime=Thu Mar 01 11:31:51 CST 2018, clickCount=1, author=Author(id=null, name=xmp, remark=null), tutorial=Tutorial(id=1, name=test1))
Article(id=7, title=title, abstracts=abs, content=content, postTime=Thu Mar 01 11:31:52 CST 2018, clickCount=7, author=Author(id=null, name=xmp, remark=null), tutorial=Tutorial(id=7, name=test7))
Article(id=3, title=title, abstracts=abs, content=content, postTime=Thu Mar 01 11:31:52 CST 2018, clickCount=3, author=Author(id=null, name=xmp, remark=null), tutorial=Tutorial(id=3, name=test3))
2018-03-01 11:31:53.335  INFO 19000 --- [           main] com.xmp.ElasticsearchApplication         : Started ElasticsearchApplication in 12.028 seconds (JVM running for 13.188)
</code></pre>
<p>When we run the application, our data is stored at ${ELASTICSEARCH_HOME}\data\mkyong-cluster.</p>
<p>5.3 Maven package and run it.</p>
<div class="filename">Terminal</div>
<pre><code class="language-bash">
$ mvn clean package
$ java -jar target/elasticsearch-0.0.1-SNAPSHOT.jar
</code></pre>
<p>5.4 Test with <code>cURL</code> tool.</p>
<div class="filename">Terminal</div>
<pre><code class="language-json">
$ curl "http://localhost:9200/xmp/news/_search?pretty=true"
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0{
  "took" : 5,
  "timed_out" : false,
  "_shards" : {
    "total" : 5,
    "successful" : 5,
    "failed" : 0
  },
  "hits" : {
    "total" : 10,
    "max_score" : 1.0,
    "hits" : [ {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "0",
      "_score" : 1.0,
      "_source" : {
        "id" : 0,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875605867,
        "clickCount" : 0,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 0,
          "name" : "test0"
        }
      }
    }, {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "5",
      "_score" : 1.0,
      "_source" : {
        "id" : 5,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875607354,
        "clickCount" : 5,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 5,
          "name" : "test5"
        }
      }
    }, {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "8",
      "_score" : 1.0,
      "_source" : {
        "id" : 8,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875608116,
        "clickCount" : 8,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 8,
          "name" : "test8"
        }
      }
    }, {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "9",
      "_score" : 1.0,
      "_source" : {
        "id" : 9,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875608240,
        "clickCount" : 9,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 9,
          "name" : "test9"
        }
      }
    }, {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "2",
      "_score" : 1.0,
      "_source" : {
        "id" : 2,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875606420,
        "clickCount" : 2,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 2,
          "name" : "test2"
        }
      }
    }, {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "4",
      "_score" : 1.0,
      "_source" : {
        "id" : 4,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875606995,
        "clickCount" : 4,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 4,
          "name" : "test4"
        }
      }
    }, {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "6",
      "_score" : 1.0,
      "_source" : {
        "id" : 6,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875607634,
        "clickCount" : 6,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 6,
          "name" : "test6"
        }
      }
    }, {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "1",
      "_score" : 1.0,
      "_source" : {
        "id" : 1,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875606181,
        "clickCount" : 1,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 1,
          "name" : "test1"
        }
      }
    }, {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "7",
100  4992  100  4992    0     0   325k      0 --:--:-- --:--:-- --:--:-- 4875k    "_score" : 1.0,
      "_source" : {
        "id" : 7,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875607923,
        "clickCount" : 7,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 7,
          "name" : "test7"
        }
      }
    }, {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "3",
      "_score" : 1.0,
      "_source" : {
        "id" : 3,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875606716,
        "clickCount" : 3,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 3,
          "name" : "test3"
        }
      }
    } ]
  }
}

</code></pre>
<div class="filename">Terminal</div>
<pre><code class="language-json">
$ curl "http://localhost:9200/xmp/news/_search?q=_id:3&pretty=true"
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   672  100   672    0     0  10666      0 --:--:-- --:--:-- --:--:-- 14297{
  "took" : 22,
  "timed_out" : false,
  "_shards" : {
    "total" : 5,
    "successful" : 5,
    "failed" : 0
  },
  "hits" : {
    "total" : 1,
    "max_score" : 1.0,
    "hits" : [ {
      "_index" : "xmp",
      "_type" : "news",
      "_id" : "3",
      "_score" : 1.0,
      "_source" : {
        "id" : 3,
        "title" : "title",
        "abstracts" : "abs",
        "content" : "content",
        "postTime" : 1519875606716,
        "clickCount" : 3,
        "author" : {
          "id" : null,
          "name" : "xmp",
          "remark" : null
        },
        "tutorial" : {
          "id" : 3,
          "name" : "test3"
        }
      }
    } ]
  }
}

</code></pre>
[参考文章](https://www.mkyong.com/spring-boot/spring-boot-spring-data-elasticsearch-example/)










  
  