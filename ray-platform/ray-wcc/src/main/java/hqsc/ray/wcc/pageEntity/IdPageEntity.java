package hqsc.ray.wcc.pageEntity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class IdPageEntity extends Page {

    private Long id;

    public IdPageEntity(){

    }

    public IdPageEntity(Long current,Long size,Long id){
        this.id = id;
        this.current = current;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long userid) {
        this.id = userid;
    }
}
