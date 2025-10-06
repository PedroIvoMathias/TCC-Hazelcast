package com.safar.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safar.model.Route;
@Repository
public interface RouteRepository extends JpaRepository<Route, Long>{
	
	public Route findByRouteFromAndRouteTo(String from,String to);	

}
