package hqsc.ray.wcc.pageEntity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PlatformPageEntity extends Page {

    private String platform;

    public PlatformPageEntity() {
    }

    public PlatformPageEntity(String platform,Long current,Long size) {
        this.platform = platform;
        this.current = current;
        this.size = size;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
