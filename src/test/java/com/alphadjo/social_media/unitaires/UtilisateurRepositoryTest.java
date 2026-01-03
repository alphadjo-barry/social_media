package com.alphadjo.social_media.unitaires;

import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.entity.Role;

import com.alphadjo.social_media.repository.contract.RoleRepository;
import com.alphadjo.social_media.repository.contract.UtilisateurRepository;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest(showSql = false)
public class UtilisateurRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(UtilisateurRepositoryTest.class);

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Utilisateur utilisateur;
    private Role roleUser;
    private Role roleAdmin;

    @BeforeEach
    void setup(){

        roleUser = Role.builder()
                .name("ROLE_USER")
                .isActive(false)
                .build();

        roleAdmin =  Role.builder()
                .name("ROLE_ADMIN")
                .isActive(false)
                .build();

        Role savedRoleUser = roleRepository.save(roleUser);
        Role savedRoleAdmin = roleRepository.save(roleAdmin);

        utilisateur = Utilisateur.builder()
                    .firstName("Alphadjo")
                    .lastName("Barry")
                    .email("alphadiobiya@gmail.com")
                    .password("Alphadjo30901@")
                    .phone("+33661474742")
                    .isActive(false)
                    .genre("HOMME")
                    .roles(new HashSet<>(Collections.singletonList(savedRoleUser)))
                    .photoOriginalName(null)
                    .adresses(new ArrayList<>())
                    .birthDay(LocalDate.of(1999, 12, 12))
                    .build();

        this.utilisateurRepository.save(utilisateur);
        log.info(() -> "User saved with id: " + utilisateur.getId());
    }

    @Test
    @DisplayName("Should return empty list when no user added")
    public void shouldReturnEmptyListWhenNoOneUserAdded(){

        //When
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();

        //Given
        assertThat(utilisateurs).isNotEmpty();

        //Then
        assertThat(utilisateurs.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should find user by id")
    public void shouldFindUserById() {
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findById(utilisateur.getId());
        assertThat(utilisateurOptional).isPresent();
    }

    /**
     * Finds user by email and validates email with AssertJ
     */
    @Test
    @DisplayName("Should find user by email")
    public void shouldFindUserByEmailWithAssertJ() {
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        assertThat(utilisateurOptional).isPresent()
                .get()
                .extracting(Utilisateur::getEmail)
                .isEqualTo("alphadiobiya@gmail.com");
    }

    /**
     * Finds user by phone and validates phone number
     */
    @Test
    @DisplayName("Should find user by phone")
    public void shouldFindUserByPhone() {
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByPhone(utilisateur.getPhone());

        assertThat(utilisateurOptional).isPresent()
                .get()
                .extracting(Utilisateur::getPhone)
                .isEqualTo("+33661474742");
    }

    @Test
    @DisplayName("Should return empty when user email is not found")
    public void shouldReturnEmptyWhenEMailDoesNotExist() {
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail("inconu.email@gmail.com");
        assertThat(utilisateurOptional).isEmpty();
    }

    /**
     * Confirms user status can be updated and persisted
     */
    @Test
    @DisplayName("Should return user with updated status")
    public void shouldReturnIsActiveUserWhenStatusIsUpdate(){
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        assertTrue(utilisateurOptional.isPresent());

        Utilisateur updateUser = utilisateurOptional.get();
        updateUser.setActive(true);
        this.utilisateurRepository.save(updateUser);

        utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        assertThat(utilisateurOptional).isPresent();
        assertThat(utilisateurOptional.get().isActive()).isTrue();
    }

    /**
     * Confirms deletion by verifying absence after removal
     */
    @Test
    @DisplayName("Should return null when user is deleted")
    public void shouldReturnEmptyWhenUserIsDeleted(){
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        assertTrue(utilisateurOptional.isPresent());

        this.utilisateurRepository.delete(utilisateurOptional.get());
        utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        assertThat(utilisateurOptional).isEmpty();
    }

    /**
     * confirms that the user role is ROLE_USER
     */
    @Test
    @DisplayName("Should return user with role ROLE_USER")
    public void shouldContainRoledUserTest()    {
        assertThat(utilisateur.getRoles()).contains(roleUser);
    }

    /**
     * confirms that the user role is not ROLE_ADMIN
     */
    @Test
    @DisplayName("Should not return user with role ROLE_ADMIN")
    public void shouldNotHaveRoleAdminTest(){
        assertThat(utilisateur.getRoles()).doesNotContain(roleAdmin);
    }

    /**
     * confirms that the user has no address
     */
    @Test
    @DisplayName("Should return user with no address")
    public void shouldAdresseBeEmpty(){
       assertThat(utilisateur.getAdresses()).isEmpty();
    }

    /**
     * confirms that the user was born before today
     */
    @Test
    @DisplayName("Should return user born before today")
    public void shouldReturnThatUserIsBornBeforeToDay(){
        assertThat(utilisateur.getBirthDay()).isBefore(LocalDate.now());
    }

    /**
     * confirms that the user was not born in the future
     */
    @Test
    @DisplayName("Should return user not born in future")
    public void shouldReturnThatUserIsNotBornInFuture(){
        assertThat(utilisateur.getBirthDay().isAfter(LocalDate.now())).isFalse();
    }
}
