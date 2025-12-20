package com.alphadjo.social_media;

import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.entity.Role;

import com.alphadjo.social_media.repository.contract.RoleRepository;
import com.alphadjo.social_media.repository.contract.UtilisateurRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = false)
@ActiveProfiles("test")
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
    public void shouldFindUserById() {
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findById(utilisateur.getId());
        assertTrue(utilisateurOptional.isPresent());
    }

    /**
     * Finds user by email and validates email
     */
    @Test
    public void shouldFindUserByEmail() {
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        assertTrue(utilisateurOptional.isPresent());
        assertEquals("alphadiobiya@gmail.com", utilisateurOptional.get().getEmail());
    }

    /**
     * Finds user by phone and validates phone number
     */
    @Test
    public void shouldFindUserByPhone() {
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByPhone(utilisateur.getPhone());
        assertTrue(utilisateurOptional.isPresent());
        assertEquals("+33661474742", utilisateurOptional.get().getPhone());
    }

    @Test
    public void shouldReturnEmptyWhenEMailDoesNotExist() {
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail("inconu.email@gmail.com");
        assertTrue(utilisateurOptional.isEmpty());
    }

    /**
     * Confirms user status can be updated and persisted
     */
    @Test
    public void shouldReturnIsActiveUserWhenStatusIsUpdate(){
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        assertTrue(utilisateurOptional.isPresent());

        Utilisateur updateUser = utilisateurOptional.get();
        updateUser.setActive(true);
        this.utilisateurRepository.save(updateUser);

        utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        assertTrue(utilisateurOptional.isPresent());
        assertTrue(utilisateurOptional.get().isActive());
    }

    /**
     * Confirms deletion by verifying absence after removal
     */
    @Test
    public void shouldReturnNullWhenUserIsDeleted(){
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        assertTrue(utilisateurOptional.isPresent());

        this.utilisateurRepository.delete(utilisateurOptional.get());
        utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        assertTrue(utilisateurOptional.isEmpty());
    }

    @Test
    public void shouldContainRoledUSerTest(){
        assertTrue(utilisateur.getRoles().contains(roleUser));
    }

    @Test
    public void shouldNotHaveRoleAdminTest(){
        assertFalse(utilisateur.getRoles().contains(roleAdmin));
    }

    @Test
    public void shouldAdresseBeEmpty(){
        assertTrue(utilisateur.getAdresses().isEmpty());
    }

    @AfterEach
    public void tearDown(){
        utilisateurRepository.deleteAll();
        roleRepository.deleteAll();
    }
}
