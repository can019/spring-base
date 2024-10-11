package com.github.can019.base.gradle.github;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import net.datafaker.Faker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegisterGitHubMetadataTaskTest {
    private Project project;
    private RegisterGitHubMetadataTask task;

    private Faker faker = new Faker();

    @BeforeEach
    public void setUp() {
        project = ProjectBuilder.builder().build();

        project.getExtensions().getExtraProperties().set("commitId", null);
        project.getExtensions().getExtraProperties().set("releaseVersion", null);

        task = project.getTasks().create("registerGitHubMetadata", RegisterGitHubMetadataTask.class);
    }

    @Test
    @DisplayName("Commit id와 release version이 정상적으로 등록되어야 한다")
    public void testRegisterMetadata() {
        String commitId = faker.code().ean8();
        project.getExtensions().getExtraProperties().set("commitId", commitId);

        String releaseVersion = faker.app().version();
        project.getExtensions().getExtraProperties().set("releaseVersion", releaseVersion);

        task.registerMetadata();

        System.out.println(commitId);
        System.out.println(releaseVersion);


        assertThat(commitId).isEqualTo( project.getExtensions().getExtraProperties().get("commitId"));
        assertThat(releaseVersion).isEqualTo(project.getExtensions().getExtraProperties().get("releaseVersion"));
    }

    @Test
    @DisplayName("Commit id가 null인 경우 오류가 발생해야한다")
    public void testRegisterMetadataWithoutCommitId() {
        String releaseVersion = faker.app().version();
        project.getExtensions().getExtraProperties().set("releaseVersion", releaseVersion);

        project.getExtensions().getExtraProperties().set("commitId", null);

        assertThatThrownBy(()-> task.registerMetadata())
                .isInstanceOf(GradleException.class)
                .hasMessage("Commit id 또는 release version이 없습니다");
    }

    @Test
    @DisplayName("Release version이 null인 경우 오류가 발생해야한다")
    public void testRegisterMetadataWithoutReleaseVersion() {
        String commitId = faker.code().ean8();
        project.getExtensions().getExtraProperties().set("commitId", commitId);
        project.getExtensions().getExtraProperties().set("releaseVersion", null);

        assertThatThrownBy(()-> task.registerMetadata())
                .isInstanceOf(GradleException.class)
                .hasMessage("Commit id 또는 release version이 없습니다");
    }
}
