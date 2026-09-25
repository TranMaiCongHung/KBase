package com.se196693.mvc.specification;

import com.se196693.mvc.entity.Project;
import com.se196693.mvc.entity.ProjectMember;
import com.se196693.mvc.entity.User;
import com.se196693.mvc.enums.ProjectRole;
import com.se196693.mvc.enums.Role;
import com.se196693.mvc.enums.UserStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification {
    public static Specification<Project> hasKeyword(String keyword) {

        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }

            String pattern = "%" + keyword.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            pattern
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("description")),
                            pattern
                    )
            );
        };
    }

    public static Specification<Project> containsUserWithRole(User user, ProjectRole role) {
        return (root, query, criteriaBuilder) -> {

            // Nếu không truyền role, chỉ lọc theo User (Các dự án user tham gia)
            if (role == null) {
                Join<Project, ProjectMember> memberJoin = root.join("projectMembers");
                return criteriaBuilder.equal(memberJoin.get("user"), user);
            }
            // Thực hiện thao tác JOIN từ bảng Project sang ProjectMember
            Join<Project, ProjectMember> memberJoin = root.join("projectMembers");
            // Tạo điều kiện: member.user = user AND member.projectRole = role
            return criteriaBuilder.and(
                    criteriaBuilder.equal(memberJoin.get("user"), user),
                    criteriaBuilder.equal(memberJoin.get("projectRole"), role)
            );
        };
    }
    public static Specification<Project> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDeleted"), false);
    }

}
