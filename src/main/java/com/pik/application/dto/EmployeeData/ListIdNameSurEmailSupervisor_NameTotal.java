package com.pik.application.dto.EmployeeData;

import java.util.List;

public class ListIdNameSurEmailSupervisor_NameTotal {

    private List<IdNameSurEmailSupervisor_NameProjects> list;
    private Integer totalCount;

    public ListIdNameSurEmailSupervisor_NameTotal(List<IdNameSurEmailSupervisor_NameProjects> list, Integer totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    public List<IdNameSurEmailSupervisor_NameProjects> getList() {
        return list;
    }

    public void setList(List<IdNameSurEmailSupervisor_NameProjects> list) {
        this.list = list;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
