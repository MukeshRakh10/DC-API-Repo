package in.mk.dc.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.mk.dc.entity.CitizenApp;

@Repository
public interface CitizenAppRepository extends JpaRepository<CitizenApp, Serializable> {

}
