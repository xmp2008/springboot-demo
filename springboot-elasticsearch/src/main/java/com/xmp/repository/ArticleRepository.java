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
