/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.common;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    //
    // Vorhandene Datensätze finden
    //
    public Entity findById(EntityId id) {
        return em.find(entityClass, id);
    }

    public List<Entity> findAll() {
        String select = "SELECT s FROM $S s".replace("$S", this.entityClass.getName());
        return em.createQuery(select).getResultList();
    }

    //
    // Datensätze speichern, ändern, löschen
    //
    public Entity saveNew(Entity entity) {
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
