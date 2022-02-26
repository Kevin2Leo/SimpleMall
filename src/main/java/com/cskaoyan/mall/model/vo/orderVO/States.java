package com.cskaoyan.mall.model.vo.orderVO;


public class States {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {

        if(id == 0){
            setName("未付款");
        }
        else if(id == 1){
            setName("未发货");
        }
        else if (id == 2){
            setName("已发货");
        }
        else if (id ==3){
            setName("已完成订单");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
