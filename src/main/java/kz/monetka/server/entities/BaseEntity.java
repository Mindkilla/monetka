package kz.monetka.server.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BaseEntity {
    @Id
    @GeneratedValue
    private long id;
}
