package com.cskaoyan.mall.model.bo.goodsBo;

import com.cskaoyan.mall.model.bean.Spec;

import java.util.List;

/**
 * 编辑商品信息请求时的BO
 * {"id":"744",
 *   "name":"华为手机",
 *   "typeId":191,
 *   "img":"http://115.29.141.32:8084/static/image/1620973070891a.jpg",
 *   "desc":"华为手机 就是好，不买后悔一辈子",
 *   "specList":[{
 *      "id":1913,"specName":"内存128G","stockNum":123,"unitPrice":2499},{
 *      "id":1914,"specName":"内存64G","stockNum":277,"unitPrice":3499}
 *      ]
 * }
 */
public class GoodsUpdateBO {

    private Integer id;
    private String name;
    private Integer typeId;
    private String img;
    private String desc;

    private List<Spec> specList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }
}
