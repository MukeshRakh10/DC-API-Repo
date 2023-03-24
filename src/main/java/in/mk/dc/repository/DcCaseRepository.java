package in.mk.dc.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.mk.dc.entity.DcCase;

public interface DcCaseRepository extends JpaRepository<DcCase,Serializable> {

}
