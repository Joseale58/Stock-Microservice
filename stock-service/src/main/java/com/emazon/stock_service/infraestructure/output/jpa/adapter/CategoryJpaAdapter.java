package com.emazon.stock_service.infraestructure.output.jpa.adapter;

import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.infraestructure.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.infraestructure.exception.CategoryNotFoundByIdException;
import com.emazon.stock_service.infraestructure.exception.CategoryNotFoundByNameException;
import com.emazon.stock_service.infraestructure.output.jpa.entity.CategoryEntity;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.ICategoryEntityMapper;
import com.emazon.stock_service.infraestructure.output.jpa.repository.ICategoryRepository;
import com.emazon.stock_service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;


    @Override
    public Category getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);
        if(categoryEntityOptional.isEmpty()) {
            throw new CategoryNotFoundByIdException();
        }
        return categoryEntityMapper.toCategory(categoryEntityOptional.get());
    }

    @Override
    public Category getCategoryByName(String name) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findByName(name);
        if(categoryEntityOptional.isEmpty()) {
            throw new CategoryNotFoundByNameException();
        }
        return categoryEntityMapper.toCategory(categoryEntityOptional.get());
    }


    @Override
    public List<Category> getAllCategories() {
        List< CategoryEntity> categoryEntityList = categoryRepository.findAll();
        if (categoryEntityList.isEmpty()) {
            throw new CategoryNotFoundByNameException();
        }
        return categoryEntityMapper.toCategoryList(categoryEntityList);
    }


    @Override
    public CustomPage<Category> getPaginatedCategories(Integer page, Integer pageSize, String order) {
        final Pageable pageable;
        if(order.equals(Constants.PAGE_ASC_OPTION)){
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC,  Constants.PAGE_NAME_OPTION));
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,  Constants.PAGE_NAME_OPTION));
        }

        Page<CategoryEntity> categoryEntityPage = categoryRepository.findAll(pageable);
        List<CategoryEntity> categoryEntityList = categoryEntityPage.getContent();

        if (categoryEntityList.isEmpty()) {
            throw new CategoryNotFoundByNameException();
        }

        return new CustomPage<>(
                categoryEntityMapper.toCategoryList(categoryEntityList),
                categoryEntityPage.getTotalElements(),
                categoryEntityPage.getTotalPages(),
                categoryEntityPage.getNumber(),
                order.equals(Constants.PAGE_ASC_OPTION),
                categoryEntityPage.isEmpty()
        );
    }


    @Override
    public void save(Category category) {

        if(categoryRepository.findByName(category.getName()).isPresent()){
            throw new CategoryAlreadyExistsException(Constants.CATEGORY_ALREADY_EXISTS_EXCEPTION);
        }
        categoryRepository.save(categoryEntityMapper.toCategoryEntity(category));
    }

    @Override
    public void update(Category category) {

    }

    @Override
    public void delete(Category category) {

    }


}
