package com.pik.application.dto.ProjectsData;

import java.util.List;

public class ListIdNameDescriptionTotal {

    private List<IdNameDescription> list;
    private Integer totalCount;

    public ListIdNameDescriptionTotal(List<IdNameDescription> list, Integer totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    public List<IdNameDescription> getList() {
        return list;
    }

    public void setList(List<IdNameDescription> list) {
        this.list = list;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
