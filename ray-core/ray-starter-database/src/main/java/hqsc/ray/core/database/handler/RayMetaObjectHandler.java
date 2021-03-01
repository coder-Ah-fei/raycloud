package hqsc.ray.core.database.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充时间字段
 * @author xuzhanfu
 */
@Component
public class RayMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "creationDate", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "lastUpdateDate", LocalDateTime.class, LocalDateTime.now());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "creationDate", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "lastUpdateDate", LocalDateTime.class, LocalDateTime.now());
    }
}
