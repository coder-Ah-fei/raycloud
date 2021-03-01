package hqsc.ray.component.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "spring.servlet.multipart",ignoreUnknownFields = false)
public class MultipartProperties {
    /**
     * Whether to enable support of multipart uploads.
     */
    private boolean enabled = true;

    /**
     * Intermediate location of uploaded files.
     */
    private String location;

    /**
     * Max file size. Values can use the suffixes "MB" or "KB" to indicate megabytes or
     * kilobytes, respectively.
     */
    private String maxFileSize = "10MB";

    /**
     * Max request size. Values can use the suffixes "MB" or "KB" to indicate megabytes or
     * kilobytes, respectively.
     */
    private String maxRequestSize = "15MB";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getMaxRequestSize() {
        return maxRequestSize;
    }

    public void setMaxRequestSize(String maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }
}
