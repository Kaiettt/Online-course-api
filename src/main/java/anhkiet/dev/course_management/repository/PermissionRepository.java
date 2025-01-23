package anhkiet.dev.course_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import anhkiet.dev.course_management.domain.entity.Permisison;

public interface PermissionRepository extends JpaRepository<Permisison, Long>, JpaSpecificationExecutor<Permisison>{
}
