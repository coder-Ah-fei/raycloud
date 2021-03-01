package hqsc.ray.wcc.pageEntity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class ScopePageEntity extends Page {

    private String scope;

    public ScopePageEntity() {
    }

    public ScopePageEntity(String scope,Long current,Long size) {
        this.scope = scope;
        this.current = current;
        this.size = size;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
