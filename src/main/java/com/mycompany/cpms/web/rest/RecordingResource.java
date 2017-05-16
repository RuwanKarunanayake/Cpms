package com.mycompany.cpms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.cpms.domain.Recording;

import com.mycompany.cpms.repository.RecordingRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Recording.
 */
@RestController
@RequestMapping("/api")
public class RecordingResource {

    private final Logger log = LoggerFactory.getLogger(RecordingResource.class);

    private static final String ENTITY_NAME = "recording";
        
    private final RecordingRepository recordingRepository;

    public RecordingResource(RecordingRepository recordingRepository) {
        this.recordingRepository = recordingRepository;
    }

    /**
     * POST  /recordings : Create a new recording.
     *
     * @param recording the recording to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recording, or with status 400 (Bad Request) if the recording has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recordings")
    @Timed
    public ResponseEntity<Recording> createRecording(@Valid @RequestBody Recording recording) throws URISyntaxException {
        log.debug("REST request to save Recording : {}", recording);
        if (recording.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new recording cannot already have an ID")).body(null);
        }
        Recording result = recordingRepository.save(recording);
        return ResponseEntity.created(new URI("/api/recordings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recordings : Updates an existing recording.
     *
     * @param recording the recording to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recording,
     * or with status 400 (Bad Request) if the recording is not valid,
     * or with status 500 (Internal Server Error) if the recording couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recordings")
    @Timed
    public ResponseEntity<Recording> updateRecording(@Valid @RequestBody Recording recording) throws URISyntaxException {
        log.debug("REST request to update Recording : {}", recording);
        if (recording.getId() == null) {
            return createRecording(recording);
        }
        Recording result = recordingRepository.save(recording);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recording.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recordings : get all the recordings.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of recordings in body
     */
    @GetMapping("/recordings")
    @Timed
    public ResponseEntity<List<Recording>> getAllRecordings(@ApiParam Pageable pageable, @RequestParam(required = false) String filter) {
        if ("investigation-is-null".equals(filter)) {
            log.debug("REST request to get all Recordings where investigation is null");
            return new ResponseEntity<>(StreamSupport
                .stream(recordingRepository.findAll().spliterator(), false)
                .filter(recording -> recording.getInvestigation() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Recordings");
        Page<Recording> page = recordingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recordings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recordings/:id : get the "id" recording.
     *
     * @param id the id of the recording to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recording, or with status 404 (Not Found)
     */
    @GetMapping("/recordings/{id}")
    @Timed
    public ResponseEntity<Recording> getRecording(@PathVariable Long id) {
        log.debug("REST request to get Recording : {}", id);
        Recording recording = recordingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recording));
    }

    /**
     * DELETE  /recordings/:id : delete the "id" recording.
     *
     * @param id the id of the recording to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recordings/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecording(@PathVariable Long id) {
        log.debug("REST request to delete Recording : {}", id);
        recordingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
