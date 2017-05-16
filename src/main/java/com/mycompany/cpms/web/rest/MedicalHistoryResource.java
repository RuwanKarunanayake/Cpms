package com.mycompany.cpms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.cpms.domain.MedicalHistory;

import com.mycompany.cpms.repository.MedicalHistoryRepository;
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
 * REST controller for managing MedicalHistory.
 */
@RestController
@RequestMapping("/api")
public class MedicalHistoryResource {

    private final Logger log = LoggerFactory.getLogger(MedicalHistoryResource.class);

    private static final String ENTITY_NAME = "medicalHistory";

    private final MedicalHistoryRepository medicalHistoryRepository;

    public MedicalHistoryResource(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    /**
     * POST  /medical-histories : Create a new medicalHistory.
     *
     * @param medicalHistory the medicalHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalHistory, or with status 400 (Bad Request) if the medicalHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-histories")
    @Timed
    public ResponseEntity<MedicalHistory> createMedicalHistory(@Valid @RequestBody MedicalHistory medicalHistory) throws URISyntaxException {
        log.debug("REST request to save MedicalHistory : {}", medicalHistory);
        if (medicalHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new medicalHistory cannot already have an ID")).body(null);
        }
        MedicalHistory result = medicalHistoryRepository.save(medicalHistory);
        return ResponseEntity.created(new URI("/api/medical-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-histories : Updates an existing medicalHistory.
     *
     * @param medicalHistory the medicalHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalHistory,
     * or with status 400 (Bad Request) if the medicalHistory is not valid,
     * or with status 500 (Internal Server Error) if the medicalHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-histories")
    @Timed
    public ResponseEntity<MedicalHistory> updateMedicalHistory(@Valid @RequestBody MedicalHistory medicalHistory) throws URISyntaxException {
        log.debug("REST request to update MedicalHistory : {}", medicalHistory);
        if (medicalHistory.getId() == null) {
            return createMedicalHistory(medicalHistory);
        }
        MedicalHistory result = medicalHistoryRepository.save(medicalHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-histories : get all the medicalHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medicalHistories in body
     */
    @GetMapping("/medical-histories")
    @Timed
    public List<MedicalHistory> getAllMedicalHistories() {
        log.debug("REST request to get all MedicalHistories");
        List<MedicalHistory> medicalHistories;
        if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            medicalHistories = medicalHistoryRepository.findAll();
        }else{
            medicalHistories = medicalHistoryRepository.findByUserIsCurrentUser();
        }
        return medicalHistories;
    }

    /**
     * GET  /medical-histories/:id : get the "id" medicalHistory.
     *
     * @param id the id of the medicalHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalHistory, or with status 404 (Not Found)
     */
    @GetMapping("/medical-histories/{id}")
    @Timed
    public ResponseEntity<MedicalHistory> getMedicalHistory(@PathVariable Long id) {
        log.debug("REST request to get MedicalHistory : {}", id);
        MedicalHistory medicalHistory = medicalHistoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medicalHistory));
    }

    /**
     * DELETE  /medical-histories/:id : delete the "id" medicalHistory.
     *
     * @param id the id of the medicalHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalHistory(@PathVariable Long id) {
        log.debug("REST request to delete MedicalHistory : {}", id);
        medicalHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
