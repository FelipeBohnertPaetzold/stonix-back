package com.escoladeti.oldowl.stonix.forum.model;

import com.google.common.base.MoreObjects;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by Felipe on 2016-05-21.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BasicForum extends Forum {

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastUpdate;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BasicForum() {
        this.lastUpdate = new Date(System.currentTimeMillis());
    }


    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("last_update", lastUpdate)
                .toString();
    }
}
