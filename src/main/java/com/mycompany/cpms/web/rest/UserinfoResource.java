package com.mycompany.cpms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.cpms.domain.Userinfo;

import com.mycompany.cpms.repository.UserinfoRepository;
import com.mycompany.cpms.security.SecurityUtils;
import com.mycompany.cpms.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Userinfo.
 */
@RestController
@RequestMapping("/api")
public class UserinfoResource {

    private final Logger log = LoggerFactory.getLogger(UserinfoResource.class);

    private static final String ENTITY_NAME = "userinfo";

    private final UserinfoRepository userinfoRepository;

    public UserinfoResource(UserinfoRepository userinfoRepository) {
        this.userinfoRepository = userinfoRepository;
    }

    /**
     * POST  /userinfos : Create a new userinfo.
     *
     * @param userinfo the userinfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userinfo, or with status 400 (Bad Request) if the userinfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/userinfos")
    @Timed
    public ResponseEntity<Userinfo> createUserinfo(@Valid @RequestBody Userinfo userinfo) throws URISyntaxException {
        log.debug("REST request to save Userinfo : {}", userinfo);
        if (userinfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userinfo cannot already have an ID")).body(null);
        }
        Userinfo result = userinfoRepository.save(userinfo);
        return ResponseEntity.created(new URI("/api/userinfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userinfos : Updates an existing userinfo.
     *
     * @param userinfo the userinfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userinfo,
     * or with status 400 (Bad Request) if the userinfo is not valid,
     * or with status 500 (Internal Server Error) if the userinfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/userinfos")
    @Timed
    public ResponseEntity<Userinfo> updateUserinfo(@Valid @RequestBody Userinfo userinfo) throws URISyntaxException {
        log.debug("REST request to update Userinfo : {}", userinfo);
        if (userinfo.getId() == null) {
            return createUserinfo(userinfo);
        }
        Userinfo result = userinfoRepository.save(userinfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userinfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /userinfos : get all the userinfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userinfos in body
     */
    @GetMapping("/userinfos")
    @Timed
    public List<Userinfo> getAllUserinfos() {
        log.debug("REST request to get all Userinfos");
        List<Userinfo> userinfos;
        if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            userinfos = userinfoRepository.findAll();
        }else{
            userinfos = userinfoRepository.findByUserIsCurrentUser();
        }
        return userinfos;
    }

    /**
     * GET  /userinfos/:id : get the "id" userinfo.
     *
     * @param id the id of the userinfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userinfo, or with status 404 (Not Found)
     */
    @GetMapping("/userinfos/{id}")
    @Timed
    public ResponseEntity<Userinfo> getUserinfo(@PathVariable Long id) {
        log.debug("REST request to get Userinfo : {}", id);
        Userinfo userinfo = userinfoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userinfo));
    }

    /**
     * DELETE  /userinfos/:id : delete the "id" userinfo.
     *
     * @param id the id of the userinfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/userinfos/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserinfo(@PathVariable Long id) {
        log.debug("REST request to delete Userinfo : {}", id);
        userinfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
