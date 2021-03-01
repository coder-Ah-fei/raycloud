package hqsc.ray.wcc.pageEntity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class FieldPageEntity extends Page{

    private String field;

    public FieldPageEntity(){

    }

    public FieldPageEntity(Long current,Long size,String field){
        this.field = field;
        this.current = current;
        this.size = size;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
