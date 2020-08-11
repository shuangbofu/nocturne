package cn.shuangbofu.nocturne.dao;

import io.github.biezhi.anima.core.ResultKey;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * Created by shuangbofu on 2020/7/29 上午10:56
 */
@Accessors(chain = true)
@Getter
@Setter
public class Model<T extends Model> extends io.github.biezhi.anima.Model {

    private Long id;
    private Long gmtCreate;
    private Long gmtModified;

    @Override
    public ResultKey save() {
        ResultKey key = super.save();
        setId(key.asBigInteger().longValue());
        return key;
    }

    public T insert() {
        setGmtCreate(System.currentTimeMillis());
        setGmtModified(System.currentTimeMillis());
        save();
        return (T) this;
    }

    @Override
    public int update() {
        long id = getId();
        int update = super.update();
        setId(id);
        setGmtModified(System.currentTimeMillis());
        return update;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model<?> model = (Model<?>) o;
        return Objects.equals(id, model.id) &&
                Objects.equals(gmtCreate, model.gmtCreate) &&
                Objects.equals(gmtModified, model.gmtModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified);
    }
}