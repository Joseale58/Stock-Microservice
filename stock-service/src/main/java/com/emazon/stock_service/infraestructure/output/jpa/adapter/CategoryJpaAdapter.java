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


    //To show a category by id
    @Override
    public Category getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);
        if(categoryEntityOptional.isEmpty()) {
            throw new CategoryNotFoundByIdException();
        }
        return categoryEntityMapper.toCategory(categoryEntityOptional.get());
    }

    //To show a category by name
    @Override
    public Category getCategoryByName(String name) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findByName(name);
        if(categoryEntityOptional.isEmpty()) {
            throw new CategoryNotFoundByNameException();
        }
        return categoryEntityMapper.toCategory(categoryEntityOptional.get());
    }

    //To show all categories
    @Override
    public List<Category> getAllCategories() {
        List< CategoryEntity> categoryEntityList = categoryRepository.findAll();
        if (categoryEntityList.isEmpty()) {
            throw new CategoryNotFoundByNameException();
        }
        return categoryEntityMapper.toCategoryList(categoryEntityList);
    }

    //To paginate categories
    @Override
    public CustomPage<Category> getPaginatedCategories(Integer page, Integer pageSize, String order) {
        final Pageable pageable; // Declare var out of if-block
        if(order.equals("asc")){
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "name"));
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
                order.equals("asc"),
                categoryEntityPage.isEmpty()
        );
    }


    //To create a new category
    @Override
    public void save(Category category) {

        //Validating that the category doesn't exist at DB
        if(categoryRepository.findByName(category.getName()).isPresent()){
            throw new CategoryAlreadyExistsException("Ya existe una categoría con ese nombre");
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
