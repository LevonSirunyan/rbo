package am.task.repositories;


import am.task.model.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonRepository<E extends AbstractEntity> extends JpaRepository<E, Long> {
    Page<E> findAll(Pageable pageable);

    Page<E> findAll(Specification<E> specification, Pageable page);

    List<E> findAll(Specification<E> specification);

    List<E> findALLByIdIn(List<Long> ids);
}