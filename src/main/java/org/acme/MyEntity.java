package org.acme;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class MyEntity {

    @Id
    private String name;

    @CreationTimestamp
    private Instant creationTime;

    @UpdateTimestamp
    private Instant lastUpdated;

    public MyEntity() {
    }

    public MyEntity(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public MyEntity setName(final String name) {
        this.name = name;
        return this;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public MyEntity setCreationTime(final Instant creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public MyEntity setLastUpdated(final Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MyEntity myEntity = (MyEntity) o;
        return name.equals(myEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
