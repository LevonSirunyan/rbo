package am.task.services;

import am.task.exceptions.EntityNotFoundException;
import am.task.model.entity.AbstractEntity;
import am.task.repositories.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>> implements CommonService<E> {

    private final R repository;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E model) {
        return repository.save(model);
    }

    @Override
    public Iterable<E> saveAll(Iterable<E> iterable) {
        return repository.saveAll(iterable);
    }

    @Override
    public Iterable<E> listAll() {
        return repository.findAll();
    }

    @Override
    public Page<E> listAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public E getById(Long id) throws EntityNotFoundException {
        if (repository.findById(id).isEmpty())
            throw new EntityNotFoundException(repository.findById(id).getClass(), "id", String.valueOf(id));

        return repository.findById(id).orElse(null);
    }

    @Override
    public void remove(E entity) {
        repository.delete(entity);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public boolean delete(Long id) throws EntityNotFoundException {
        if (repository.findById(id).isEmpty())
            throw new EntityNotFoundException(repository.findById(id).getClass(), "id", String.valueOf(id));
        repository.deleteById(id);
        return true;
    }
}