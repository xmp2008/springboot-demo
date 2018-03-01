  Springboot+Elasticsearch整合实例
  ================================

  这篇文章主要介绍Springboot与Elasticsearch整合在一起的简单例子
  ####使用到的工具：
  1. Spring Boot 1.5.10.RELEASE
  2. Spring Boot Starter Data Elasticsearch 1.5.10.RELEASE
  3. Spring Data Elasticsearch 2.1.10.RELEASE
  4. Elasticsearch 2.4.0
  5. Maven
  6. Java 8
  
  ``注意 ``：Spring Boot 1.5.10.RELEASE和Spring Data Elasticsearch 2.1.10.RELEASE只支持Elasticsearch 2.4.0，不支持最新的ElasticSearch 5.x版本
  详见：[Spring Data Elasticsearch Spring Boot version matrix](https://github.com/spring-projects/spring-data-elasticsearch/wiki/Spring-Data-Elasticsearch---Spring-Boot---version-matrix)
  
  ##1.项目依赖：
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
  ##2. Spring Data ElasticSearch Application
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


  
  