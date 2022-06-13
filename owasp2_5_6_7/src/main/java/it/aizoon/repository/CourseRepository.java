package it.aizoon.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.aizoon.model.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

}
