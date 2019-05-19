package com.pik.application.dto.EmployeeData;

import java.util.List;

public class ListIdNameSurUserEmailTotal {

    private List<IdNameSurUserEmail> list;
    private Integer totalCount;

    public ListIdNameSurUserEmailTotal(List<IdNameSurUserEmail> list, Integer totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    public List<IdNameSurUserEmail> getList() {
        return list;
    }

    public void setList(List<IdNameSurUserEmail> list) {
        this.list = list;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
