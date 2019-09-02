package com.gofun.cloudbox.android.entity;

import java.util.List;

public class CarBrandList {
    private int total;
    private int pages;
    private int pageNum;
    private List<CarBrand> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<CarBrand> getList() {
        return list;
    }

    public void setList(List<CarBrand> list) {
        this.list = list;
    }

    public static class CarBrand {
        private String modelId;
        private String carBrand;
        private String brandSeries;
        private String modelYear;
        private int level;
        private String parentId;
        private int status;
        private String modelCode;
        private String engineType;
        private String gearboxType;
        private String paiLiang;
        private String remark;
        private String createBy;
        private long createTime;

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public String getBrandSeries() {
            return brandSeries;
        }

        public void setBrandSeries(String brandSeries) {
            this.brandSeries = brandSeries;
        }

        public String getModelYear() {
            return modelYear;
        }

        public void setModelYear(String modelYear) {
            this.modelYear = modelYear;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getModelCode() {
            return modelCode;
        }

        public void setModelCode(String modelCode) {
            this.modelCode = modelCode;
        }

        public String getEngineType() {
            return engineType;
        }

        public void setEngineType(String engineType) {
            this.engineType = engineType;
        }

        public String getGearboxType() {
            return gearboxType;
        }

        public void setGearboxType(String gearboxType) {
            this.gearboxType = gearboxType;
        }

        public String getPaiLiang() {
            return paiLiang;
        }

        public void setPaiLiang(String paiLiang) {
            this.paiLiang = paiLiang;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

    }
}


