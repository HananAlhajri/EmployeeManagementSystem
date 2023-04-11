package com.coop.employeemanagment.infrastructures.constans;

import java.util.List;

public interface ICommonServices {

    List<Object> listOf(Long id);

    Object add(Long id,Object object);

    //Object add(Employee employee);

    Object update(Long id, Object object);

    List<Object> view(Long id);

    String isExist(Long id);
}
