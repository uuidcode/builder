package com.github.uuidcode.builder.validation;

import com.github.uuidcode.builder.domain.Person;
import com.github.uuidcode.builder.domain.Project;
import org.junit.Test;

public class ValidationBuilderTest {
   @Test
   public void test1() {
       Project project = Project.of();

       try {
           this.getValidationBuilder(project);
       } catch (Throwable t) {
           t.getMessage().equals("projectId is null.");
       }

       project.setProjectId(1234L);

       try {
           this.getValidationBuilder(project);
       } catch (Throwable t) {
           t.getMessage().equals("price is null.");
       }

       project.setPrice(999L);

       try {
           this.getValidationBuilder(project);
       } catch (Throwable t) {
           t.getMessage().equals("price is less than 1000.");
       }
   }

    private void getValidationBuilder(Project project) {
        ValidationBuilder.of(project)
            .add(p -> p.getProjectId() == null, "projectId is null.")
            .add(p -> p.getPrice() == null, "price is null.")
            .add(p -> p.getPrice() < 1000L, "price is less than 1000.");
    }

    @Test
    public void test2() {
        Project project = null;
        Person person = Person.of();

        ValidationBuilder.of(person, project != null)
            .add(p -> project.getProjectId() != null && p.getName() == null, "person name is null.");
    }

    @Test
    public void test3() {
        Project project = Project.of().setProjectId(1234L);
        Person person = Person.of();

        try {
            ValidationBuilder.of(person, project != null)
                .add(p -> project.getProjectId() != null && p.getName() == null, "person name is null.");
        } catch (Throwable t) {
            t.getMessage().equals("person name is null.");
        }
    }
}