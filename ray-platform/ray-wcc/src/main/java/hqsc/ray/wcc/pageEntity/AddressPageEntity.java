package hqsc.ray.wcc.pageEntity;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class AddressPageEntity extends Page{


    private String address;

    public AddressPageEntity(){

    }

    public AddressPageEntity(Long current,Long size,String address) {
        this.address = address;
        this.current = current;
        this.size = size;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
