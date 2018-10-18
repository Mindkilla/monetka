package kz.monetka.server.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Comment("Идентификатор объекта")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;
    @Version
    @Comment("Системная (hibernate) версия объекта")
    private Integer version;
    @Comment("Системное время создания")
    @Column
    private Date sysCreateTime;
    @Comment("Дата архивирования")
    @Column
    private Long archiveTime;
    @Comment("Дата удаления")
    @Column
    private Long deleteTime;

    public BaseEntity() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        if (version == null) {
            this.version = Integer.valueOf(0);
        } else {
            this.version = version;
        }

    }

    public Date getSysCreateTime() {
        return this.sysCreateTime;
    }

    public void setSysCreateTime(Date sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
    }

    public Long getArchiveTime() {
        return this.archiveTime;
    }

    public void setArchiveTime(Long archiveTime) {
        this.archiveTime = archiveTime;
    }

    public Long getDeleteTime() {
        return this.deleteTime;
    }

    public void setDeleteTime(Long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof BaseEntity)) {
            return false;
        } else {
            BaseEntity that = (BaseEntity) o;
            if (this.id != null) {
                if (!this.id.equals(that.id)) {
                    return false;
                }
            } else if (that.id != null) {
                return false;
            }

            return true;
        }
    }

    public int hashCode() {
        return this.id != null ? this.id.hashCode() : 0;
    }

    public String toString() {
        return this.getClass().getSimpleName() + ":" + this.id;
    }
}
