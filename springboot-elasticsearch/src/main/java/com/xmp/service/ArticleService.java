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
