package hqsc.ray.wcc.pageEntity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class TypePageEntity extends Page {
    private String type;

    public TypePageEntity() {
    }

    public TypePageEntity(Long current,Long size,String type) {
        this.type = type;
        this.current = current;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
