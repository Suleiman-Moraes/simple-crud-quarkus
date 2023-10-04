package com.moraes.api.service;

import java.util.List;
import java.util.stream.Collectors;

import com.moraes.api.dto.ProductDTO;
import com.moraes.api.model.ProductEntity;
import com.moraes.api.repository.ProductRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductService {

    @Inject
    private ProductRepository repository;

    public List<ProductDTO> findAll() {
        return repository.findAll().stream().map(this::mapProductEntityToProductDTO)
                .collect(Collectors.toList());
    }

    public Long create(ProductDTO dto) {
        ProductEntity entity = mapProductDTOToProductEntity(dto);
        repository.persist(entity);
        return entity.getId();
    }

    public void update(Long id, ProductDTO dto) {
        ProductEntity entity = repository.findById(id);

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setModel(dto.getModel());
        entity.setPrice(dto.getPrice());

        repository.persist(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public ProductDTO findById(Long id) {
        return mapProductEntityToProductDTO(repository.findById(id));
    }

    private ProductDTO mapProductEntityToProductDTO(ProductEntity entity) {
        return ProductDTO.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .model(entity.getName())
                .price(entity.getPrice())
                .build();
    }

    private ProductEntity mapProductDTOToProductEntity(ProductDTO dto) {
        return ProductEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .model(dto.getName())
                .price(dto.getPrice())
                .build();
    }
}
