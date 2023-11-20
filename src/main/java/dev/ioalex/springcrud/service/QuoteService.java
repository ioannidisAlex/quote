package dev.ioalex.springcrud.service;

import dev.ioalex.springcrud.dto.PaginationDTO;
import dev.ioalex.springcrud.dto.QuoteDTO;
import dev.ioalex.springcrud.entity.Quote;
import dev.ioalex.springcrud.repository.QuoteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final QuoteRepository repository;

    public QuoteService(QuoteRepository repository) {
        this.repository = repository;
    }

    public List<QuoteDTO> findAll(PaginationDTO paginationDTO) {
        return repository.findAllByOrderByAuthor(PageRequest.of(paginationDTO.getPage(), paginationDTO.getPageSize()))
                .stream().map(entity -> new QuoteDTO(entity.getId(), entity.getAuthor(), entity.getText()))
                .collect(Collectors.toList());
    }

    public QuoteDTO findById(long id) {
        return repository.findById(id)
                .map(entity -> new QuoteDTO(entity.getId(), entity.getAuthor(), entity.getText()))
                .orElseThrow();
    }

    public QuoteDTO update(long id, QuoteDTO quoteDTO) {
        return repository.findById(id)
                .map(entity -> updateEntity(entity, quoteDTO))
                .orElseThrow();
    }

    public QuoteDTO create(QuoteDTO quoteDTO) {
        return updateEntity(new Quote(), quoteDTO);
    }

    private QuoteDTO updateEntity(Quote quote, QuoteDTO quoteDTO) {
        quote.setText(quoteDTO.getText());
        quote.setAuthor(quoteDTO.getAuthor());
        quote = repository.save(quote);
        quoteDTO.setId(quote.getId());
        return quoteDTO;
    }

}
