package dev.ioalex.springcrud.repository;

import dev.ioalex.springcrud.entity.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {
    /*
    Create a quote.
    Update a quote with a specific ID.
    Get a quote with a specific ID.
    Delete a quote with a specific ID.
    Get a random quote.
    Get all quotes.
    Get quotes that contain specific text (e.g. "discover").
    */
    @Query("""
        select quote from Quote quote where quote.text like %:text% order by quote.author
    """)
    List<Quote> findAllByTextLikeOrderByAuthor(@Param("text") String text, Pageable pageable);

    @Query("""
        select quote from Quote quote order by quote.author
    """)
    List<Quote> findAllByOrderByAuthor(Pageable pageable);

    Quote save(@Validated Quote entity);

}
