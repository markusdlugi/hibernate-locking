package org.acme;

import java.time.Instant;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.hibernate.LockMode;
import org.hibernate.reactive.mutiny.Mutiny;
import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Uni;

@Path("/myentity")
public class MyEntityResource {

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @POST
    @Path("/withlock/{name}")
    public Uni<MyEntity> createEntityWithLock(final String name) {
        return sessionFactory.withTransaction(session ->
                session.persist(new MyEntity(name))
                        .onItem().ignore().andSwitchTo(() -> this.updateEntity(name, true)));
    }

    @POST
    @Path("/withoutlock/{name}")
    public Uni<MyEntity> createEntityWithoutLock(final String name) {
        return sessionFactory.withTransaction(session ->
                session.persist(new MyEntity(name))
                        .onItem().ignore().andSwitchTo(() -> this.updateEntity(name, false)));
    }

    private Uni<MyEntity> updateEntity(final String name, final boolean useLock) {
        if (useLock) {
            return sessionFactory.withTransaction(session -> session.find(MyEntity.class, name, LockMode.PESSIMISTIC_WRITE))
                    .onItem().transform(entity -> entity.setLastUpdated(Instant.now()));
        }
        else {
            return sessionFactory.withTransaction(session -> session.find(MyEntity.class, name))
                    .onItem().transform(entity -> entity.setLastUpdated(Instant.now()));
        }
    }
}