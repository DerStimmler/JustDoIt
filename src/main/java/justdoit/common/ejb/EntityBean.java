package justdoit.common.ejb;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import justdoit.common.exception.EntityAlreadyExistsException;

/**
 * Abstrakte Basisklasse für EJBs, die einfach nur Standardmethoden zum Lesen
 * und Schreiben eines Entity-Typs bietet.
 *
 * @param <Entity> Basisklasse der Entität
 * @param <EntityId> Datentyp oder Klasse für die Schlüsselwerte
 */
public abstract class EntityBean<Entity, EntityId> {

    @PersistenceContext
    EntityManager em;

    private final Class<Entity> entityClass;

    public EntityBean(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    public Entity findById(EntityId id) {
        return em.find(entityClass, id);
    }

    public List<Entity> findAll() {
        try {
            String select = "SELECT s FROM $S s".replace("$S", this.entityClass.getName());
            return em.createQuery(select).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Entity saveNew(Entity entity, EntityId id) throws EntityAlreadyExistsException {
        if (this.findById(id) != null) {
            throw new EntityAlreadyExistsException(this.entityClass.getName());
        }

        em.persist(entity);
        return em.merge(entity);
    }

    public Entity update(Entity entity) {
        return em.merge(entity);
    }

    public void delete(Entity entity) {
        entity = em.merge(entity);
        em.remove(entity);
    }
}
