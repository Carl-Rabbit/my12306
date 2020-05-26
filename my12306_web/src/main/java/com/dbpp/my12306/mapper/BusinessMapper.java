package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.RouteDetailInfo;
import com.dbpp.my12306.entity.RouteInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessMapper {
	List<RouteInfo> listRoutes(String fromStr, String toStr, String departDate);
	List<RouteDetailInfo> listRouteDetails(String trainCode);
}
