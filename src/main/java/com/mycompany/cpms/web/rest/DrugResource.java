package com.mycompany.cpms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.cpms.domain.Drug;

import com.mycompany.cpms.repository.DrugRepository;
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
 * REST controller for managing Drug.
 */
@RestController
@RequestMapping("/api")
public class DrugResource {

    private final Logger log = LoggerFactory.getLogger(DrugResource.class);

    private static final String ENTITY_NAME = "drug";
        
    private final DrugRepository drugRepository;

    public DrugResource(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    /**
     * POST  /drugs : Create a new drug.
     *
     * @param drug the drug to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drug, or with status 400 (Bad Request) if the drug has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/drugs")
    @Timed
    public ResponseEntity<Drug> createDrug(@Valid @RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to save Drug : {}", drug);
        if (drug.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new drug cannot already have an ID")).body(null);
        }
        Drug result = drugRepository.save(drug);
        return ResponseEntity.created(new URI("/api/drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drugs : Updates an existing drug.
     *
     * @param drug the drug to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drug,
     * or with status 400 (Bad Request) if the drug is not valid,
     * or with status 500 (Internal Server Error) if the drug couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/drugs")
    @Timed
    public ResponseEntity<Drug> updateDrug(@Valid @RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to update Drug : {}", drug);
        if (drug.getId() == null) {
            return createDrug(drug);
        }
        Drug result = drugRepository.save(drug);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, drug.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drugs : get all the drugs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of drugs in body
     */
    @GetMapping("/drugs")
    @Timed
    public ResponseEntity<List<Drug>> getAllDrugs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Drugs");
        Page<Drug> page = drugRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/drugs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /drugs/:id : get the "id" drug.
     *
     * @param id the id of the drug to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drug, or with status 404 (Not Found)
     */
    @GetMapping("/drugs/{id}")
    @Timed
    public ResponseEntity<Drug> getDrug(@PathVariable Long id) {
        log.debug("REST request to get Drug : {}", id);
        Drug drug = drugRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(drug));
    }

    /**
     * DELETE  /drugs/:id : delete the "id" drug.
     *
     * @param id the id of the drug to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/drugs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        log.debug("REST request to delete Drug : {}", id);
        drugRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
