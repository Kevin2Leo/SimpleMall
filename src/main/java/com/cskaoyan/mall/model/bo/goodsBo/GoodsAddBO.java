package com.cskaoyan.mall.model.bo.goodsBo;

import java.util.List;

/**
 * "name": "华为手机",
 * 	"typeId": "191",
 * 	"img": "/static/image/1620973070891a.jpg",
 * 	"desc": "华为手机 就是好，不买后悔一辈子",
 * 	"specList": [{
 * 		"specName": "内存64GG",
 * 		"stockNum": "123",
 * 		"unitPrice": "2499"
 *        }, {
 */
public class GoodsAddBO {

    private String name;
    private Integer typeId;
    private String img;
    private String desc;

    private List<GoodsAddSpecBO> specList;



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

    public List<GoodsAddSpecBO> getSpecList() {
        return specList;
    }

    public void setSpecList(List<GoodsAddSpecBO> specList) {
        this.specList = specList;
    }
}
