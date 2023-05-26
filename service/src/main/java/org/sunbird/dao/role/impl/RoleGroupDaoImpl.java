package org.sunbird.dao.role.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.sunbird.cassandra.CassandraOperation;
import org.sunbird.dao.role.RoleGroupDao;
import org.sunbird.helper.ServiceFactory;
import org.sunbird.keys.JsonKey;
import org.sunbird.model.role.RoleGroup;
import org.sunbird.request.RequestContext;
import org.sunbird.response.Response;

public class RoleGroupDaoImpl implements RoleGroupDao {

  private final ObjectMapper mapper = new ObjectMapper();
  private static RoleGroupDao roleGroupDao;

  public static RoleGroupDao getInstance() {
    if (roleGroupDao == null) {
      roleGroupDao = new RoleGroupDaoImpl();
    }
    return roleGroupDao;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<RoleGroup> getRoleGroups(RequestContext context) {
    String KEYSPACE_NAME = "sunbird";
    String TABLE_NAME = "role_group";
    Response roleGroupResults =
        getCassandraOperation().getAllRecords(KEYSPACE_NAME, TABLE_NAME, context);
    TypeReference<List<RoleGroup>> roleGroupType = new TypeReference<List<RoleGroup>>() {};
    System.out.println("***************roleGroupResults************"+roleGroupResults);
    List<Map<String, Object>> roleGroupMapList =
        (List<Map<String, Object>>) roleGroupResults.get(JsonKey.RESPONSE);
    
    System.out.println("***************roleGroupMapList************ : "+roleGroupMapList);
     System.out.println("***************roleGroupMapList************ : "+roleGroupMapList.size());
    
    List<RoleGroup> roleGroupList = mapper.convertValue(roleGroupMapList, roleGroupType);
     //List<RoleGroup> roleGroupList = this.convertValue();
    
    return roleGroupList;
  }
  
  public CassandraOperation getCassandraOperation() {
    return ServiceFactory.getInstance();
  }
}
