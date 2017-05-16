package com.mycompany.cpms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.cpms.domain.Investigation;

import com.mycompany.cpms.repository.InvestigationRepository;
import com.mycompany.cpms.security.SecurityUtils;
import com.mycompany.cpms.web.rest.util.HeaderUtil;
import com.mycompany.cpms.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Investigation.
 */
@RestController
@RequestMapping("/api")
public class InvestigationResource {

    private final Logger log = LoggerFactory.getLogger(InvestigationResource.class);

    private static final String ENTITY_NAME = "investigation";

    private final InvestigationRepository investigationRepository;

    public InvestigationResource(InvestigationRepository investigationRepository) {
        this.investigationRepository = investigationRepository;
    }

    /**
     * POST  /investigations : Create a new investigation.
     *
     * @param investigation the investigation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new investigation, or with status 400 (Bad Request) if the investigation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/investigations")
    @Timed
    public ResponseEntity<Investigation> createInvestigation(@Valid @RequestBody Investigation investigation) throws URISyntaxException {
        log.debug("REST request to save Investigation : {}", investigation);
        if (investigation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new investigation cannot already have an ID")).body(null);
        }
        Investigation result = investigationRepository.save(investigation);
        return ResponseEntity.created(new URI("/api/investigations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /investigations : Updates an existing investigation.
     *
     * @param investigation the investigation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated investigation,
     * or with status 400 (Bad Request) if the investigation is not valid,
     * or with status 500 (Internal Server Error) if the investigation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/investigations")
    @Timed
    public ResponseEntity<Investigation> updateInvestigation(@Valid @RequestBody Investigation investigation) throws URISyntaxException {
        log.debug("REST request to update Investigation : {}", investigation);
        if (investigation.getId() == null) {
            return createInvestigation(investigation);
        }
        Investigation result = investigationRepository.save(investigation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, investigation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /investigations : get all the investigations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of investigations in body
     */
    @GetMapping("/investigations")
    @Timed
    public ResponseEntity<List<Investigation>> getAllInvestigations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Investigations");
        Page<Investigation> page;
        if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            page = investigationRepository.findAll(pageable);
        }else{
            page = investigationRepository.findByRecordingUserLogin(SecurityUtils.getCurrentUserLogin(),pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/investigations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /investigations/:id : get the "id" investigation.
     *
     * @param id the id of the investigation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the investigation, or with status 404 (Not Found)
     */
    @GetMapping("/investigations/{id}")
    @Timed
    public ResponseEntity<Investigation> getInvestigation(@PathVariable Long id) {
        log.debug("REST request to get Investigation : {}", id);
        Investigation investigation = investigationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(investigation));
    }

    /**
     * DELETE  /investigations/:id : delete the "id" investigation.
     *
     * @param id the id of the investigation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/investigations/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvestigation(@PathVariable Long id) {
        log.debug("REST request to delete Investigation : {}", id);
        investigationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
